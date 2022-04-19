package crypto.manager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class CoinBalance {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private ExchangeEnum exchange;

    private String coin;

    private double amount;

    private double pricePerUnit;

    private String fiat;

    public CoinBalance(LocalDate date, ExchangeEnum exchange, String coin, double amount, double pricePerUnit, String fiat) {
        this.date = date;
        this.exchange = exchange;
        this.coin = coin;
        this.amount = amount;
        this.pricePerUnit = pricePerUnit;
        this.fiat = fiat;
    }
}
