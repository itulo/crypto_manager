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
import java.util.ArrayList;
import java.util.Collection;

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

    public Collection<CoinBalance> getCoinBalances(){
        Collection<CoinBalance> coinBalances = new ArrayList<>();
        AccountService accountService = coinbase.getAccountService();
        try {
            AccountInfo accountInfo = accountService.getAccountInfo();

            // TODO allow to choose a specific portfolio
            coinBalances = ExchangeClientHelper.getCoinBalances(
                    accountInfo.getWallet().getBalances(),
                    ExchangeEnum.COINBASEPRO);

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            return coinBalances;
        }
    }
}
