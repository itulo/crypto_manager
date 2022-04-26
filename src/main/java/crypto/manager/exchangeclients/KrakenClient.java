package crypto.manager.exchangeclients;

import crypto.manager.configuration.KrakenClientConfigProperties;
import crypto.manager.domain.CoinBalance;
import crypto.manager.domain.ExchangeEnum;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class KrakenClient implements ExchangeClient{

    private Exchange kraken;

    @Autowired
    public KrakenClient(KrakenClientConfigProperties krakenProps){
        ExchangeSpecification exSpec = new KrakenExchange().getDefaultExchangeSpecification();
        exSpec.setApiKey(krakenProps.apiKey());
        exSpec.setSecretKey(krakenProps.apiSecret());
        kraken = ExchangeFactory.INSTANCE.createExchange(exSpec);
    }

    @Override
    public Collection<CoinBalance> getCoinBalances(){
        Collection<CoinBalance> coinBalances = new ArrayList<>();
        AccountService accountService = kraken.getAccountService();
        try {
            AccountInfo accountInfo = accountService.getAccountInfo();

            coinBalances = ExchangeClientHelper.getCoinBalancesWithFunction(
                    accountInfo.getWallet("margin").getBalances().values(),
                    ExchangeEnum.KRAKEN,
                    (currencyCode) -> {
                        return currencyCode.endsWith(".S") ? currencyCode.replace(".S", "") : currencyCode;
                    });

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            return coinBalances;
        }
    }
}
