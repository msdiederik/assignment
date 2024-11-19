package nl.ing.mortgages.mortgagecheck.domain.service;

import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;

import java.util.List;

public interface InterestRateRepository {
    List<InterestRate> getInterestRates();
}
