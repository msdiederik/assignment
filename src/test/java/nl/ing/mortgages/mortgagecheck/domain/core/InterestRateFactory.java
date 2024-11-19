package nl.ing.mortgages.mortgagecheck.domain.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InterestRateFactory {
    private static final Faker faker = new Faker();

    public static List<InterestRate> uniqueListOfInterestRates(int listSize) {
        TreeSet<InterestRate> interestRates = new TreeSet<>(comparingInt(InterestRate::maturityPeriod));
        while (interestRates.size() < listSize) {
            interestRates.add(validInterestRate());
        }

        return interestRates.stream().toList();
    }

    public static List<InterestRate> validListOfInterestRates(int listSize) {
        return Stream.generate(InterestRateFactory::validInterestRate)
                .limit(listSize)
                .toList();
    }

    public static InterestRate validInterestRate() {
        return new InterestRate(
                faker.number().numberBetween(5,30),
                new BigDecimal(faker.number().digits(2)),
                faker.timeAndDate().past());
    }

    public static InterestRate.InterestRateBuilder interestRateBuilder() {
        return InterestRate.builder()
                .maturityPeriod(faker.number().numberBetween(5,30))
                .interestRate(new BigDecimal(faker.number().digits(2)))
                .lastUpdate(faker.timeAndDate().past());
    }
}
