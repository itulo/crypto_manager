package crypto.manager.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchanges.kraken")
public record KrakenClientConfigProperties(String apiKey, String apiSecret) {
}
