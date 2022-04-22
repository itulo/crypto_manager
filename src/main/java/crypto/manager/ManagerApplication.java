package crypto.manager;

import crypto.manager.configuration.BinanceClientConfigProperties;
import crypto.manager.configuration.BittrexClientConfigProperties;
import crypto.manager.configuration.CoinbaseProClientConfigProperties;
import crypto.manager.configuration.ExchangesProperties;
import crypto.manager.configuration.HitbtcClientConfigProperties;
import crypto.manager.configuration.KrakenClientConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

// need to add this annotation even though these classes use @ConfigurationProperties because java records are implicitly final and spring would have a problem with that otherwise
@EnableConfigurationProperties({ExchangesProperties.class, BinanceClientConfigProperties.class, BittrexClientConfigProperties.class, CoinbaseProClientConfigProperties.class, HitbtcClientConfigProperties.class, KrakenClientConfigProperties.class})
public class ManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}

}
