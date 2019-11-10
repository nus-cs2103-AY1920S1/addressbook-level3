package seedu.address.model.itinerary.day;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserDateUtil.DATE_FORMATTER;
import static seedu.address.logic.parser.ParserDateUtil.DATE_TIME_FORMATTER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelTestUtil;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.event.EventList;
import seedu.address.testutil.DayBuilder;

class DayTest {

    @Test
    void constructor_emptyDescription_isValid() throws ParseException {
        Day day = new Day(LocalDateTime.parse(ModelTestUtil.VALID_STARTDATE_DAY_1_2, DATE_TIME_FORMATTER),
                LocalDateTime.parse(ModelTestUtil.VALID_ENDDATE_DAY_1_2, DATE_TIME_FORMATTER),
                Optional.empty(),
                new Location(ModelTestUtil.VALID_DESTINATION_DAY_1),
                Optional.of(new Budget(ModelTestUtil.VALID_TOTAL_BUDGET_DAY_1)),
                new EventList(ParserDateUtil.getDateFromString(ModelTestUtil.VALID_STARTDATE_DAY_1_1)),
                Optional.empty());
        assertEquals(Optional.empty(), day.getDescription());
    }

    @Test
    void constructor_emptyBudget_isValid() throws ParseException {
        Day day = new Day(LocalDateTime.parse(ModelTestUtil.VALID_STARTDATE_DAY_1_2, DATE_TIME_FORMATTER),
                LocalDateTime.parse(ModelTestUtil.VALID_ENDDATE_DAY_1_2, DATE_TIME_FORMATTER),
                Optional.of(new Description(ModelTestUtil.VALID_DESCRIPTION_DAY_1)),
                new Location(ModelTestUtil.VALID_DESTINATION_DAY_1),
                Optional.empty(),
                new EventList(ParserDateUtil.getDateFromString(ModelTestUtil.VALID_STARTDATE_DAY_1_1)),
                Optional.empty());
        assertEquals(Optional.empty(), day.getTotalBudget());
    }

    @Test
    void isClashingWith() {
        //Testing boundaries for clashes
        // start date == other's start date, end date >= other's end date
        assertTrue(ModelTestUtil.VALID_DAY_1.isClashingWith(ModelTestUtil.CLASHING_DAY_1));

        // start date <= other's start date, end date == other's end date
        assertTrue(ModelTestUtil.VALID_DAY_1.isClashingWith(ModelTestUtil.CLASHING_DAY_2));

        // start date <= other's start date, end date <= other's end date
        assertTrue(ModelTestUtil.VALID_DAY_1.isClashingWith(ModelTestUtil.CLASHING_DAY_3));

        // start date >= other's start date, end date >= other's end date
        assertTrue(ModelTestUtil.VALID_DAY_1.isClashingWith(ModelTestUtil.CLASHING_DAY_4));

        //start date <= other's start date, end date >= other's end date
        assertTrue(ModelTestUtil.VALID_DAY_1.isClashingWith(ModelTestUtil.CLASHING_DAY_5));
    }


    @Test
    void isSameDay() {
        // Same day requires same name, start date, end date and destination

        // Same object
        assertTrue(ModelTestUtil.VALID_DAY_1.isSameDay(ModelTestUtil.VALID_DAY_1));

        // Different object same fields
        assertTrue(ModelTestUtil.VALID_DAY_1.isSameDay(DayBuilder.of(ModelTestUtil.VALID_DAY_1).build()));

        // Same name, diff start date
        assertFalse(ModelTestUtil.VALID_DAY_1.isSameDay(DayBuilder.of(ModelTestUtil.VALID_DAY_1)
                .setStartDate(LocalDate.parse(ModelTestUtil.VALID_STARTDATE_DAY_2_1, DATE_FORMATTER)
                        .atStartOfDay()).build()));

    }

    @Test
    void equals() {
        // Same instance
        assertTrue(ModelTestUtil.VALID_DAY_1.equals(ModelTestUtil.VALID_DAY_1));

        // Different instance same properties
        assertTrue(ModelTestUtil.VALID_DAY_1.equals(DayBuilder.of(ModelTestUtil.VALID_DAY_1).build()));

        //The following tests assumes all other properties are the same
        // Different Start Date
        assertFalse(ModelTestUtil.VALID_DAY_1.equals(DayBuilder.of(ModelTestUtil.VALID_DAY_1)
                .setStartDate(LocalDateTime.parse(ModelTestUtil.VALID_STARTDATE_DAY_2_2,
                        DATE_TIME_FORMATTER)).build()));

        // Different End Date
        assertFalse(ModelTestUtil.VALID_DAY_1.equals(DayBuilder.of(ModelTestUtil.VALID_DAY_1)
                .setEndDate(LocalDateTime.parse("5/1/2019 1200", DATE_TIME_FORMATTER)).build()));

        // Different Destination
        assertFalse(ModelTestUtil.VALID_DAY_1.equals(DayBuilder.of(ModelTestUtil.VALID_DAY_1)
                .setLocation(new Location("Zimbabwe")).build()));
    }
}
