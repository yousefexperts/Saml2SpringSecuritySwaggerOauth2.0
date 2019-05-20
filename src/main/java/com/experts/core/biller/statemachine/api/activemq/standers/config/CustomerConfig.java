package com.experts.core.biller.statemachine.api.activemq.standers.config;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jms.AtomikosConnectionFactoryBean;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.apache.commons.dbcp.BasicDataSource;
import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.jms.ConnectionFactory;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableJpaRepositories(basePackages = {"com.experts.core.biller.statemachine.api.gigaspace.daos.repo"})
@EnableJpaAuditing
@EnableTransactionManagement
public class CustomerConfig {

    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;

    @Bean
    @Primary
    public DataSource dataSource() throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setURL("jdbc:mysql://localhost:3306/pdpserver?serverTimezone=UTC");
        mysqlXaDataSource.setUser("root");
        mysqlXaDataSource.setPassword("opc@2018");
        mysqlXaDataSource.setCharacterEncoding("UTF-8");
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("datasource2");
        return xaDataSource;
    }

    @Bean
    @Primary
    public ConnectionFactory connectionFactory() throws Exception {
        BrokerService brokerService = new BrokerService();
        brokerService.setPersistent(false);
        brokerService.setUseJmx(false);
        brokerService.setAdvisorySupport(false);
        brokerService.setSchedulerSupport(false);
        TransportConnector connector = brokerService.addConnector("vm://localhost");
        brokerService.start();
        String connectionUri = connector.getPublishableConnectString();
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(connectionUri);
        ActiveMQXAConnectionFactory activeMQXAConnectionFactory = new ActiveMQXAConnectionFactory(connectionUri);
        activeMQXAConnectionFactory.setUser(DistTxConstants.USERNAME);
        activeMQXAConnectionFactory.setPassword(DistTxConstants.PASSWD);
        AtomikosConnectionFactoryBean atomikosConnectionFactoryBean = new AtomikosConnectionFactoryBean();
        atomikosConnectionFactoryBean.setUniqueResourceName(DistTxConstants.UNIQUE_RESOURCE_NAME_MESSAGE_QUEUE);
        atomikosConnectionFactoryBean.setLocalTransactionMode(false);
        atomikosConnectionFactoryBean.setXaConnectionFactory(activeMQXAConnectionFactory);
        atomikosConnectionFactoryBean.setMinPoolSize(25);
        atomikosConnectionFactoryBean.setMaxPoolSize(50);
        return atomikosConnectionFactoryBean;
    }

    @Bean("transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    private Properties props() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        return properties;
    }

    @Bean("entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        final LocalContainerEntityManagerFactoryBean build = new LocalContainerEntityManagerFactoryBean();
        build.setPackagesToScan("com.experts.core.biller.statemachine.api.model.domain.jpa");
        build.setPersistenceUnitName("EXPERTS-MYSQL");
        build.setPersistenceXmlLocation("classpath:META-INF/persistence-dev.xml");
        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.transaction.jta.platform", AtomikFactoryBean.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");
        build.setJtaDataSource(dataSource());
        build.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        return build;
    }

    @Bean
    public JpaVendorAdapter hibernateJpaVendorAdapter() {
        final HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        jpaAdapter.setShowSql(true);
        return jpaAdapter;
    }

}
