package crypto.manager.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchange.binance")
public record BinanceClientConfigProperties(String apiKey, String apiSecret) {
}
