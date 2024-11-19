package nl.ing.mortgages.mortgagecheck.domain.service;

import lombok.RequiredArgsConstructor;
import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestRateService {
    private final InterestRateRepository interestRateRepository;

    public List<InterestRate> getInterestRates() {
        return interestRateRepository.getInterestRates();
    }
}
