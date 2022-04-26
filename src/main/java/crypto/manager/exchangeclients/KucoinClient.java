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
import java.util.ArrayList;
import java.util.Collection;

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
        Collection<CoinBalance> coinBalances = new ArrayList<>();
        AccountService accountService = kucoin.getAccountService();
        try {
            AccountInfo accountInfo = accountService.getAccountInfo();

            coinBalances = ExchangeClientHelper.getCoinBalances(
                    accountInfo.getWallet("main").getBalances(),
                    ExchangeEnum.KUCOIN);

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            return coinBalances;
        }
    }
}
