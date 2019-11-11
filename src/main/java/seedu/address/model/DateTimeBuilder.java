package seedu.address.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * Represents a Builder responsible for creating {@link DateTime}.
 */
@JsonPOJOBuilder
public class DateTimeBuilder {

    private final Instant instant;

    DateTimeBuilder(Instant instant) {
        this.instant = instant;
    }

    DateTimeBuilder(int day, int month, int year, int hour, int minute, ZoneId timezone) {
        this.instant = ZonedDateTime.of(year, month, day, hour, minute, 0 , 0, timezone)
            .toInstant();
    }

    @JsonCreator
    DateTimeBuilder(@JsonProperty("epoch") long epoch) {
        this.instant = Instant.ofEpochSecond(epoch);
    }

    public DateTime build() {
        return new DateTime(this.instant);
    }
}
