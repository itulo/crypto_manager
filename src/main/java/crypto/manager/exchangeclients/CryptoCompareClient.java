package crypto.manager.exchangeclients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * cryptocompare.com
 * Check value of coins in a specific fiat
 */
public class CryptoCompareClient {
    private static final Logger log = LoggerFactory.getLogger(CryptoCompareClient.class);
    private static final String URL = "https://min-api.cryptocompare.com/data/price?fsym={coin}&tsyms=EUR";
    private static final RestTemplate restTemplate = new RestTemplate();

    // TODO make it a time based cache
    private final static Map<String, Double> coinValueInFiat = new HashMap<>();

    private final static Map<String, String> coinOtherName = Map.of(
            "iota", "miota"     //binance calls it iota, cryptocompare calls it miota
    );

    /**
     * Process response from CryptoCompare
     * Even though the response.status is 200, the response body might mention an error
     * If the error is related to rate limit, then wait and retry
     *
     * @param body
     * @param coin
     * @return
     */
    private static double processResponse(CryptoCompareResponseBody body, String coin){
        // TODO retry max 3 times instead of trying forever and use backoff to increase sleep time
        if(body.response() != null && body.message() != null && body.message().contains("rate limit")){
            log.info("CryptoCompare: reached the rate limit, wait and retry!");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error("Could not sleep...");
            }

            return getCoinValueFromExchange(coin);
        }

        return body.eur();
    }

    private static double getCoinValueFromExchange(String coin){
        String coinName = coinOtherName.getOrDefault(coin.toLowerCase(), coin);
        String conversionUrl = URL.replace("{coin}", coinName);
        ResponseEntity<CryptoCompareResponseBody> response
                = restTemplate.getForEntity(conversionUrl, CryptoCompareResponseBody.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return processResponse(response.getBody(), coin);
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