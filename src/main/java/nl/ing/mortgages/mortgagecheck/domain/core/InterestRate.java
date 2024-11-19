package nl.ing.mortgages.mortgagecheck.domain.core;

import java.math.BigDecimal;
import java.time.Instant;

public record InterestRate(
        int maturityPeriod,
        BigDecimal interestRate,
        Instant lastUpdate) {}
