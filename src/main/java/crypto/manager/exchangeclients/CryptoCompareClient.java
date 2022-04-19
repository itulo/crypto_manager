package crypto.manager.exchangeclients;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * cryptocompare.com
 * Check value of coins in a specific fiat
 */
public class CryptoCompareClient {

    private final static String URL = "https://min-api.cryptocompare.com/data/price?fsym={coin}&tsyms=EUR";

    private final static RestTemplate restTemplate = new RestTemplate();

    // TODO make it a time based cache
    private final static Map<String, Double> coinValueInFiat = new HashMap<>();

    private final static Map<String, String> coinOtherName = Map.of(
            "iota", "miota"
    );

    private static double getCoinValueFromExchange(String coin){
        String coinName = coinOtherName.getOrDefault(coin.toLowerCase(), coin);
        String conversionUrl = URL.replace("{coin}", coinName);
        ResponseEntity<CryptoCompareResponseBody> response
                = restTemplate.getForEntity(conversionUrl, CryptoCompareResponseBody.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getEur();
        } else {
            return 0L;
        }
    }

    public static double getCoinValueInEur(String coin){
        if (coinValueInFiat.containsKey(coin)){
            return coinValueInFiat.get(coin);
        } else {
            double coinValue = getCoinValueFromExchange(coin);
            coinValueInFiat.put(coin, coinValue);

            return coinValue;
        }
    }
}