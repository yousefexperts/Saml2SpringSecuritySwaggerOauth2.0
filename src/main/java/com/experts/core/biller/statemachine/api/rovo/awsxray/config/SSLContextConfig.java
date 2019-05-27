package com.experts.core.biller.statemachine.api.rovo.awsxray.config;

import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.apache.camel.util.jsse.FilterParameters;
import org.apache.camel.util.jsse.KeyManagersParameters;
import org.apache.camel.util.jsse.KeyStoreParameters;
import org.apache.camel.util.jsse.SSLContextParameters;
import org.apache.camel.util.jsse.SecureSocketProtocolsParameters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.experts.core.biller.statemachine.api.rovo.awsxray.config.settings.HttpServerSettings;
import com.experts.core.biller.statemachine.api.rovo.awsxray.config.settings.KeyStoreSettings;
import com.experts.core.biller.statemachine.api.rovo.awsxray.config.settings.SSLSettings;
import com.experts.core.biller.statemachine.api.rovo.awsxray.config.settings.TrustStoreSettings;

@Configuration
@EnableConfigurationProperties({HttpServerSettings.class, SSLSettings.class, KeyStoreSettings.class, TrustStoreSettings.class})
public class SSLContextConfig {

    @Resource
    private HttpServerSettings serverSettings;


    @Bean(name = "sslContextParameters")
    public SSLContextParameters sslContextParameters() {
        InputStream keyStoreUrl = this.getClass().getResourceAsStream("keystore-biller.jks");
        URL res = this.getClass().getClassLoader().getResource("keystore-biller.jks");

        KeyStoreParameters ksp = new KeyStoreParameters();
        ksp.setResource(res.getPath());
        ksp.setPassword("opc@2018");

        KeyManagersParameters kmp = new KeyManagersParameters();
        kmp.setKeyStore(ksp);
        kmp.setKeyPassword("opc@2018");

        SSLContextParameters scp = new SSLContextParameters();
        scp.setKeyManagers(kmp);


        List<String> supportedSslProtocols = Arrays.asList("TLSv1", "TLSv1.1", "TLSv1.2");
        SecureSocketProtocolsParameters protocolsParameters = new SecureSocketProtocolsParameters();
        protocolsParameters.setSecureSocketProtocol(supportedSslProtocols);
        scp.setSecureSocketProtocols(protocolsParameters);


        FilterParameters cipherParameters = new FilterParameters();
        cipherParameters.getInclude().add(".*");
        cipherParameters.getExclude().add("^.*_(MD5|SHA1)$");
        scp.setCipherSuitesFilter(cipherParameters);


        return scp;
    }
}
