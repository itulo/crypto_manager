package crypto.manager.exchangeclients;

import crypto.manager.domain.CoinBalance;

import java.util.Collection;

public interface ExchangeClient {
    public Collection<CoinBalance> getCoinBalances();
}
