package crypto.manager.bootstrap;

import crypto.manager.configuration.BinanceClientConfigProperties;
import crypto.manager.configuration.BittrexClientConfigProperties;
import crypto.manager.configuration.CoinbaseProClientConfigProperties;
import crypto.manager.configuration.ExchangesProperties;
import crypto.manager.configuration.HitbtcClientConfigProperties;
import crypto.manager.configuration.KrakenClientConfigProperties;
import crypto.manager.configuration.KucoinClientConfigProperties;
import crypto.manager.repositories.CoinBalanceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final BinanceClientConfigProperties binanceProps;
    private final CoinBalanceRepository coinBalanceRepository;
    private final CoinbaseProClientConfigProperties coinbaseProProps;
    private final HitbtcClientConfigProperties hitbtcProps;
    private final KrakenClientConfigProperties krakenProps;
    private final KucoinClientConfigProperties kucoinProps;
    private final BittrexClientConfigProperties bittrexProps;
    private final ExchangesProperties exchangesProps;

    public BootStrapData(BinanceClientConfigProperties binanceProps,
                         CoinBalanceRepository coinBalanceRepository,
                         CoinbaseProClientConfigProperties coinbaseProProps,
                         HitbtcClientConfigProperties hitbtcProps,
                         KrakenClientConfigProperties krakenProps,
                         BittrexClientConfigProperties bittrexProps,
                         KucoinClientConfigProperties kucoinProps,
                         ExchangesProperties exchangesProps) {
        this.binanceProps = binanceProps;
        this.coinBalanceRepository = coinBalanceRepository;
        this.coinbaseProProps = coinbaseProProps;
        this.hitbtcProps = hitbtcProps;
        this.krakenProps = krakenProps;
        this.bittrexProps = bittrexProps;
        this.kucoinProps = kucoinProps;
        this.exchangesProps = exchangesProps;
    }

    @Override
    public void run(String... args) throws Exception {
        //Collection<CoinBalance> coinBalancesB = new BinanceClient(binanceProps).getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalancesB);

        //CoinbaseProClient c = new CoinbaseProClient(coinbaseProProps);
        //Collection<CoinBalance> coinBalancesC = c.getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalancesC);

        //KrakenClient k = new KrakenClient(krakenProps);
        //Collection<CoinBalance> coinBalancesK = k.getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalancesK);

        //BittrexClient btt = new BittrexClient(bittrexProps);
        //Collection<CoinBalance> coinBalancesBtt = btt.getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalancesBtt);

        //HitbtcClient h = new HitbtcClient(hitbtcProps);
        //Collection<CoinBalance> coinBalancesH = h.getCoinBalances();

        //KucoinClient ku = new KucoinClient(kucoinProps);
        //Collection<CoinBalance> coinBalancesKu = ku.getCoinBalances();

        return;
    }
}
