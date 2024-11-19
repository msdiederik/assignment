package nl.ing.mortgages.mortgagecheck.domain.service;

import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;
import nl.ing.mortgages.mortgagecheck.domain.core.InterestRateFactory;
import nl.ing.mortgages.mortgagecheck.domain.exception.InterestRateNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class InterestRateServiceTest {

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

    @Test
    void givenValidMaturityPeriod_whenGetInterestRateByMaturityPeriod_thenInterestRateIsReturned() {
        InterestRate expectedInterestRate = InterestRateFactory.validInterestRate();
        int maturityPeriod = expectedInterestRate.maturityPeriod();
        when(interestRateRepository.getInterestRateByMaturityPeriod(maturityPeriod)).thenReturn(Optional.of(expectedInterestRate));

        InterestRate result = interestRateService.getInterestRateByMaturityPeriod(maturityPeriod);

        assertThat(result).isEqualTo(expectedInterestRate);
    }

    @Test
    void givenInvalidMaturityPeriod_whenGetInterestRateByMaturityPeriod_thenExceptionIsThrown() {
        int maturityPeriod = 10;
        InterestRateNotFoundException expectedException = new InterestRateNotFoundException(maturityPeriod);
        when(interestRateRepository.getInterestRateByMaturityPeriod(maturityPeriod)).thenReturn(Optional.empty());

        InterestRateNotFoundException resultingException = assertThrows(InterestRateNotFoundException.class,
                () -> interestRateService.getInterestRateByMaturityPeriod(maturityPeriod));

        assertThat(resultingException.getMessage()).isEqualTo(expectedException.getMessage());
    }
}
