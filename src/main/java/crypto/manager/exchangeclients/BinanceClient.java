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
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
        Map<String, CoinBalance> coinBalancesByCoin = new HashMap<>();
        AccountService accountService = binance.getAccountService();
        try {
            AccountInfo accountInfo = accountService.getAccountInfo();

            accountInfo.getWallet().getBalances().values().forEach( balance -> {
                if(balance.getAvailable().doubleValue() == 0d) return;

                String currencyCode = balance.getCurrency().getCurrencyCode();
                // all coins that are stacked are prefixed by LD, e.g. if you are staking ETH you will have coin LDETH
                String coin = currencyCode.startsWith("LD") ? currencyCode.replace("LD", "") : currencyCode;

                double coinValue = CryptoCompareClient.getCoinValueInEur(coin);

                // because you can have the same coin in your Spot wallet (e.g. ETH) and your Earn wallet (e.g. LDETH)
                // we need to sum them
                CoinBalance newCoinBalance;
                if (coinBalancesByCoin.containsKey(coin)){
                    CoinBalance duplicateCoinBalance = coinBalancesByCoin.get(coin);
                    newCoinBalance =
                            new CoinBalance(
                                    LocalDate.now(),
                                    ExchangeEnum.BINANCE,
                                    coin,
                                    balance.getTotal().doubleValue() + duplicateCoinBalance.getAmount(),
                                    coinValue,
                                    "EUR"
                            );
                } else {
                    newCoinBalance =
                            new CoinBalance(
                                    LocalDate.now(),
                                    ExchangeEnum.BINANCE,
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
