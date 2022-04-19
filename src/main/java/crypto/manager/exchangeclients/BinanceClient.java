package crypto.manager.exchangeclients;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.AssetBalance;
import crypto.manager.configuration.BinanceClientConfigProperties;
import crypto.manager.domain.CoinBalance;
import crypto.manager.domain.ExchangeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BinanceClient implements ExchangeClient {

    private BinanceApiRestClient binanceClient;

    @Autowired
    public BinanceClient(BinanceClientConfigProperties binanceProps) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(binanceProps.apiKey(), binanceProps.apiSecret());
        binanceClient = factory.newRestClient();
    }

    public List<CoinBalance> getCoinBalances(){
        Account account = binanceClient.getAccount(/*BinanceApiConstants.DEFAULT_RECEIVING_WINDOW*/ 60_000L, System.currentTimeMillis());
        List<AssetBalance> nonZeroBalances = account.getBalances().stream()
                .filter(b -> Double.parseDouble(b.getFree()) > 0 || Double.parseDouble(b.getLocked()) > 0)
                .toList();

        List<CoinBalance> coinBalances = new ArrayList<>();
        nonZeroBalances.forEach( b -> {
            String coin = b.getAsset().startsWith("LD") ? b.getAsset().replace("LD", "") : b.getAsset();
            double coinValue = CryptoCompareClient.getCoinValueInEur(coin);

            coinBalances.add(
                    new CoinBalance(
                            LocalDate.now(),
                            ExchangeEnum.BINANCE,
                            coin,
                            Double.parseDouble(b.getFree()) + Double.parseDouble(b.getLocked()),
                            coinValue,
                            "EUR"
                    )
            );
        });

        return coinBalances;
    }
}
