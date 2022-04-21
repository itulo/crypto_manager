package crypto.manager.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchanges.bittrex")
public record BittrexClientConfigProperties(String apiKey, String apiSecret) {
}
