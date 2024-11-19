package nl.ing.mortgages.mortgagecheck.domain.core;

import java.math.BigDecimal;

public record MortgageCheckResult
        (boolean isFeasible,
         BigDecimal monthlyCosts) {}
