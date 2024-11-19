package nl.ing.mortgages.mortgagecheck.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.ing.mortgages.mortgagecheck.domain.core.InterestRate;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class InterestRateService {
    private final InterestRateRepository interestRateRepository;

    public List<InterestRate> getInterestRates() {
        return interestRateRepository.getInterestRates();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadInterestRatesIntoMemory() {
        log.info("Loading interest rates...");
        interestRateRepository.saveAll(List.of(new InterestRate(10, 3.21, Instant.now()),
                new InterestRate(15, 3.41, Instant.now()),
                new InterestRate(20, 3.51, Instant.now()),
                new InterestRate(25, 3.61, Instant.now())));
        log.info("Interest rates loaded into repository");
    }
}
