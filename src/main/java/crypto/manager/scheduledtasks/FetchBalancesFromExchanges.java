package crypto.manager.scheduledtasks;

import crypto.manager.configuration.BinanceClientConfigProperties;
import crypto.manager.configuration.BittrexClientConfigProperties;
import crypto.manager.configuration.CoinbaseProClientConfigProperties;
import crypto.manager.configuration.ExchangesProperties;
import crypto.manager.configuration.HitbtcClientConfigProperties;
import crypto.manager.configuration.KrakenClientConfigProperties;
import crypto.manager.domain.CoinBalance;
import crypto.manager.exchangeclients.BinanceClient;
import crypto.manager.exchangeclients.BittrexClient;
import crypto.manager.exchangeclients.CoinbaseProClient;
import crypto.manager.exchangeclients.ExchangeClient;
import crypto.manager.exchangeclients.HitbtcClient;
import crypto.manager.exchangeclients.KrakenClient;
import crypto.manager.services.CoinBalanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Scheduled task to fetch balances from exchanges once an hour
 */
@Component
public class FetchBalancesFromExchanges {
    private static final Logger log = LoggerFactory.getLogger(FetchBalancesFromExchanges.class);

    private final CoinBalanceService coinBalanceService;
    private final BinanceClientConfigProperties binanceProps;
    private final CoinbaseProClientConfigProperties coinbaseProProps;
    private final HitbtcClientConfigProperties hitbtcProps;
    private final KrakenClientConfigProperties krakenProps;
    private final BittrexClientConfigProperties bittrexProps;
    private final ExchangesProperties exchangesProps;

    public FetchBalancesFromExchanges(CoinBalanceService coinBalanceService,
                                      BinanceClientConfigProperties binanceProps,
                                      CoinbaseProClientConfigProperties coinbaseProProps,
                                      HitbtcClientConfigProperties hitbtcProps,
                                      KrakenClientConfigProperties krakenProps,
                                      BittrexClientConfigProperties bittrexProps,
                                      ExchangesProperties exchangesProps) {
        this.coinBalanceService = coinBalanceService;
        this.binanceProps = binanceProps;
        this.coinbaseProProps = coinbaseProProps;
        this.hitbtcProps = hitbtcProps;
        this.krakenProps = krakenProps;
        this.bittrexProps = bittrexProps;
        this.exchangesProps = exchangesProps;
    }

    private ExchangeClient getClient(String client){
        return switch(client){
            case "binance" -> new BinanceClient(this.binanceProps);
            case "bittrex" -> new BittrexClient(this.bittrexProps);
            case "coinbasepro" -> new CoinbaseProClient(this.coinbaseProProps);
            case "hitbtc" -> new HitbtcClient(this.hitbtcProps);
            case "kraken" -> new KrakenClient(this.krakenProps);
            default -> throw new IllegalArgumentException(client+" is not a valid client exchange");
        };
    }

    @Scheduled(fixedRate = 3600000) // 1 hour
    public void fetchBalancesFromExchanges() {
        for (String exchange : this.exchangesProps.clients()){
            ExchangeClient client = getClient(exchange);
            Collection<CoinBalance> coinBalances = client.getCoinBalances();

            // TODO update only the rows that changed
            Collection<CoinBalance> savedCoinBalances = this.coinBalanceService.replaceExchangeCoinBalancesToday(exchange, coinBalances);

            if(coinBalances.size() != savedCoinBalances.size()){
                log.warn("Fetched from exchange {} {} coin balances but only {} were saved", exchange, coinBalances.size(), savedCoinBalances.size());
            } else {
                log.info("exchange {}: updated with {} coin balances", exchange, coinBalances.size());
            }
        }
    }
}
