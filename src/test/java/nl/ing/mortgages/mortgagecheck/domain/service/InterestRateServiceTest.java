package nl.ing.mortgages.mortgagecheck.domain.service;

import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;
import nl.ing.mortgages.mortgagecheck.domain.core.InterestRateFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class InterestRateServiceTest {

    @Mock
    InterestRateRepository interestRateRepository;

    @InjectMocks
    InterestRateService interestRateService;

    @Test
    void givenRepositoryReturnsInterestRates_whenGetInterestRates_thenListOfInterestRatesIsReturned() {
        List<InterestRate> expectedListOfInterestRates = InterestRateFactory.validListOfInterestRates(3);
        when(interestRateRepository.getInterestRates()).thenReturn(expectedListOfInterestRates);

        List<InterestRate> result = interestRateService.getInterestRates();

        assertThat(result).isEqualTo(expectedListOfInterestRates);
    }
}
