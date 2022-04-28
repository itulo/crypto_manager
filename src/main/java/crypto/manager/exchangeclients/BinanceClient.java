package crypto.manager.exchangeclients;

import crypto.manager.configuration.BinanceClientConfigProperties;
import crypto.manager.domain.CoinBalance;
import crypto.manager.domain.ExchangeEnum;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class BinanceClient implements ExchangeClient {

    private Exchange binance;

    @Autowired
    public BinanceClient(BinanceClientConfigProperties binanceProps){
        ExchangeSpecification exSpec = new BinanceExchange().getDefaultExchangeSpecification();
        exSpec.setApiKey(binanceProps.apiKey());
        exSpec.setSecretKey(binanceProps.apiSecret());
        binance = ExchangeFactory.INSTANCE.createExchange(exSpec);
    }

    public Collection<CoinBalance> getCoinBalances(){
        Collection<CoinBalance> coinBalances = new ArrayList<>();
        AccountService accountService = binance.getAccountService();
        try {
            AccountInfo accountInfo = accountService.getAccountInfo();

            coinBalances = ExchangeClientHelper.getCoinBalancesWithFunction(
                    accountInfo.getWallet().getBalances().values(),
                    ExchangeEnum.BINANCE,
                    // all coins that are stacked are prefixed by LD, e.g. if you are staking ETH you will have coin LDETH
                    (currencyCode) -> {
                        return currencyCode.startsWith("LD") ? currencyCode.replace("LD", "") : currencyCode;
                    });

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            return coinBalances;
        }
    }
}
