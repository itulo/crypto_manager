package crypto.manager.exchangeclients;

import crypto.manager.configuration.HitbtcClientConfigProperties;
import crypto.manager.domain.CoinBalance;
import crypto.manager.domain.ExchangeEnum;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.hitbtc.v2.HitbtcExchange;
import org.knowm.xchange.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HitbtcClient implements ExchangeClient {

    private Exchange hitbtc;

    @Autowired
    public HitbtcClient(HitbtcClientConfigProperties hitbtcProProps){
        ExchangeSpecification exSpec = new HitbtcExchange().getDefaultExchangeSpecification();
        exSpec.setApiKey(hitbtcProProps.apiKey());
        exSpec.setSecretKey(hitbtcProProps.apiSecret());
        hitbtc = ExchangeFactory.INSTANCE.createExchange(exSpec);
    }

    public Collection<CoinBalance> getCoinBalances(){
        List<CoinBalance> coinBalances = new ArrayList<>();
        AccountService accountService = hitbtc.getAccountService();
        try {
            AccountInfo accountInfo = accountService.getAccountInfo();

            accountInfo.getWallet().getBalances().forEach((currency, balance) -> {
                if(balance.getAvailable().doubleValue() == 0d) return;

                String currencyName = currency.getCurrencyCode();
                double coinValue = CryptoCompareClient.getCoinValueInEur(currencyName);

                coinBalances.add(
                        new CoinBalance(
                                LocalDate.now(),
                                ExchangeEnum.HITBTC,
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
