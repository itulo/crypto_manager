package crypto.manager.services;

import crypto.manager.domain.CoinBalance;

import java.time.LocalDate;
import java.util.List;

public interface CoinBalanceService {
    List<CoinBalance> findAll();

    List<CoinBalance> saveAll(List<CoinBalance> coinBalances);

    void deleteByExchangeAndDate(String exchange, LocalDate date);
}
