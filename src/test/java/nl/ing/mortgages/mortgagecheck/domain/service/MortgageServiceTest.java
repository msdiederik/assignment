package nl.ing.mortgages.mortgagecheck.domain.service;

import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;
import nl.ing.mortgages.mortgagecheck.domain.core.InterestRateFactory;
import nl.ing.mortgages.mortgagecheck.domain.core.MortgageCheckResult;
import nl.ing.mortgages.mortgagecheck.domain.exception.InterestRateNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MortgageServiceTest {
    @Mock
    InterestRateRepository interestRateRepository;

    @InjectMocks
    MortgageService mortgageService;

    @Test
    void givenValidRequestAndInterestRateExists_whenPerformMortgageCheck_thenResultIsFeasibleAndHasMonthlyCosts() {
        int maturityPeriod = 10;
        BigDecimal interestRateValue = BigDecimal.valueOf(3.41);
        BigDecimal income = BigDecimal.valueOf(30000.12);
        BigDecimal loanValue = BigDecimal.valueOf(90032.41);
        BigDecimal homeValue =  BigDecimal.valueOf(400000.32);

        InterestRate interestRate = InterestRateFactory.interestRateBuilder()
                .maturityPeriod(maturityPeriod)
                .interestRate(interestRateValue).build();

        when(interestRateRepository.getInterestRateByMaturityPeriod(maturityPeriod)).thenReturn(Optional.of(interestRate));

        MortgageCheckResult result = mortgageService.performMortgageCheck(income, maturityPeriod, loanValue, homeValue);

        BigDecimal expectedMonthlyCosts = loanValue.add(loanValue.multiply(interestRateValue.movePointLeft(2)))
                .divide(BigDecimal.valueOf(((double) maturityPeriod/12)), RoundingMode.HALF_UP);


        assertThat(result.isFeasible()).isTrue();
        assertThat(result.monthlyCosts()).isEqualTo(expectedMonthlyCosts);
    }

    @Test
    void givenLoanValueExceedsFourTimesTheIncome_whenPerformMortgageCheck_thenMortgageIsNotFeasible() {
        int maturityPeriod = 10;
        BigDecimal income = BigDecimal.valueOf(30000.12);
        BigDecimal loanValue = BigDecimal.valueOf(150000);
        BigDecimal homeValue =  BigDecimal.valueOf(400000.32);

        InterestRate interestRate = InterestRateFactory.validInterestRate();
        when(interestRateRepository.getInterestRateByMaturityPeriod(maturityPeriod)).thenReturn(Optional.of(interestRate));

        MortgageCheckResult result = mortgageService.performMortgageCheck(income, maturityPeriod, loanValue, homeValue);

        assertThat(result.isFeasible()).isFalse();
        assertThat(result.monthlyCosts()).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    void givenHomeValueExceedsTheHomeValue_whenPerformMortgageCheck_thenMortgageIsNotFeasible() {
        int maturityPeriod = 10;
        BigDecimal income = BigDecimal.valueOf(30000.12);
        BigDecimal loanValue = BigDecimal.valueOf(400000.32);
        BigDecimal homeValue =  BigDecimal.valueOf(90000);

        InterestRate interestRate = InterestRateFactory.validInterestRate();
        when(interestRateRepository.getInterestRateByMaturityPeriod(maturityPeriod)).thenReturn(Optional.of(interestRate));

        MortgageCheckResult result = mortgageService.performMortgageCheck(income, maturityPeriod, loanValue, homeValue);

        assertThat(result.isFeasible()).isFalse();
        assertThat(result.monthlyCosts()).isEqualTo(BigDecimal.valueOf(0));
    }

    @Test
    void givenInvalidMaturityPeriod_whenPerformMortgageCheck_thenExceptionIsThrown() {
        int maturityPeriod = 10;
        BigDecimal income = BigDecimal.valueOf(30000.12);
        BigDecimal loanValue = BigDecimal.valueOf(90032.41);
        BigDecimal homeValue =  BigDecimal.valueOf(400000.32);

        InterestRateNotFoundException expectedException = new InterestRateNotFoundException(maturityPeriod);
        when(interestRateRepository.getInterestRateByMaturityPeriod(maturityPeriod)).thenReturn(Optional.empty());

        InterestRateNotFoundException resultingException = assertThrows(InterestRateNotFoundException.class,
                () -> mortgageService.performMortgageCheck(income, maturityPeriod, loanValue, homeValue));

        assertThat(resultingException.getMessage()).isEqualTo(expectedException.getMessage());
    }
}
