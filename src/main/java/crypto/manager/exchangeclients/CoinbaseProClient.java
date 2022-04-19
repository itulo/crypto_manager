package crypto.manager.exchangeclients;

import crypto.manager.configuration.CoinbaseProClientConfigProperties;
import crypto.manager.domain.CoinBalance;
import crypto.manager.domain.ExchangeEnum;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CoinbaseProClient implements ExchangeClient {

    private Exchange coinbase;

    @Autowired
    public CoinbaseProClient(CoinbaseProClientConfigProperties coinbaseProProps){
        ExchangeSpecification exSpec = new CoinbaseProExchange().getDefaultExchangeSpecification();
        exSpec.setApiKey(coinbaseProProps.apiKey());
        exSpec.setSecretKey(coinbaseProProps.apiSecret());
        exSpec.setExchangeSpecificParametersItem("passphrase", coinbaseProProps.passphrase());
        coinbase = ExchangeFactory.INSTANCE.createExchange(exSpec);
    }

    public List<CoinBalance> getCoinBalances(){
        List<CoinBalance> coinBalances = new ArrayList<>();
        AccountService accountService = coinbase.getAccountService();
        try {
            AccountInfo accountInfo = accountService.getAccountInfo();

            // TODO allow to choose a specific portfolio
            accountInfo.getWallet().getBalances().forEach((currency, balance) -> {
                if(balance.getAvailable().doubleValue() == 0d) return;

                String currencyName = currency.getCurrencyCode();
                double coinValue = CryptoCompareClient.getCoinValueInEur(currencyName);

                coinBalances.add(
                        new CoinBalance(
                                LocalDate.now(),
                                ExchangeEnum.COINBASE_PRO,
                                currencyName,
                                balance.getTotal().doubleValue(),
                                coinValue,
                                "EUR"
                        )
                );
            });
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            return coinBalances;
        }
    }
}
