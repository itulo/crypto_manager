package crypto.manager.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchanges.coinbasepro")
public record CoinbaseProClientConfigProperties(String apiKey, String apiSecret, String passphrase)  {
}