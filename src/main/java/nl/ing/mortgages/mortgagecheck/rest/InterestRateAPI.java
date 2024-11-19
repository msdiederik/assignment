package nl.ing.mortgages.mortgagecheck.rest;

import lombok.RequiredArgsConstructor;
import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;
import nl.ing.mortgages.mortgagecheck.domain.service.InterestRateService;
import nl.ing.mortgages.mortgagecheck.rest.response.InterestRateListResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InterestRateAPI {
    private final InterestRateService interestRateService;

    @GetMapping("/api/interest-rates")
    public InterestRateListResponse getInterestRates() {
        List<InterestRate> interestRates = interestRateService.getInterestRates();
        return new InterestRateListResponse(interestRates);
    }

}
