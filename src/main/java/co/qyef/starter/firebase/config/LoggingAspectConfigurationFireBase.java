package co.qyef.starter.firebase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

import com.gismat.test.aop.logging.LoggingAspect;



@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfigurationFireBase {

    @Bean
    @Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
