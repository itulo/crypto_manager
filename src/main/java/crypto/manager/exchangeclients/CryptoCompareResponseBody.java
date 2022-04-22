package crypto.manager.exchangeclients;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CryptoCompareResponseBody (
        @JsonProperty("Response") String response,
        @JsonProperty("Message") String message,
        @JsonProperty("EUR") double eur)
{}

