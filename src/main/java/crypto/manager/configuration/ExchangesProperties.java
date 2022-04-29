package crypto.manager.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchanges")
public record ExchangesProperties(String[] clients,
                                  BinanceClientConfigProperties binance,
                                  BittrexClientConfigProperties bittrex,
                                  CoinbaseProClientConfigProperties coinbasepro,
                                  HitbtcClientConfigProperties hitbtc,
                                  KrakenClientConfigProperties kraken,
                                  KucoinClientConfigProperties kucoin) {
}

