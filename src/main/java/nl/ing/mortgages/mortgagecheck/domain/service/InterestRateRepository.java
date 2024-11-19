package nl.ing.mortgages.mortgagecheck.domain.service;

import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;

import java.util.List;
import java.util.Optional;

public interface InterestRateRepository {
    List<InterestRate> getInterestRates();
    Optional<InterestRate> getInterestRateByMaturityPeriod(int maturityPeriod);
}
