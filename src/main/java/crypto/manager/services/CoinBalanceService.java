package crypto.manager.services;

import crypto.manager.domain.CoinBalance;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CoinBalanceService {
    List<CoinBalance> findAll();

    Optional<CoinBalance> findById(long id);

    List<CoinBalance> findByDate(LocalDate date);

    Collection<CoinBalance> saveAll(Collection<CoinBalance> coinBalances);

    Collection<CoinBalance> replaceExchangeCoinBalancesToday(String exchange, Collection<CoinBalance> coinBalances);

    void deleteByExchangeAndDate(String exchange, LocalDate date);

    void deleteById(long id);
}
