package nl.ing.mortgages.mortgagecheck.rest.request;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MortgageCheckRequestFactory {
    public static MortgageCheckRequest.MortgageCheckRequestBuilder mortgageCheckRequestBuilder() {
        return MortgageCheckRequest.builder()
                .income(BigDecimal.valueOf(100))
                .maturityPeriod(15)
                .loanValue(BigDecimal.valueOf(200))
                .homeValue(BigDecimal.valueOf(200));
    }

    public static MortgageCheckRequest validMortgageCheckRequest() {
        return mortgageCheckRequestBuilder().build();
    }
}
