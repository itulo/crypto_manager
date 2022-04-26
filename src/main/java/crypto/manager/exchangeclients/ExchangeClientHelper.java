package crypto.manager.exchangeclients;

import crypto.manager.domain.CoinBalance;
import crypto.manager.domain.ExchangeEnum;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ExchangeClientHelper {

    /**
     * Provide a list of coin balances from the balances received from the exchange
     *
     * @param balances
     * @param exchange
     * @return
     */
    public static Collection<CoinBalance> getCoinBalances(Map<Currency, Balance> balances,
                                                    ExchangeEnum exchange
                                                    ){
        List<CoinBalance> coinBalances = new ArrayList<>();

        balances.forEach((currency, balance) -> {
            if(balance.getAvailable().doubleValue() == 0d) return;

            String currencyName = currency.getCurrencyCode();
            double coinValue = CryptoCompareClient.getCoinValueInEur(currencyName);

            coinBalances.add(
                    new CoinBalance(
                            LocalDate.now(),
                            exchange,
                            currencyName,
                            balance.getTotal().doubleValue(),
                            coinValue,
                            "EUR"
                    )
            );
        });

        return coinBalances;
    }

    /**
     * Provide a list of coin balances from the balances received from the exchange
     * With a function manipulate coin names that are different when they are in spot wallet or earn/staking wallet
     *
     * @param balances
     * @param exchange
     * @return
     */
    public static Collection<CoinBalance> getCoinBalancesWithFunction(Collection<Balance> balances,
                                                                ExchangeEnum exchange,
                                                                Function<String, String> exctractCoinName
    ){
        Map<String, CoinBalance> coinBalancesByCoin = new HashMap<>();

        balances.forEach( balance -> {
            if(balance.getAvailable().doubleValue() == 0d) return;

            //String currencyCode = balance.getCurrency().getCurrencyCode();
            // all coins that are stacked are prefixed by LD, e.g. if you are staking ETH you will have coin LDETH
            //String coin = currencyCode.startsWith("LD") ? currencyCode.replace("LD", "") : currencyCode;
            String coin = exctractCoinName.apply(balance.getCurrency().getCurrencyCode());

            double coinValue = CryptoCompareClient.getCoinValueInEur(coin);

            // because you can have the same coin in your Spot wallet (e.g. ETH) and your Earn wallet (e.g. LDETH)
            // we need to sum them
            CoinBalance newCoinBalance;
            if (coinBalancesByCoin.containsKey(coin)){
                CoinBalance duplicateCoinBalance = coinBalancesByCoin.get(coin);
                newCoinBalance =
                        new CoinBalance(
                                LocalDate.now(),
                                exchange,
                                coin,
                                balance.getTotal().doubleValue() + duplicateCoinBalance.getAmount(),
                                coinValue,
                                "EUR"
                        );
            } else {
                newCoinBalance =
                        new CoinBalance(
                                LocalDate.now(),
                                exchange,
                                coin,
                                balance.getTotal().doubleValue(),
                                coinValue,
                                "EUR"
                        );
            }
            coinBalancesByCoin.put(coin, newCoinBalance);
        });

        return coinBalancesByCoin.values();
    }
}
