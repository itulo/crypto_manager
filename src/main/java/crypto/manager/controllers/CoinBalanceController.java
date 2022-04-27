package crypto.manager.controllers;

import crypto.manager.domain.CoinBalance;
import crypto.manager.services.CoinBalanceService;
import crypto.manager.services.RabbitMqSender;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/balances")
public class CoinBalanceController {

    private final CoinBalanceService coinBalanceService;
    private final RabbitMqSender rabbitMqSender;

    public CoinBalanceController(CoinBalanceService coinBalanceService,
                                 RabbitMqSender rabbitMqSender) {
        this.coinBalanceService = coinBalanceService;
        this.rabbitMqSender = rabbitMqSender;
    }

    @GetMapping("/today")
    List<CoinBalance> getAllCoinBalanceToday(){
        return coinBalanceService.findByDate(LocalDate.now());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteCoinBalanceById(@PathVariable Long id){
        Optional<CoinBalance> cb = coinBalanceService.findById(id);
        if(!cb.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        //coinBalanceService.deleteById(id);
        rabbitMqSender.sendCoinBalanceIdForDeletion(id);
    }
}
