package crypto.manager.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchanges.hitbtc")
public record HitbtcClientConfigProperties(String apiKey, String apiSecret) {
}
