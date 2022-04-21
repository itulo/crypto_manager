package crypto.manager.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchanges")
public record ExchangesProperties(String[] clients) {
}

