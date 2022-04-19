package crypto.manager.services;

import crypto.manager.domain.CoinBalance;
import crypto.manager.repositories.CoinBalanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CoinBalanceServiceImpl implements CoinBalanceService {

    private final CoinBalanceRepository coinBalanceRepository;

    public CoinBalanceServiceImpl(CoinBalanceRepository coinBalanceRepository) {
        this.coinBalanceRepository = coinBalanceRepository;
    }

    @Override
    public List<CoinBalance> findAll() {
        return coinBalanceRepository.findAll();
    }

    @Override
    public List<CoinBalance> saveAll(List<CoinBalance> coinBalances) {
        return coinBalanceRepository.saveAllAndFlush(coinBalances);
    }

    @Override
    public void deleteByExchangeAndDate(String exchange, LocalDate date) {
        coinBalanceRepository.deleteByExchangeAndDate(exchange, date);
    }
}
