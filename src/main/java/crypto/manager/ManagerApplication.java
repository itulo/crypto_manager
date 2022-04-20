package crypto.manager;

import crypto.manager.configuration.BinanceClientConfigProperties;
import crypto.manager.configuration.BittrexClientConfigProperties;
import crypto.manager.configuration.CoinbaseProClientConfigProperties;
import crypto.manager.configuration.KrakenClientConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication

// need to add this annotation even though these classes use @ConfigurationProperties because java records are implicitly final and spring would have a problem with that otherwise
@EnableConfigurationProperties({BinanceClientConfigProperties.class, BittrexClientConfigProperties.class, CoinbaseProClientConfigProperties.class, KrakenClientConfigProperties.class})
public class ManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}

}
