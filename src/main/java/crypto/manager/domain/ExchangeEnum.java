package crypto.manager.domain;

import org.checkerframework.checker.units.qual.K;

import java.util.Locale;

public enum ExchangeEnum {
    BINANCE("BINANCE"),
    BITTREX("BITTREX"),
    COINBASEPRO("COINBASEPRO"),
    HITBTC("HITBTC"),
    KRAKEN("KRAKEN"),
    KUCOIN("KUCOIN");

    private String code;

    private ExchangeEnum(String code) {
        this.code = code;
    }

    public static ExchangeEnum getExchangeEnumFromString(String exchange) {
        return switch(exchange.toUpperCase(Locale.ROOT)){
            case "BINANCE" -> BINANCE;
            case "BITTREX" -> BITTREX;
            case "COINBASEPRO" -> COINBASEPRO;
            case "HITBTC" -> HITBTC;
            case "KRAKEN" -> KRAKEN;
            case "KUCOIN" -> KUCOIN;
            default -> throw new IllegalArgumentException("No exchange enum for "+exchange);
        };
    }

    public String getCode(){
        return this.code;
    }
}
