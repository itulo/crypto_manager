package crypto.manager.repositories;

import crypto.manager.domain.CoinBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface CoinBalanceRepository extends JpaRepository<CoinBalance, Long> {
    long deleteByExchangeAndDate(
            @Param("exchange") String exchange,
            @Param("date") LocalDate date);
}
