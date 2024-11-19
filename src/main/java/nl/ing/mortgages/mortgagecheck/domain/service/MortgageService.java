package nl.ing.mortgages.mortgagecheck.domain.service;

import lombok.RequiredArgsConstructor;
import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;
import nl.ing.mortgages.mortgagecheck.domain.core.MortgageCheckResult;
import nl.ing.mortgages.mortgagecheck.domain.exception.InterestRateNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class MortgageService {
    private final InterestRateRepository interestRateRepository;

    public MortgageCheckResult performMortgageCheck(BigDecimal income, int maturityPeriod, BigDecimal loanValue, BigDecimal homeValue) {
        InterestRate interestRate = interestRateRepository.getInterestRateByMaturityPeriod(maturityPeriod)
                .orElseThrow(() -> new InterestRateNotFoundException(maturityPeriod));

        if(loanDoesNotExceedIncomeFourTimes(loanValue, income)
                && homeValueIsNotGreaterThanLoanValue(homeValue, loanValue)) {
            return new MortgageCheckResult(true, calculateMonthlyCosts(loanValue, interestRate.interestRate()));
        }

        return new MortgageCheckResult(false, BigDecimal.valueOf(0));
    }

    private boolean loanDoesNotExceedIncomeFourTimes(BigDecimal loanValue, BigDecimal income) {
        return loanValue.compareTo(income.multiply(BigDecimal.valueOf(4))) <= 0;
    }

    private boolean homeValueIsNotGreaterThanLoanValue(BigDecimal homeValue, BigDecimal loanValue) {
        return homeValue.compareTo(loanValue) > 0;
    }

    private BigDecimal calculateMonthlyCosts(BigDecimal loanValue, BigDecimal interestRate) {
        return loanValue.add(loanValue.multiply(interestRate.movePointLeft(2)))
                .divide(BigDecimal.valueOf((12)), RoundingMode.HALF_UP);
    }
}
