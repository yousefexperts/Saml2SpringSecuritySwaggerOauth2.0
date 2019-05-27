package com.experts.core.biller.statemachine.api.rovo.awsxray.config;

import com.experts.core.biller.statemachine.api.rovo.awsxray.HeaderConstants;
import com.experts.core.biller.statemachine.api.rovo.awsxray.config.mdc.CustomUnitOfWorkFactory;
import com.experts.core.biller.statemachine.api.rovo.awsxray.routes.HttpInvokerRoute;
import com.experts.core.biller.statemachine.api.rovo.awsxray.routes.SqlQueryRoute;
import com.experts.core.biller.statemachine.api.rovo.awsxray.routes.api.FileProcessingRoute;
import com.experts.core.biller.statemachine.api.rovo.awsxray.routes.api.HealthCheckResponderRoute;
import com.experts.core.biller.statemachine.api.rovo.awsxray.routes.api.SampleFileRoute;
import com.experts.core.biller.statemachine.api.rovo.awsxray.xray.MonitorServicesAspect;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import dk.nykredit.jackson.dataformat.hal.HALMapper;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.aws.xray.XRayTracer;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.micrometer.messagehistory.MicrometerMessageHistoryFactory;
import org.apache.camel.component.micrometer.routepolicy.MicrometerRoutePolicyFactory;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultDataFormatResolver;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.management.DefaultManagementAgent;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.spi.ManagementAgent;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.camel.spring.spi.BridgePropertyPlaceholderConfigurer;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jms.connection.JmsTransactionManager;

@Configuration
@Import({
        SpringSecurityConfig.class,
        MongoSpringConfig.class,
    SSLContextConfig.class,
        HttpClientSpringConfig.class
})
@EnableAspectJAutoProxy
public class SpringConfig extends CamelConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Resource
    private Environment env;



    @Bean
    public static BridgePropertyPlaceholderConfigurer properties()
    {
        final BridgePropertyPlaceholderConfigurer result = new BridgePropertyPlaceholderConfigurer();

        result.setOrder( 0 );
        result.setIgnoreUnresolvablePlaceholders( true );
        result.setLocations( new ClassPathResource( "application.properties" ));
        return result;
    }

    @Override
    protected void setupCamelContext(CamelContext camelContext) throws Exception {
        LOG.debug("Initializing camel context");
        super.setupCamelContext(camelContext);
        SimpleRegistry registry = new SimpleRegistry();
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        registry.put("connectionFactory", connectionFactory);

        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
        jmsTransactionManager.setConnectionFactory(connectionFactory);
        registry.put("jmsTransactionManager", jmsTransactionManager);

        SpringTransactionPolicy propagationRequired = new SpringTransactionPolicy();
        propagationRequired.setTransactionManager(jmsTransactionManager);
        propagationRequired.setPropagationBehaviorName("PROPAGATION_REQUIRED");
        registry.put("PROPAGATION_REQUIRED", propagationRequired);

        SpringTransactionPolicy propagationNotSupported = new SpringTransactionPolicy();
        propagationNotSupported.setTransactionManager(jmsTransactionManager);
        propagationNotSupported.setPropagationBehaviorName("PROPAGATION_NOT_SUPPORTED");
        registry.put("PROPAGATION_NOT_SUPPORTED", propagationNotSupported);


        ((org.apache.camel.impl.DefaultCamelContext) camelContext).setRegistry(registry);

        ActiveMQComponent activeMQComponent = new ActiveMQComponent();
        activeMQComponent.setConnectionFactory(connectionFactory);
        activeMQComponent.setTransactionManager(jmsTransactionManager);
        camelContext.addComponent("jms", activeMQComponent);

        PropertiesComponent pc = new PropertiesComponent( );

        pc.setLocation( new ClassPathResource("application.properties").getPath());
        camelContext.addComponent("properties",  pc);

        camelContext.addRoutePolicyFactory(new MicrometerRoutePolicyFactory());
        camelContext.setMessageHistoryFactory(new MicrometerMessageHistoryFactory());
        camelContext.setDataFormatResolver(new HalDataFormatResolver());

        ManagementAgent agent = new DefaultManagementAgent( camelContext);

        agent.setCreateConnector(true);
        agent.setUsePlatformMBeanServer(true);
        camelContext.getManagementStrategy().setManagementAgent(agent);

        camelContext.getManagementNameStrategy().setNamePattern("#name#");
        camelContext.setUseBreadcrumb(true);
        camelContext.setTracing(false);

        camelContext.setUseMDCLogging(true);
        String[] mdcHeaders = new String[]{HeaderConstants.FILE_ID};
        camelContext.setUnitOfWorkFactory(new CustomUnitOfWorkFactory(mdcHeaders));
        XRayTracer xRayTracer = new XRayTracer();
        xRayTracer.init(camelContext);
    }

    @Bean(name = "producerTemplate")
    public ProducerTemplate producerTemplate() throws Exception {
        return camelContext().createProducerTemplate();
    }

    @Override
    public List<RouteBuilder> routes() {
        LOG.debug("Initializing Camel routes");
        List<RouteBuilder> routes = new ArrayList<>(4);
        routes.add(healthCheckResponderRoute());
        routes.add(httpInvokerRoute());
        routes.add(sampleFileRoute());
        routes.add(fileProcessingRoute());
        routes.add(sqlQueryRoute());

        return routes;
    }

    @Bean(name = "healthCheckResponder")
    public HealthCheckResponderRoute healthCheckResponderRoute() {
        LOG.debug("Initializing HealCheckResponder route");
        return new HealthCheckResponderRoute();
    }

    @Bean
    public HttpInvokerRoute httpInvokerRoute() {
        return new HttpInvokerRoute();
    }

    @Bean
    public SampleFileRoute sampleFileRoute() {
        return new SampleFileRoute();
    }

    @Bean
    public FileProcessingRoute fileProcessingRoute() {
        return new FileProcessingRoute();
    }

    @Bean
    public SqlQueryRoute sqlQueryRoute() {
        return new SqlQueryRoute();
    }



    @Bean
    public JacksonJsonProvider jacksonJsonProvider() {
        return new JacksonJsonProvider(new HALMapper());
    }



    private class HalDataFormatResolver extends DefaultDataFormatResolver {

        private final DataFormat dataFormat;

        public HalDataFormatResolver() {
            JacksonDataFormat jdf = new JacksonDataFormat();
            jdf.setPrettyPrint(true);
            jdf.setObjectMapper(new HALMapper());
            dataFormat = jdf;
        }

        @Override
        public DataFormat resolveDataFormat(String name, CamelContext context) {
            if ("hal+json".equals(name)) {
                return dataFormat;
            } else {
                return super.resolveDataFormat(name, context);
            }
        }

        @Override
        public DataFormat createDataFormat(String name, CamelContext context) {
            if ("hal+json".equals(name)) {
                return dataFormat;
            } else {
                return super.createDataFormat(name, context);
            }
        }
    }
    @Bean
    public MonitorServicesAspect monitorServices() {
        return new MonitorServicesAspect();
    }



}
