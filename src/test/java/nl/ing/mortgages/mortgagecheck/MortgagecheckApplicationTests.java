package nl.ing.mortgages.mortgagecheck;

import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;
import nl.ing.mortgages.mortgagecheck.domain.service.InterestRateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MortgagecheckApplicationTests {

	@Autowired
	private InterestRateRepository interestRateRepository;

	@Test
	void whenContextLoads_thenInterestRatesAreLoadedIntoMemory() {
		List<InterestRate> interestRates = interestRateRepository.getInterestRates();
		assertThat(interestRates).hasSizeGreaterThan(0);
	}

}
