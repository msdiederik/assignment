package nl.ing.mortgages.mortgagecheck.rest.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MortgageCheckRequest(
        @NotNull
        @Positive
        BigDecimal income,

        @NotNull
        @Positive
        int maturityPeriod,

        @NotNull
        @Positive
        BigDecimal loanValue,

        @NotNull
        @Positive
        BigDecimal homeValue) {
}
