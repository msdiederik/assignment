package nl.ing.mortgages.mortgagecheck.domain.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InterestRateFactory {
    private static final Faker faker = new Faker();

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
}
