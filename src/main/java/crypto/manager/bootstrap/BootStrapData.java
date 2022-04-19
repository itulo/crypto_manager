package crypto.manager.bootstrap;

import crypto.manager.configuration.BinanceClientConfigProperties;
import crypto.manager.configuration.CoinbaseProClientConfigProperties;
import crypto.manager.domain.CoinBalance;
import crypto.manager.exchangeclients.BinanceClient;
import crypto.manager.exchangeclients.CoinbaseProClient;
import crypto.manager.repositories.CoinBalanceRepository;
import crypto.manager.repositories.CustomerRepository;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BootStrapData implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(BootStrapData.class);
    //private final CustomerRepository customerRepository;
    private final BinanceClientConfigProperties binanceProps;
    private final CoinBalanceRepository coinBalanceRepository;
    private final CoinbaseProClientConfigProperties coinbaseProProps;

    public BootStrapData(//CustomerRepository customerRepository,
                         BinanceClientConfigProperties binanceProps,
                         CoinBalanceRepository coinBalanceRepository,
                         CoinbaseProClientConfigProperties coinbaseProProps) {
        //this.customerRepository = customerRepository;
        this.binanceProps = binanceProps;
        this.coinBalanceRepository = coinBalanceRepository;
        this.coinbaseProProps = coinbaseProProps;
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

        //List<CoinBalance> coinBalances = new BinanceClient(binanceProps).getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalances);

        //CoinbaseProClient c = new CoinbaseProClient(coinbaseProProps);
        //List<CoinBalance> coinBalances = c.getCoinBalances();
        //coinBalanceRepository.saveAll(coinBalances);

        return;
    }
}
