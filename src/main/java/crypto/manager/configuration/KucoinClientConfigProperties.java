package crypto.manager.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchanges.kucoin")
public record KucoinClientConfigProperties(String apiKey, String apiSecret, String passphrase) {
}
