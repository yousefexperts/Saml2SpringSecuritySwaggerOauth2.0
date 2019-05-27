package com.experts.core.biller.statemachine.api.rovo.awsxray.settings;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "trustStore")
public class TrustStoreSettings {

    private String resource;
    private String password;
}
