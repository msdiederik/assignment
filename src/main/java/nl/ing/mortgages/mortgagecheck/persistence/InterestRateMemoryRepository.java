package nl.ing.mortgages.mortgagecheck.persistence;

import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;
import nl.ing.mortgages.mortgagecheck.domain.service.InterestRateRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class InterestRateMemoryRepository implements InterestRateRepository {
    private final HashMap<Integer, InterestRate> storedInterestRates = new HashMap<>();

    @Override
    public List<InterestRate> getInterestRates() {
        return storedInterestRates.values().stream().toList();
    }

    @Override
    public Optional<InterestRate> getInterestRateByMaturityPeriod(int maturityPeriod) {
        return Optional.of(storedInterestRates.get(maturityPeriod));
    }

    @Override
    public void saveAll(List<InterestRate> interestRates) {
        interestRates.forEach(interestRate -> storedInterestRates.put(interestRate.maturityPeriod(), interestRate));
    }
}
