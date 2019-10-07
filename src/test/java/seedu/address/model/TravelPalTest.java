package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTrips.TRIP_A;
import static seedu.address.testutil.TypicalTrips.getTypicalTravelPal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.trip.Trip;
import seedu.address.model.trip.exceptions.ClashingTripException;
import seedu.address.model.trip.exceptions.DuplicateTripException;
import seedu.address.testutil.PersonBuilder;

public class TravelPalTest {

    private final TravelPal travelPal = new TravelPal();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), travelPal.getTripList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> travelPal.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTravelPal_replacesData() {
        TravelPal newData = getTypicalTravelPal();
        travelPal.resetData(newData);
        assertEquals(newData, travelPal);
    }

    @Test
    public void resetData_withDuplicateTrips_throwsDuplicatePersonException() {
        // Two trips with the same identity fields
        Trip editedTripA = Trip.Builder.of(TRIP_A).setTotalBudget(new Expenditure(VALID_TOTAL_BUDGET_AFRICA)).build();
        List<Trip> newTrips = Arrays.asList(TRIP_A, editedTripA);
        TravelPalStub newData = new TravelPalStub(newTrips);

        assertThrows(DuplicateTripException.class, () -> travelPal.resetData(newData));
    }

    @Test
    public void resetData_withClashingTrips_throwsClashingTripsException() {
        Trip editedTripA = Trip.Builder.of(TRIP_A).build();
        List<Trip> newTrips = Arrays.asList(TRIP_A, editedTripA);
        TravelPalStub newData = new TravelPalStub(newTrips);

        assertThrows(ClashingTripException.class, () -> travelPal.resetData(newData));
    }

    @Test
    public void hasTrip_nullTrip_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> travelPal.hasTrip(null));
    }

    @Test
    public void hasTrip_tripNotInTravelPal_returnsFalse() {
        assertFalse(travelPal.hasTrip(TRIP_A));
    }

    @Test
    public void hasTrip_tripInAddressBook_returnsTrue() {
        try {
            travelPal.addTrip(TRIP_A);
            assertTrue(travelPal.hasTrip(TRIP_A));
        } catch (ClashingTripException e){
        }
    }

    @Test
    public void hasTrip_tripWithSameIdentityFieldsInTravelPal_returnsTrue() {
        try {
            travelPal.addTrip(TRIP_A);

            Trip editedTripA = Trip.Builder.of(TRIP_A).setTotalBudget(new Expenditure(VALID_TOTAL_BUDGET_AFRICA))
                    .build();
            assertTrue(travelPal.hasTrip(editedTripA));
        } catch (ClashingTripException e){
        }
    }

    @Test
    public void getTripList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> travelPal.getTripList().remove(0));
    }

    /**
     * A stub ReadOnlyTravelPal whose trip list can violate interface constraints.
     */
    @Disabled
    private static class TravelPalStub implements ReadOnlyTravelPal {
        private final ObservableList<Trip> trips = FXCollections.observableArrayList();

        TravelPalStub(Collection<Trip> trips) {
            this.trips.setAll(trips);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return null;
        }

        @Override
        public ObservableList<Trip> getTripList() {
            return trips;
        }
    }

}
