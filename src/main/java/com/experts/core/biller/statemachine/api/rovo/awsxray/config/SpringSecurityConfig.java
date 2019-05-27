package com.experts.core.biller.statemachine.api.rovo.awsxray.config;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.AWSXRayRecorderBuilder;
import com.amazonaws.xray.plugins.EC2Plugin;
import com.experts.core.biller.statemachine.api.rovo.awsxray.security.*;
import com.google.common.base.Joiner;
import org.apache.camel.component.spring.security.SpringSecurityAccessPolicy;
import org.apache.camel.component.spring.security.SpringSecurityAuthorizationPolicy;
import org.apache.camel.spi.HeaderFilterStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
@Order(-1000)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    static {
        AWSXRayRecorderBuilder builder = AWSXRayRecorderBuilder.standard().withPlugin(new EC2Plugin());

        AWSXRay.setGlobalRecorder(builder.build());
    }

    public SpringSecurityConfig() {
        super();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userKeyAuthProvider())
                .userDetailsService(appUserDetailsService());
    }

    @Bean(name = "userKeyAuthProvider")
    public AuthenticationProvider userKeyAuthProvider() {
        return new UserKeyAuthenticationProvider();
    }

    @Bean(name = "passwordAuthProvider")
    public AuthenticationProvider passwordAuthProvider() {
        return new PasswordAuthenticationProvider();
    }

    @Bean
    public UserDetailsService appUserDetailsService() {
        return new AppUserDetailsService();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = new ArrayList<>();
        decisionVoters.add(new RoleVoter());
        return new AffirmativeBased(decisionVoters);
    }

    @Bean
    public SpringSecurityContextLoader contextLoader() {
        return new SpringSecurityContextLoader();
    }

    @Bean(name = AuthFilterStrategy.ID)
    public HeaderFilterStrategy authHeaderFilter() {
        return new AuthFilterStrategy();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();



        String hierarchy = Joiner.on(' ').join(
                "ROLE_SUPER ADMIN      > ROLE_READ_ONLY_ADMIN",
                "ROLE_READ_ONLY_ADMIN  > ROLE_BILLING_ADMIN",
                "ROLE_BILLING_ADMIN    > ROLE_ADMIN",
                "ROLE_ADMIN            > ROLE_USER",
                "ROLE_USER             > ROLE_UNAUTHENTICATED");


        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public RoleHierarchyVoter roleHierarchyVoter() {
        return new RoleHierarchyVoter(roleHierarchy());
    }

    @Bean(name = "authenticated")
    public SpringSecurityAuthorizationPolicy authenticated_access() throws Exception {
        SpringSecurityAuthorizationPolicy policy = new SpringSecurityAuthorizationPolicy();
        policy.setId("authenticated");
        policy.setAuthenticationManager(authenticationManagerBean());
        policy.setAccessDecisionManager(accessDecisionManager());
        policy.setSpringSecurityAccessPolicy(new SpringSecurityAccessPolicy("ROLE_USER,ROLE_ADMIN"));
        return policy;
    }

    @Bean(name = "admin_access")
    public SpringSecurityAuthorizationPolicy admin_access() throws Exception {
        SpringSecurityAuthorizationPolicy policy = new SpringSecurityAuthorizationPolicy();
        policy.setId("admin_access");
        policy.setAuthenticationManager(authenticationManagerBean());
        policy.setAccessDecisionManager(accessDecisionManager());
        policy.setSpringSecurityAccessPolicy(new SpringSecurityAccessPolicy("ROLE_ADMIN"));
        return policy;
    }

}
