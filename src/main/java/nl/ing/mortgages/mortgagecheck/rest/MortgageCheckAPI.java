package nl.ing.mortgages.mortgagecheck.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.ing.mortgages.mortgagecheck.domain.core.MortgageCheckResult;
import nl.ing.mortgages.mortgagecheck.domain.exception.InterestRateNotFoundException;
import nl.ing.mortgages.mortgagecheck.domain.service.MortgageService;
import nl.ing.mortgages.mortgagecheck.rest.request.MortgageCheckRequest;
import nl.ing.mortgages.mortgagecheck.rest.response.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class MortgageCheckAPI {
    private final MortgageService mortgageService;

    @PostMapping("/api/mortgage-check")
    MortgageCheckResult performMortgageCheck(@Valid @RequestBody MortgageCheckRequest mortgageCheckRequest) {
        return mortgageService.performMortgageCheck(
                mortgageCheckRequest.income(),
                mortgageCheckRequest.maturityPeriod(),
                mortgageCheckRequest.loanValue(),
                mortgageCheckRequest.homeValue());
    }

    @ExceptionHandler(InterestRateNotFoundException.class)
    public ResponseEntity<ApiError> handleInterestRateNotFoundException(InterestRateNotFoundException e) {
        return new ResponseEntity<>(new ApiError(e.getMessage(), Instant.now()), HttpStatus.BAD_REQUEST);
    }
}
