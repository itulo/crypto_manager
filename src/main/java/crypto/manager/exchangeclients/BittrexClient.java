package crypto.manager.exchangeclients;

import crypto.manager.configuration.BittrexClientConfigProperties;
import crypto.manager.domain.CoinBalance;
import crypto.manager.domain.ExchangeEnum;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bittrex.BittrexExchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class BittrexClient implements ExchangeClient {

    private Exchange bittrex;

    @Autowired
    public BittrexClient(BittrexClientConfigProperties bittrexProProps){
        ExchangeSpecification exSpec = new BittrexExchange().getDefaultExchangeSpecification();
        exSpec.setApiKey(bittrexProProps.apiKey());
        exSpec.setSecretKey(bittrexProProps.apiSecret());
        bittrex = ExchangeFactory.INSTANCE.createExchange(exSpec);
    }

    public Collection<CoinBalance> getCoinBalances(){
        List<CoinBalance> coinBalances = new ArrayList<>();
        AccountService accountService = bittrex.getAccountService();
        try {
            AccountInfo accountInfo = accountService.getAccountInfo();

            accountInfo.getWallet().getBalances().forEach((currency, balance) -> {
                if(balance.getAvailable().doubleValue() == 0d) return;

                String currencyName = currency.getCurrencyCode();
                double coinValue = CryptoCompareClient.getCoinValueInEur(currencyName);

                coinBalances.add(
                        new CoinBalance(
                                LocalDate.now(),
                                ExchangeEnum.BITTREX,
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
