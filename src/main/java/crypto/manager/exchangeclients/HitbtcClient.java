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
import java.util.ArrayList;
import java.util.Collection;

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
        Collection<CoinBalance> coinBalances = new ArrayList<>();
        AccountService accountService = hitbtc.getAccountService();
        try {
            AccountInfo accountInfo = accountService.getAccountInfo();

            coinBalances = ExchangeClientHelper.getCoinBalances(
                    accountInfo.getWallet().getBalances(),
                    ExchangeEnum.HITBTC);

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            return coinBalances;
        }
    }
}
