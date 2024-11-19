package nl.ing.mortgages.mortgagecheck.rest;

import nl.ing.mortgages.mortgagecheck.domain.core.MortgageCheckResult;
import nl.ing.mortgages.mortgagecheck.domain.exception.InterestRateNotFoundException;
import nl.ing.mortgages.mortgagecheck.domain.service.MortgageService;
import nl.ing.mortgages.mortgagecheck.rest.request.MortgageCheckRequest;
import nl.ing.mortgages.mortgagecheck.rest.request.MortgageCheckRequestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MortgageCheckAPI.class)
class MortgageCheckAPITest {

    @MockitoBean
    private MortgageService mortgageService;

    @Autowired
    MockMvc mockMvc;

    private static final String ENDPOINT = "/api/mortgage-check";

    private final BodyParser<MortgageCheckRequest> mortgageCheckRequestBodyParser = new BodyParser<>(MortgageCheckRequest.class);

    @Test
    void givenResponseIsFeasible_whenPerformMortgageCheck_thenStatusIs200() throws Exception {
        MortgageCheckRequest request = MortgageCheckRequestFactory.validMortgageCheckRequest();

        boolean isFeasible = true;
        BigDecimal monthlyCosts = BigDecimal.valueOf(16.8);
        MortgageCheckResult mortgageCheckResult = new MortgageCheckResult(isFeasible, monthlyCosts);

        when(mortgageService.performMortgageCheck(request.income(), request.maturityPeriod(), request.loanValue(), request.homeValue()))
                .thenReturn(mortgageCheckResult);

        mockMvc.perform(post(ENDPOINT)
                .contentType("application/json")
                .content(mortgageCheckRequestBodyParser.parseObjectToBody(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isFeasible").value(isFeasible))
                .andExpect(jsonPath("$.monthlyCosts").value(monthlyCosts));
    }

    @Test
    void givenResponseIsNotFeasible_whenPerfromMortgageCheck_thenStatusIs200() throws Exception {
        MortgageCheckRequest request = MortgageCheckRequestFactory.validMortgageCheckRequest();

        boolean isFeasible = false;
        BigDecimal monthlyCosts = BigDecimal.valueOf(0);
        MortgageCheckResult mortgageCheckResult = new MortgageCheckResult(isFeasible, monthlyCosts);

        when(mortgageService.performMortgageCheck(request.income(), request.maturityPeriod(), request.loanValue(), request.homeValue()))
                .thenReturn(mortgageCheckResult);

        mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(mortgageCheckRequestBodyParser.parseObjectToBody(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isFeasible").value(isFeasible))
                .andExpect(jsonPath("$.monthlyCosts").value(monthlyCosts));
    }

    @Test
    void givenMortgageServiceCannotFindInterestRate_whenPerformMortgageCheck_thenStatusIs400() throws Exception {
        MortgageCheckRequest request = MortgageCheckRequestFactory.validMortgageCheckRequest();
        InterestRateNotFoundException expectedException = new InterestRateNotFoundException(request.maturityPeriod());

        when(mortgageService.performMortgageCheck(request.income(), request.maturityPeriod(), request.loanValue(), request.homeValue()))
                .thenThrow(expectedException);

        var result = mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(mortgageCheckRequestBodyParser.parseObjectToBody(request)))
                .andExpect(status().isBadRequest())
                .andReturn();

        result.getResponse().getContentAsString();
    }

    @Test
    void givenMaturityPeriodIsInvalid_whenPerformMortgageCheck_thenStatusIs400() throws Exception {
        MortgageCheckRequest nullRequest = MortgageCheckRequestFactory.mortgageCheckRequestBuilder()
                .maturityPeriod(null).build();

        MortgageCheckRequest belowZeroRequest = MortgageCheckRequest.builder()
                .maturityPeriod(-12)
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(mortgageCheckRequestBodyParser.parseObjectToBody(nullRequest)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(mortgageCheckRequestBodyParser.parseObjectToBody(belowZeroRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenIncomeIsInvalid_whenPerformMortgageCheck_thenStatusIs400() throws Exception {
        MortgageCheckRequest nullRequest = MortgageCheckRequestFactory.mortgageCheckRequestBuilder()
                .income(null).build();

        MortgageCheckRequest belowZeroRequest = MortgageCheckRequest.builder()
                .income(BigDecimal.valueOf(-12))
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(mortgageCheckRequestBodyParser.parseObjectToBody(nullRequest)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(mortgageCheckRequestBodyParser.parseObjectToBody(belowZeroRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenLoanValueIsInvalid_whenPerformMortgageCheck_thenStatusIs400() throws Exception {
        MortgageCheckRequest nullRequest = MortgageCheckRequestFactory.mortgageCheckRequestBuilder()
                .loanValue(null).build();

        MortgageCheckRequest belowZeroRequest = MortgageCheckRequest.builder()
                .loanValue(BigDecimal.valueOf(-12))
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(mortgageCheckRequestBodyParser.parseObjectToBody(nullRequest)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(mortgageCheckRequestBodyParser.parseObjectToBody(belowZeroRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenHomeValueIsInvalid_whenPerformMortgageCheck_thenStatusIs400() throws Exception {
        MortgageCheckRequest nullRequest = MortgageCheckRequestFactory.mortgageCheckRequestBuilder()
                .homeValue(null).build();

        MortgageCheckRequest belowZeroRequest = MortgageCheckRequest.builder()
                .homeValue(BigDecimal.valueOf(-12))
                .build();

        mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(mortgageCheckRequestBodyParser.parseObjectToBody(nullRequest)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post(ENDPOINT)
                        .contentType("application/json")
                        .content(mortgageCheckRequestBodyParser.parseObjectToBody(belowZeroRequest)))
                .andExpect(status().isBadRequest());
    }
}
