package crypto.manager.exchangeclients;


import crypto.manager.domain.CoinBalance;
import crypto.manager.domain.ExchangeEnum;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.mockito.MockedStatic;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

public class ExchangeClientHelperIT {

    private Map<Currency, Balance> createBalances(){
        return Map.of(
                new Currency("BTC"), new Balance(new Currency("BTC"), new BigDecimal("1")),
                new Currency("ETH"), new Balance(new Currency("ETH"), new BigDecimal("10")),
                new Currency("DOGE"), new Balance(new Currency("DOGE"), new BigDecimal("100346"))
        );
    }

    @Test
    public void getCoinBalancesIT(){
        try(MockedStatic mockedCryptoCompareClient = mockStatic(CryptoCompareClient.class)) {
            mockedCryptoCompareClient.when(() -> CryptoCompareClient.getCoinValueInEur("BTC")).thenReturn(30000d);
            mockedCryptoCompareClient.when(() -> CryptoCompareClient.getCoinValueInEur("ETH")).thenReturn(2000d);
            mockedCryptoCompareClient.when(() -> CryptoCompareClient.getCoinValueInEur("DOGE")).thenReturn(1d); // moon!

            Map<Currency, Balance> balances = createBalances();

            Collection<CoinBalance> coinBalances = ExchangeClientHelper.getCoinBalances(balances, ExchangeEnum.KRAKEN);

            assertThat(coinBalances.size()).isEqualTo(3);

            CoinBalance coinBalance = coinBalances.stream().filter(c -> c.getCoin().equals("BTC")).findFirst().orElseThrow();
            assertThat(coinBalance.getAmount()).isEqualTo(1d);
            assertThat(coinBalance.getPricePerUnit()).isEqualTo(30000d);
            assertThat(coinBalance.getExchange()).isEqualByComparingTo(ExchangeEnum.KRAKEN);

            coinBalance = coinBalances.stream().filter(c -> c.getCoin().equals("ETH")).findFirst().orElseThrow();
            assertThat(coinBalance.getAmount()).isEqualTo(10);
            assertThat(coinBalance.getPricePerUnit()).isEqualTo(2000d);
            assertThat(coinBalance.getExchange()).isEqualByComparingTo(ExchangeEnum.KRAKEN);

            coinBalance = coinBalances.stream().filter(c -> c.getCoin().equals("DOGE")).findFirst().orElseThrow();
            assertThat(coinBalance.getAmount()).isEqualTo(100346d);
            assertThat(coinBalance.getPricePerUnit()).isEqualTo(1d);
            assertThat(coinBalance.getExchange()).isEqualByComparingTo(ExchangeEnum.KRAKEN);
        }
    }

    @Test
    public void getCoinBalancesWithFunctionIT(){
        try(MockedStatic mockedCryptoCompareClient = mockStatic(CryptoCompareClient.class)) {
            mockedCryptoCompareClient.when(() -> CryptoCompareClient.getCoinValueInEur("BTC")).thenReturn(30000d);
            mockedCryptoCompareClient.when(() -> CryptoCompareClient.getCoinValueInEur("ETH")).thenReturn(2000d);
            mockedCryptoCompareClient.when(() -> CryptoCompareClient.getCoinValueInEur("DOGE")).thenReturn(1d); // moon!

            Map<Currency, Balance> balances = new HashMap<>(createBalances()); // need to create a new hashmap because the one we get is immutable
            balances.put(new Currency("ETH.S"), new Balance(new Currency("ETH.S"), new BigDecimal("11")));

            Function<String, String> extractCoinName =
                    (currencyCode) -> {
                return currencyCode.endsWith(".S") ? currencyCode.replace(".S", "") : currencyCode;
            };

            Collection<CoinBalance> coinBalances = ExchangeClientHelper.getCoinBalancesWithFunction(
                    balances.values(),
                    ExchangeEnum.KRAKEN,
                    extractCoinName);

            assertThat(coinBalances.size()).isEqualTo(3);

            CoinBalance coinBalance = coinBalances.stream().filter(c -> c.getCoin().equals("BTC")).findFirst().orElseThrow();
            assertThat(coinBalance.getAmount()).isEqualTo(1d);
            assertThat(coinBalance.getPricePerUnit()).isEqualTo(30000d);
            assertThat(coinBalance.getExchange()).isEqualByComparingTo(ExchangeEnum.KRAKEN);

            coinBalance = coinBalances.stream().filter(c -> c.getCoin().equals("ETH")).findFirst().orElseThrow();
            assertThat(coinBalance.getAmount()).isEqualTo(21);
            assertThat(coinBalance.getPricePerUnit()).isEqualTo(2000d);
            assertThat(coinBalance.getExchange()).isEqualByComparingTo(ExchangeEnum.KRAKEN);

            coinBalance = coinBalances.stream().filter(c -> c.getCoin().equals("DOGE")).findFirst().orElseThrow();
            assertThat(coinBalance.getAmount()).isEqualTo(100346d);
            assertThat(coinBalance.getPricePerUnit()).isEqualTo(1d);
            assertThat(coinBalance.getExchange()).isEqualByComparingTo(ExchangeEnum.KRAKEN);
        }
    }
}
