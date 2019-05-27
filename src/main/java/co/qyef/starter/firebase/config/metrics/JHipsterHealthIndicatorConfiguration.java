package co.qyef.starter.firebase.config.metrics;

import co.qyef.starter.firebase.config.DatabaseConfigurationStarter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import javax.sql.DataSource;

@Configuration
@Import({ DatabaseConfigurationStarter.class} )
public class JHipsterHealthIndicatorConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public HealthIndicator dbHealthIndicator() {
        return new DatabaseHealthIndicator(dataSource);
    }

}
