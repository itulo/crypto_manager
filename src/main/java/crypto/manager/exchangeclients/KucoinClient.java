package crypto.manager.exchangeclients;

import crypto.manager.configuration.KucoinClientConfigProperties;
import crypto.manager.domain.CoinBalance;
import crypto.manager.domain.ExchangeEnum;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.kucoin.KucoinExchange;
import org.knowm.xchange.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KucoinClient implements ExchangeClient {

    private Exchange kucoin;

    @Autowired
    public KucoinClient(KucoinClientConfigProperties kucoinProps){
        ExchangeSpecification exSpec = new KucoinExchange().getDefaultExchangeSpecification();
        exSpec.setApiKey(kucoinProps.apiKey());
        exSpec.setSecretKey(kucoinProps.apiSecret());
        exSpec.setExchangeSpecificParametersItem("passphrase", kucoinProps.passphrase());
        kucoin = ExchangeFactory.INSTANCE.createExchange(exSpec);
    }

    public Collection<CoinBalance> getCoinBalances(){
        List<CoinBalance> coinBalances = new ArrayList<>();
        AccountService accountService = kucoin.getAccountService();
        try {
            AccountInfo accountInfo = accountService.getAccountInfo();

            accountInfo.getWallet("main").getBalances().forEach((currency, balance) -> {
                if(balance.getAvailable().doubleValue() == 0d) return;

                String currencyName = currency.getCurrencyCode();
                double coinValue = CryptoCompareClient.getCoinValueInEur(currencyName);

                coinBalances.add(
                        new CoinBalance(
                                LocalDate.now(),
                                ExchangeEnum.KUCOIN,
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
