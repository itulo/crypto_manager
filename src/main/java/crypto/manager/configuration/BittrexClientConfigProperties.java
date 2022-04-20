package crypto.manager.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchange.bittrex")
public record BittrexClientConfigProperties(String apiKey, String apiSecret) {
}
