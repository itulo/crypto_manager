package crypto.manager.domain;

public enum ExchangeEnum {
    BINANCE("S"),
    BITTREX("BI"),
    COINBASE_PRO("CP"),
    HITBTC("H"),
    KRAKEN("K"),
    KUCOIN("KU");

    private String code;

    private ExchangeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
