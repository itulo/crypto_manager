package crypto.manager.exchangeclients;

import crypto.manager.domain.CoinBalance;

import java.util.List;

public interface ExchangeClient {
    public List<CoinBalance> getCoinBalances();
}
