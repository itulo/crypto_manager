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
     * Provide a a Collection<CoinBalance> from the balances received from the exchange
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
     * Provide a Collection<CoinBalance> from the balances received from the exchange.
     * With a function to manipulate coin names that are different when they are in spot wallet and earn/staking wallet
     *
     * @param balances
     * @param exchange
     * @return
     */
    public static Collection<CoinBalance> getCoinBalancesWithFunction(Collection<Balance> balances,
                                                                ExchangeEnum exchange,
                                                                Function<String, String> extractCoinName
    ){
        Map<String, CoinBalance> coinBalancesByCoin = new HashMap<>();

        balances.forEach( balance -> {
            if(balance.getAvailable().doubleValue() == 0d) return;

            String coin = extractCoinName.apply(balance.getCurrency().getCurrencyCode());
            double coinValue = CryptoCompareClient.getCoinValueInEur(coin);
            // there may be the same coin in Spot and Earn/Staking wallets, sum them
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
