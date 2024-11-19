package nl.ing.mortgages.mortgagecheck.domain.exception;

public class InterestRateNotFoundException extends RuntimeException {
    public InterestRateNotFoundException(int maturityPeriod) {
        super("Could not find interest rate with maturity period %d".formatted(maturityPeriod));
    }
}
