package com.experts.core.biller.statemachine.api.auth;

import co.qyef.starter.firebase.creator.firebase.FirebaseAuthenticationProvider;
import co.qyef.starter.firebase.creator.firebase.FirebaseBasicAuthenticationFilter;
import co.qyef.starter.firebase.creator.firebase.FirebaseFilter;
import co.qyef.starter.firebase.creator.firebase.FirebaseResources;
import com.experts.core.biller.statemachine.api.activemq.standers.config.CustomerConfig;
import com.experts.core.biller.statemachine.api.activemq.standers.config.SamlUserDetailsServiceImpl;
import com.experts.core.biller.statemachine.api.activemq.standers.config.WebSecurityConfigSaml;
import com.gismat.test.security.DomainUserDetailsService;
import com.gismat.test.security.jwt.JWTConfigurer;
import com.gismat.test.security.jwt.TokenProvider;
import com.gismat.test.service.FirebaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Import({SamlUserDetailsServiceImpl.class, WebSecurityConfigSaml.class, CustomerConfig.class , FirebaseAuthenticationProvider.class , DomainUserDetailsService.class , SecurityProblemSupport.class , TokenProvider.class})
@Order(2000)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    @Autowired
    private  AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private FirebaseAuthenticationProvider authenticationProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private SecurityProblemSupport problemSupport;



    private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

            .csrf().disable()
            .headers()
            .frameOptions().disable()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/signin").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()


                .formLogin()
                .loginPage("/swagger-ui.html").permitAll()
                .and()
                .logout()
                .permitAll()
        ;

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
                .antMatchers(
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources/configuration/security")
                .permitAll()
                .antMatchers("/selectUserName").permitAll()
                .antMatchers("/core").authenticated()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/**").permitAll()
            .antMatchers("/static/**").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/ws/BillPullRequest/**").permitAll()
                .antMatchers("/ws/NotificationRequest/**").permitAll()
                .antMatchers("/ws/PrePaidRequest/**").permitAll()
                .antMatchers("/ws/PaymentRequest/**").permitAll()
                .antMatchers("/ws/InquiryRequest/**").permitAll()
                .anyRequest().permitAll();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    @Bean
    @Primary
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    /*@Bean
    public KerberosAuthenticationProvider kerberosAuthenticationProvider() {
        KerberosAuthenticationProvider provider = new KerberosAuthenticationProvider();
        SunJaasKerberosClient client = new SunJaasKerberosClient();
        client.setDebug(true);
        provider.setKerberosClient(client);
        provider.setUserDetailsService(dummyUserDetailsService());
        return provider;
    }

    @Bean
    public SpnegoEntryPoint spnegoEntryPoint() {
        return new SpnegoEntryPoint("/signin");
    }

    @Bean
    public SpnegoAuthenticationProcessingFilter spnegoAuthenticationProcessingFilter(AuthenticationManager authenticationManager) throws Exception {
        SpnegoAuthenticationProcessingFilter filter = new SpnegoAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Bean
    public KerberosServiceAuthenticationProvider kerberosServiceAuthenticationProvider() {
        KerberosServiceAuthenticationProvider provider = new KerberosServiceAuthenticationProvider();
        provider.setTicketValidator(sunJaasKerberosTicketValidator());
        provider.setUserDetailsService(dummyUserDetailsService());
        return provider;
    }

    @Bean
    public SunJaasKerberosTicketValidator sunJaasKerberosTicketValidator() {
        SunJaasKerberosTicketValidator ticketValidator = new SunJaasKerberosTicketValidator();
        ticketValidator.setServicePrincipal(servicePrincipal);
        ticketValidator.setKeyTabLocation(new FileSystemResource(keytabLocation));
        ticketValidator.setDebug(true);
        return ticketValidator;
    }

    @Bean
    public DummyUserDetailsService dummyUserDetailsService() {
        return new DummyUserDetailsService();
    }*/




    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }



    @Autowired(required = false)
    private FirebaseService firebaseService;

    private FirebaseFilter tokenAuthorizationFilter() {
        return new FirebaseFilter(firebaseService);
    }

    private FirebaseBasicAuthenticationFilter firebaseBasicAuthenticationFilter(){
        try {
            return new FirebaseBasicAuthenticationFilter(this.authenticationManager(),tokenProvider);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
