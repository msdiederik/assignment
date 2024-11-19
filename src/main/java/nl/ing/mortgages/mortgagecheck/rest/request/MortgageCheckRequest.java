package nl.ing.mortgages.mortgagecheck.rest.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MortgageCheckRequest(
        @NotNull
        @Positive
        BigDecimal income,

        @NotNull
        @Positive
        Integer maturityPeriod,

        @NotNull
        @Positive
        BigDecimal loanValue,

        @NotNull
        @Positive
        BigDecimal homeValue) {
}
