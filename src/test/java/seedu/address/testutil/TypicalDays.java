package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.event.EventList;

/**
 * Collection of typical day test cases
 */
public class TypicalDays {

    public static final Day DAY_A = DayBuilder.newInstance().setName(new Name("Arrival Day"))
            .setStartDate(LocalDateTime.of(2019, 2, 6, 0, 0))
            .setEndDate(LocalDateTime.of(2019, 2, 6, 23, 59))
            .setLocation(new Location("Australia"))
            .setEventList(new EventList(LocalDateTime.of(2019, 2, 6, 0, 0)))
            .build();

    public static final Day DAY_B = DayBuilder.newInstance().setName(new Name("Arrival Day"))
            .setStartDate(LocalDateTime.of(2019, 2, 7, 0, 0))
            .setEndDate(LocalDateTime.of(2019, 2, 7, 23, 59))
            .setLocation(new Location("Belarus"))
            .setEventList(new EventList(LocalDateTime.of(2019, 2, 7, 0, 0)))
            .build();
}
