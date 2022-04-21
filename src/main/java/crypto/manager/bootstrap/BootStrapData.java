package crypto.manager.bootstrap;

import crypto.manager.configuration.BinanceClientConfigProperties;
import crypto.manager.configuration.BittrexClientConfigProperties;
import crypto.manager.configuration.CoinbaseProClientConfigProperties;
import crypto.manager.configuration.ExchangesProperties;
import crypto.manager.configuration.KrakenClientConfigProperties;
import crypto.manager.domain.CoinBalance;
import crypto.manager.exchangeclients.KrakenClient;
import crypto.manager.repositories.CoinBalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class BootStrapData implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(BootStrapData.class);
    //private final CustomerRepository customerRepository;
    private final BinanceClientConfigProperties binanceProps;
    private final CoinBalanceRepository coinBalanceRepository;
    private final CoinbaseProClientConfigProperties coinbaseProProps;
    private final KrakenClientConfigProperties krakenProps;
    private final BittrexClientConfigProperties bittrexProps;
    private final ExchangesProperties exchangesProps;

    public BootStrapData(//CustomerRepository customerRepository,
                         BinanceClientConfigProperties binanceProps,
                         CoinBalanceRepository coinBalanceRepository,
                         CoinbaseProClientConfigProperties coinbaseProProps,
                         KrakenClientConfigProperties krakenProps,
                         BittrexClientConfigProperties bittrexProps,
                         ExchangesProperties exchangesProps) {
        //this.customerRepository = customerRepository;
        this.binanceProps = binanceProps;
        this.coinBalanceRepository = coinBalanceRepository;
        this.coinbaseProProps = coinbaseProProps;
        this.krakenProps = krakenProps;
        this.bittrexProps = bittrexProps;
        this.exchangesProps = exchangesProps;
    }

    @Override
    public void run(String... args) throws Exception {
        /*logger.info("Loading customer data");

        Customer c1 = new Customer();
        c1.setFirstname("Italo");
        c1.setLastname("Ar");
        customerRepository.save(c1);

        Customer c2 = new Customer();
        c2.setFirstname("Anna-Kaisa");
        c2.setLastname("Ar");
        customerRepository.save(c2);

        Customer c3 = new Customer();
        c3.setFirstname("Nooa");
        c3.setLastname("Ar");
        customerRepository.save(c3);

        logger.info("Customers saved: {}", customerRepository.count());*/

        // convert coin to eur
        // https://min-api.cryptocompare.com/data/price?fsym=REEF&tsyms=EUR

        //Collection<CoinBalance> coinBalancesB = new BinanceClient(binanceProps).getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalancesB);

        //CoinbaseProClient c = new CoinbaseProClient(coinbaseProProps);
        //Collection<CoinBalance> coinBalancesC = c.getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalancesC);

        KrakenClient k = new KrakenClient(krakenProps);
        Collection<CoinBalance> coinBalancesK = k.getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalancesK);

        //BittrexClient btt = new BittrexClient(bittrexProps);
        //Collection<CoinBalance> coinBalancesBtt = btt.getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalancesBtt);

        return;
    }
}
