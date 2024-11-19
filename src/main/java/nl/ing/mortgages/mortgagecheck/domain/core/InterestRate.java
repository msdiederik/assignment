package nl.ing.mortgages.mortgagecheck.domain.core;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
public record InterestRate(
        int maturityPeriod,
        BigDecimal interestRate,
        Instant lastUpdate) {

    public InterestRate(int maturityPeriod, double interestRate, Instant lastUpdate) {
        this(maturityPeriod, BigDecimal.valueOf(interestRate), lastUpdate);
    }
}
