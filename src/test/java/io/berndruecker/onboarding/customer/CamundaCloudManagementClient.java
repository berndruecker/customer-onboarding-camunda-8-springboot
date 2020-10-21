package io.berndruecker.onboarding.customer;

import io.zeebe.clustertestbench.cloud.CloudAPIClient;
import io.zeebe.clustertestbench.cloud.CloudAPIClientFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "camundacloud")
public class CamundaCloudManagementClient {

    private String cloudApiUrl;
    private String cloudApiAuthenticationServerURL;
    private String cloudApiAudience;
    private String cloudApiClientId;
    private String cloudApiClientSecret;

    @Bean
    public CloudAPIClient camundaCloudApiClient() {
        return new CloudAPIClientFactory().createCloudAPIClient(
                cloudApiUrl,
                cloudApiAuthenticationServerURL,
                cloudApiAudience,
                cloudApiClientId,
                cloudApiClientSecret);
    }

    public void setCloudApiUrl(String cloudApiUrl) {
        this.cloudApiUrl = cloudApiUrl;
    }

    public void setCloudApiAuthenticationServerURL(String cloudApiAuthenticationServerURL) {
        this.cloudApiAuthenticationServerURL = cloudApiAuthenticationServerURL;
    }

    public void setCloudApiAudience(String cloudApiAudience) {
        this.cloudApiAudience = cloudApiAudience;
    }

    public void setCloudApiClientId(String cloudApiClientId) {
        this.cloudApiClientId = cloudApiClientId;
    }

    public void setCloudApiClientSecret(String cloudApiClientSecret) {
        this.cloudApiClientSecret = cloudApiClientSecret;
    }

    public String getCloudApiUrl() {
        return cloudApiUrl;
    }

    public String getCloudApiAuthenticationServerURL() {
        return cloudApiAuthenticationServerURL;
    }

    public String getCloudApiAudience() {
        return cloudApiAudience;
    }

    public String getCloudApiClientId() {
        return cloudApiClientId;
    }

    public String getCloudApiClientSecret() {
        return cloudApiClientSecret;
    }
}
