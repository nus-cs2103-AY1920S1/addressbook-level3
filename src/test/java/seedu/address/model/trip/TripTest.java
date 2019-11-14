package seedu.address.model.trip;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DATE_TIME_FORMATTER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DAY_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESTINATION_BALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESTINATION_DAY_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_AFRICA_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDDATE_DAY_1_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AFRICA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_BALI_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_DAY_1_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOTAL_BUDGET_AFRICA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOTAL_BUDGET_BALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOTAL_BUDGET_DAY_1;
import static seedu.address.testutil.TypicalTrips.TRIP_A;
import static seedu.address.testutil.TypicalTrips.TRIP_B;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.day.DayList;
import seedu.address.testutil.DayBuilder;
import seedu.address.testutil.TripBuilder;

public class TripTest {

    @Test
    public void isSameTrip() {
        // same object -> returns true
        assertTrue(TRIP_A.isSameTrip(TRIP_A));

        // null -> returns false
        assertFalse(TRIP_A.isSameTrip(null));

        // different start and end dates -> returns false
        Trip editedTripA = TripBuilder.of(TRIP_A)
                .setStartDate(LocalDateTime.of(2020, 9, 12, 3, 30))
                .setEndDate(LocalDateTime.of(2021, 4, 3, 4, 13)).build();

        assertFalse(TRIP_A.isSameTrip(editedTripA));

        // different name -> returns false
        editedTripA = TripBuilder.of(TRIP_A).setName(new Name(VALID_NAME_AFRICA)).build();
        assertFalse(TRIP_A.isSameTrip(editedTripA));

        // same name, same start and end times, same destination different attributes -> returns true
        editedTripA = TripBuilder.of(TRIP_A).setTotalBudget(new Budget(VALID_TOTAL_BUDGET_AFRICA))
                .build();
        assertTrue(TRIP_A.isSameTrip(editedTripA));

        // same name, start and end times, same total budger, same location, different attributes -> returns true
        editedTripA = TripBuilder.of(TRIP_A).build();
        assertTrue(TRIP_A.isSameTrip(editedTripA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Trip tripACopy = TripBuilder.of(TRIP_A).build();
        assertTrue(TRIP_A.equals(tripACopy));

        // same object -> returns true
        assertTrue(TRIP_A.equals(TRIP_A));

        // null -> returns false
        assertFalse(TRIP_A.equals(null));

        // different type -> returns false
        assertFalse(TRIP_A.equals(5));

        // different person -> returns false
        assertFalse(TRIP_A.equals(TRIP_B));

        // different name -> returns false
        Trip editedTripA = TripBuilder.of(TRIP_A).setName(new Name(VALID_NAME_BALI)).build();
        assertFalse(TRIP_A.equals(editedTripA));

        // different start date -> returns false
        editedTripA = TripBuilder.of(TRIP_A).setStartDate(
                LocalDateTime.parse(VALID_STARTDATE_BALI_2, DATE_TIME_FORMATTER)).build();
        assertFalse(TRIP_A.equals(editedTripA));

        // different end date -> returns false
        editedTripA = TripBuilder.of(TRIP_A).setEndDate(
                LocalDateTime.parse(VALID_ENDDATE_AFRICA_2, DATE_TIME_FORMATTER)).build();
        assertFalse(TRIP_A.equals(editedTripA));

        // different Location -> returns false
        editedTripA = TripBuilder.of(TRIP_A).setLocation(new Location(VALID_DESTINATION_BALI)).build();
        assertFalse(TRIP_A.equals(editedTripA));

        // different total budget -> returns false
        editedTripA = TripBuilder.of(TRIP_A).setTotalBudget(new Budget(VALID_TOTAL_BUDGET_BALI)).build();
        assertFalse(TRIP_A.equals(editedTripA));

        // different daylist -> returns false
        DayList days = new DayList(LocalDateTime.parse(VALID_STARTDATE_DAY_1_2, DATE_TIME_FORMATTER),
                LocalDateTime.parse(VALID_ENDDATE_DAY_1_2, DATE_TIME_FORMATTER));
        days.add(DayBuilder.newInstance()
                .setDescription(new Description(VALID_DESCRIPTION_DAY_1))
                .setStartDate(LocalDateTime.parse(VALID_STARTDATE_DAY_1_2, DATE_TIME_FORMATTER))
                .setEndDate(LocalDateTime.parse(VALID_ENDDATE_DAY_1_2, DATE_TIME_FORMATTER))
                .setTotalBudget(new Budget(VALID_TOTAL_BUDGET_DAY_1))
                .setLocation(new Location(VALID_DESTINATION_DAY_1))
                .build()
        );
        editedTripA = TripBuilder.of(TRIP_A).setDayList(days).build();
        assertFalse(TRIP_A.equals(editedTripA));
    }

}
