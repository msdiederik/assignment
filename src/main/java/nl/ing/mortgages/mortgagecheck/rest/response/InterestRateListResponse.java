package nl.ing.mortgages.mortgagecheck.rest.response;

import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;

import java.util.List;

public record InterestRateListResponse(
        List<InterestRate> interestRates) {
}
