package nl.ing.mortgages.mortgagecheck.rest;

import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;
import nl.ing.mortgages.mortgagecheck.domain.core.InterestRateFactory;
import nl.ing.mortgages.mortgagecheck.domain.service.InterestRateService;
import nl.ing.mortgages.mortgagecheck.rest.response.InterestRateListResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InterestRateAPI.class)
class InterestRateAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    InterestRateService interestRateService;

    private static final String ENDPOINT = "/api/interest-rates";
    private final BodyParser<InterestRateListResponse> bodyParser = new BodyParser<>(InterestRateListResponse.class);

    @Test
    void whenGetInterestRateList_thenListOfInterestRatesIsReturned() throws Exception {
        List<InterestRate> interestRates = InterestRateFactory.validListOfInterestRates(3);
        InterestRateListResponse expectedResponse = new InterestRateListResponse(interestRates);
        when(interestRateService.getInterestRates()).thenReturn(interestRates);

        MvcResult result = mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        InterestRateListResponse parsedResponse = bodyParser.parseBodyToObject(responseContent);
        assertThat(parsedResponse).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}
