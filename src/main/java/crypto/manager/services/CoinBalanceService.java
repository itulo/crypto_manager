package crypto.manager.services;

import crypto.manager.domain.CoinBalance;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface CoinBalanceService {
    List<CoinBalance> findAll();

    List<CoinBalance> findByDate(LocalDate date);

    Collection<CoinBalance> saveAll(Collection<CoinBalance> coinBalances);

    void deleteByExchangeAndDate(String exchange, LocalDate date);

    Collection<CoinBalance> replaceExchangeCoinBalancesToday(String exchange, Collection<CoinBalance> coinBalances);
}
