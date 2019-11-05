package seedu.address.model.itinerary.day;

import org.junit.jupiter.api.Test;
import seedu.address.logic.parser.ParserDateUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.event.EventList;
import seedu.address.testutil.DayBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.parser.ParserDateUtil.DATE_TIME_FORMATTER;
import static seedu.address.model.ModelTestUtil.*;

class DayTest {

    @Test
    void constructor_empty_description_isValid() throws ParseException {
        Day day = new Day(LocalDateTime.parse(VALID_STARTDATE_DAY_1_1)
                    , LocalDateTime.parse(VALID_ENDDATE_DAY_1_1)
                    , Optional.empty()
                    , new Location(VALID_DESTINATION_DAY_1)
                    , Optional.of(new Budget(VALID_TOTAL_BUDGET_DAY_1))
                    , new EventList(ParserDateUtil.getDateFromString(VALID_STARTDATE_DAY_1_1)));
        assertEquals(Optional.empty(), day.getDescription());
    }

    @Test
    void constructor_empty_budget_isValid() throws ParseException {
        Day day = new Day(LocalDateTime.parse(VALID_STARTDATE_DAY_1_1)
                , LocalDateTime.parse(VALID_ENDDATE_DAY_1_1)
                , Optional.of(new Description(VALID_DESCRIPTION_DAY_1))
                , new Location(VALID_DESTINATION_DAY_1)
                , Optional.empty()
                , new EventList(ParserDateUtil.getDateFromString(VALID_STARTDATE_DAY_1_1)));
        assertEquals(Optional.empty(), day.getTotalBudget());
    }

    @Test
    void isClashingWith() {
        //Testing boundaries for clashes
        // start date == other's start date, end date >= other's end date
        assertTrue(VALID_DAY_1.isClashingWith(CLASHING_DAY_1));

        // start date <= other's start date, end date == other's end date
        assertTrue(VALID_DAY_1.isClashingWith(CLASHING_DAY_2));

        // start date <= other's start date, end date <= other's end date
        assertTrue(VALID_DAY_1.isClashingWith(CLASHING_DAY_3));

        // start date >= other's start date, end date >= other's end date
        assertTrue(VALID_DAY_1.isClashingWith(CLASHING_DAY_4));

        //start date <= other's start date, end date >= other's end date
        assertTrue(VALID_DAY_1.isClashingWith(CLASHING_DAY_5));
    }


    @Test
    void isSameDay() {
        // Same day requires same name, start date, end date and destination

        // Same object
        assertTrue(VALID_DAY_1.isSameDay(VALID_DAY_1));

        // Different object same fields
        assertTrue(VALID_DAY_1.isSameDay(DayBuilder.of(VALID_DAY_1).build()));

        // Same name, diff start date
        assertFalse(VALID_DAY_1.isSameDay(DayBuilder.of(VALID_DAY_1)
                .setStartDate(LocalDate.parse(VALID_STARTDATE_DAY_2_1).atStartOfDay()).build()));

    }

    @Test
    void equals() {
        // Same instance
        assertTrue(VALID_DAY_1.equals(VALID_DAY_1));

        // Different instance same properties
        assertTrue(VALID_DAY_1.equals(DayBuilder.of(VALID_DAY_1).build()));

        //The following tests assumes all other properties are the same
        // Different Start Date
        assertFalse(VALID_DAY_1.equals(DayBuilder.of(VALID_DAY_1)
                .setStartDate(LocalDateTime.parse(VALID_STARTDATE_DAY_2_2, DATE_TIME_FORMATTER)).build()));

        // Different End Date
        assertFalse(VALID_DAY_1.equals(DayBuilder.of(VALID_DAY_1)
                .setEndDate(LocalDateTime.parse("5/1/2019 1200", DATE_TIME_FORMATTER)).build()));

        // Different Destination
        assertFalse(VALID_DAY_1.equals(DayBuilder.of(VALID_DAY_1)
                .setLocation(new Location("Zimbabwe")).build()));
    }
}