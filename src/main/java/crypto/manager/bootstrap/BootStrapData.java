package crypto.manager.bootstrap;

import crypto.manager.configuration.ExchangesProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final ExchangesProperties exchangesProps;

    public BootStrapData(ExchangesProperties exchangesProps) {
        this.exchangesProps = exchangesProps;
    }

    @Override
    public void run(String... args) throws Exception {
        //Collection<CoinBalance> coinBalancesB = new BinanceClient(exchangesProps.binance()).getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalancesB);

        //CoinbaseProClient c = new CoinbaseProClient(exchangesProps.coinbasepro());
        //Collection<CoinBalance> coinBalancesC = c.getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalancesC);

        //KrakenClient k = new KrakenClient(exchangesProps.kraken());
        //Collection<CoinBalance> coinBalancesK = k.getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalancesK);

        //BittrexClient btt = new BittrexClient(exchangesProps.bittrex());
        //Collection<CoinBalance> coinBalancesBtt = btt.getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalancesBtt);

        //HitbtcClient h = new HitbtcClient(exchangesProps.hitbtc());
        //Collection<CoinBalance> coinBalancesH = h.getCoinBalances();

        //KucoinClient ku = new KucoinClient(exchangesProps.kucoin());
        //Collection<CoinBalance> coinBalancesKu = ku.getCoinBalances();

        return;
    }
}
