package crypto.manager.controllers;

import crypto.manager.domain.CoinBalance;
import crypto.manager.services.CoinBalanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/balances")
public class CoinBalanceController {

    private final CoinBalanceService coinBalanceService;

    public CoinBalanceController(CoinBalanceService coinBalanceService) {
        this.coinBalanceService = coinBalanceService;
    }

    @GetMapping("/today")
    List<CoinBalance> getAllCoinBalanceToday(){
        return coinBalanceService.findByDate(LocalDate.now());
    }
}
