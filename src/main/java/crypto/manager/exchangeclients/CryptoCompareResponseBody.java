package crypto.manager.exchangeclients;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptoCompareResponseBody {
    @JsonProperty("EUR")
    private double eur;

    public double getEur() {
        return eur;
    }
}

