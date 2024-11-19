package nl.ing.mortgages.mortgagecheck.persistence;

import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;
import nl.ing.mortgages.mortgagecheck.domain.core.InterestRateFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class InterestRateMemoryRepositoryTests {
    private InterestRateMemoryRepository interestRateMemoryRepository;

    @BeforeEach
    void initRepository() {
        interestRateMemoryRepository = new InterestRateMemoryRepository();
    }

    @Test
    void givenUniqueInterestRatesAreStored_whenGetInterestRates_thenEqualInterestRatesAreReturned() {
        List<InterestRate> interestRates = InterestRateFactory.uniqueListOfInterestRates(4);
        interestRateMemoryRepository.saveAll(interestRates);

        List<InterestRate> actualInterestRates = interestRateMemoryRepository.getInterestRates();
        interestRates.forEach(interestRate -> assertTrue(actualInterestRates.contains(interestRate)));
    }

    @Test
    void givenDuplicateMaturityPeriod_whenSaveAll_thenEntryGetsOverwritten() {
        int maturityPeriod = 10;
        InterestRate interestRate = InterestRateFactory.interestRateBuilder().maturityPeriod(maturityPeriod).build();
        interestRateMemoryRepository.saveAll(List.of(interestRate));

        InterestRate newInterestRate = InterestRateFactory.interestRateBuilder().maturityPeriod(maturityPeriod).build();
        interestRateMemoryRepository.saveAll(List.of(newInterestRate));

        List<InterestRate> actualInterestRates = interestRateMemoryRepository.getInterestRates();

        assertThat(actualInterestRates).hasSize(1);
        assertThat(actualInterestRates.get(0)).isEqualTo(newInterestRate);
        assertThat(actualInterestRates.get(0)).isNotEqualTo(interestRate);
    }
}
