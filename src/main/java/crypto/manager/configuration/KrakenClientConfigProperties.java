package crypto.manager.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchange.kraken")
public record KrakenClientConfigProperties(String apiKey, String apiSecret) {
}
