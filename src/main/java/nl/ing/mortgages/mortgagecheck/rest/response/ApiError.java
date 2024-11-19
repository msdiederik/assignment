package nl.ing.mortgages.mortgagecheck.rest.response;

import java.time.Instant;

public record ApiError (
        String message,
        Instant timestamp
){
}
