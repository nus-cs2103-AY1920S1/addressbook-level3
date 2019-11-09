package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.day.Day;
import seedu.address.model.itinerary.event.EventList;

/**
 * Collection of typical day test cases
 */
public class TypicalDays {

    public static final LocalDateTime DATETIME_A = LocalDateTime.of(2019, 1, 5, 12, 0);
    public static final LocalDateTime DATETIME_B = LocalDateTime.of(2020, 11, 3, 0, 0);

    public static final LocalDateTime INVALID_DATETIME_AFTER_1 = LocalDateTime.of(2022, 11, 3, 0, 0);
    public static final LocalDateTime INVALID_DATETIME_AFTER_2 = LocalDateTime.of(2022, 11, 3, 0, 0);

    public static final LocalDateTime INVALID_DATETIME_BEFORE_1 = LocalDateTime.of(2000, 11, 3, 0, 0);
    public static final LocalDateTime INVALID_DATETIME_BEFORE_2 = LocalDateTime.of(2000, 11, 3, 0, 0);

    public static final Day DAY_A = DayBuilder.newInstance()
            .setStartDate(LocalDateTime.of(2019, 2, 6, 0, 0))
            .setEndDate(LocalDateTime.of(2019, 2, 6, 23, 59))
            .setLocation(new Location("Australia"))
            .setEventList(new EventList(LocalDateTime.of(2019, 2, 6, 0, 0)))
            .build();

    public static final Day DAY_B = DayBuilder.newInstance()
            .setStartDate(LocalDateTime.of(2019, 2, 7, 0, 0))
            .setEndDate(LocalDateTime.of(2019, 2, 7, 23, 59))
            .setLocation(new Location("Belarus"))
            .setEventList(new EventList(LocalDateTime.of(2019, 2, 7, 0, 0)))
            .build();

    public static final Day INVALID_DAY_AFTER = DayBuilder.newInstance()
            .setStartDate(LocalDateTime.of(200, 2, 6, 0, 0))
            .setEndDate(LocalDateTime.of(2000, 2, 6, 23, 59))
            .setLocation(new Location("Australia"))
            .setEventList(new EventList(LocalDateTime.of(2019, 2, 6, 0, 0)))
            .build();
    public static final Day INVALID_DAY_BEFORE = DayBuilder.newInstance()
            .setStartDate(LocalDateTime.of(2022, 2, 6, 0, 0))
            .setEndDate(LocalDateTime.of(2022, 2, 6, 23, 59))
            .setLocation(new Location("Australia"))
            .setEventList(new EventList(LocalDateTime.of(2019, 2, 6, 0, 0)))
            .build();
}
