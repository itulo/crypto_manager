package crypto.manager.exchangeclients;

import crypto.manager.configuration.KrakenClientConfigProperties;
import crypto.manager.domain.CoinBalance;
import crypto.manager.domain.ExchangeEnum;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<String, CoinBalance> coinBalancesByCoin = new HashMap<>();
        AccountService accountService = kraken.getAccountService();
        try {
            AccountInfo accountInfo = accountService.getAccountInfo();

            accountInfo.getWallet("margin").getBalances().values().forEach( balance -> {
                if(balance.getAvailable().doubleValue() == 0d) return;

                String currencyCode = balance.getCurrency().getCurrencyCode();
                // all coins that are stacked are postfixed by .S, e.g. if you are staking ETH you will have coin ETH.S
                String coin = currencyCode.endsWith(".S") ? currencyCode.replace(".S", "") : currencyCode;

                double coinValue = CryptoCompareClient.getCoinValueInEur(coin);

                // because you can have the same coin in your Spot wallet (e.g. ETH) and your Earn wallet (e.g. LDETH)
                // we need to sum them
                CoinBalance newCoinBalance;
                if (coinBalancesByCoin.containsKey(coin)){
                    CoinBalance duplicateCoinBalance = coinBalancesByCoin.get(coin);
                    newCoinBalance =
                            new CoinBalance(
                                    LocalDate.now(),
                                    ExchangeEnum.KRAKEN,
                                    coin,
                                    balance.getTotal().doubleValue() + duplicateCoinBalance.getAmount(),
                                    coinValue,
                                    "EUR"
                            );
                } else {
                    newCoinBalance =
                            new CoinBalance(
                                    LocalDate.now(),
                                    ExchangeEnum.KRAKEN,
                                    coin,
                                    balance.getTotal().doubleValue(),
                                    coinValue,
                                    "EUR"
                            );
                }
                coinBalancesByCoin.put(coin, newCoinBalance);

            });
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            return coinBalancesByCoin.values();
        }
    }
}
