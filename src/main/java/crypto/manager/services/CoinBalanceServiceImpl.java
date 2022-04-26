package crypto.manager.services;

import crypto.manager.domain.CoinBalance;
import crypto.manager.domain.ExchangeEnum;
import crypto.manager.repositories.CoinBalanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    public Optional<CoinBalance> findById(long id) {
        return coinBalanceRepository.findById(id);
    }

    @Override
    public List<CoinBalance> findByDate(LocalDate date) {
        return coinBalanceRepository.findByDate(date);
    }

    @Override
    public Collection<CoinBalance> saveAll(Collection<CoinBalance> coinBalances) {
        return coinBalanceRepository.saveAllAndFlush(coinBalances);
    }

    @Transactional
    @Override
    public Collection<CoinBalance> replaceExchangeCoinBalancesToday(String exchange, Collection<CoinBalance> coinBalances) {
        ExchangeEnum exchangeEnum = ExchangeEnum.getExchangeEnumFromString(exchange);
        coinBalanceRepository.deleteByExchangeAndDate(exchangeEnum, LocalDate.now());
        return coinBalanceRepository.saveAll(coinBalances);
    }

    @Override
    public void deleteByExchangeAndDate(String exchange, LocalDate date) {
        ExchangeEnum exchangeEnum = ExchangeEnum.getExchangeEnumFromString(exchange);
        coinBalanceRepository.deleteByExchangeAndDate(exchangeEnum, date);
    }

    @Override
    public void deleteById(long id) {
        coinBalanceRepository.deleteById(id);
    }
}
