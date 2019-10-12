package seedu.address.model.trip;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTrips.TRIP_A;
import static seedu.address.testutil.TypicalTrips.TRIP_B;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.trip.exceptions.DuplicateTripException;
import seedu.address.model.trip.exceptions.TripNotFoundException;
import seedu.address.testutil.TripBuilder;

public class TripListTest {
    @Test
    void contains_nullTrip_throwsNullPointerException() {
        TripList tripList = new TripList();
        assertThrows(NullPointerException.class, () -> tripList.contains(null));
    }

    @Test
    void contains_tripNotInList_returnsFalse() {
        TripList tripList = new TripList();
        assertFalse(tripList.contains(TRIP_A));
    }

    @Test
    void contains_tripInList_returnsTrue() {
        TripList tripList = new TripList();
        assertDoesNotThrow(() -> {
            tripList.add(TRIP_A);
            assertTrue(tripList.contains(TRIP_A));
        });
    }

    @Test
    void contains_tripWithSameIdentityFieldsInList_returnsTrue() {
        TripList tripList = new TripList();
        assertDoesNotThrow(() -> {
            tripList.add(TRIP_A);
            Trip editedTripA = TripBuilder.of(TRIP_A).setTotalBudget(new Expenditure("10"))
                    .build();
            assertTrue(tripList.contains(editedTripA));
        });
    }

    @Test
    void add_nullTrip_throwsNullPointerException() {
        TripList tripList = new TripList();
        assertThrows(NullPointerException.class, () -> tripList.add(null));
    }

    @Test
    void add_duplicateTrip_throwsDuplicateTripException() {
        TripList tripList = new TripList();
        assertDoesNotThrow(() -> {
            tripList.add(TRIP_A);
            assertThrows(DuplicateTripException.class, () -> tripList.add(TRIP_A));
        });
    }

    @Test
    void setTrip_nullTargetTrip_throwsNullPointerException() {
        TripList tripList = new TripList();
        assertThrows(NullPointerException.class, () -> tripList.set(null, TRIP_A));
    }

    @Test
    void setTrip_nullEditedTrip_throwsNullPointerException() {
        TripList tripList = new TripList();
        assertThrows(NullPointerException.class, () -> tripList.set(TRIP_A, null));
    }

    @Test
    void setTrip_targetTripNotInList_throwsTripNotFoundException() {
        TripList tripList = new TripList();
        assertThrows(TripNotFoundException.class, () -> tripList.set(TRIP_A, TRIP_A));
    }

    @Test
    void setTrip_editedTripIsSameTrip_success() {
        TripList tripList = new TripList();
        assertDoesNotThrow(() -> {
            tripList.add(TRIP_A);
            tripList.set(TRIP_A, TRIP_A);
            TripList expectedUniqueTripList = new TripList();
            expectedUniqueTripList.add(TRIP_A);
            assertEquals(expectedUniqueTripList, tripList);
        });
    }

    @Test
    void setTrip_editedTripHasSameIdentity_success() {
        TripList tripList = new TripList();
        assertDoesNotThrow(() -> {
            tripList.add(TRIP_A);
            Trip editedTrip = TripBuilder.of(TRIP_A)
                    .setTotalBudget(new Expenditure("100"))
                    .build();
            tripList.set(TRIP_A, editedTrip);
            TripList expectedUniqueTripList = new TripList();
            expectedUniqueTripList.add(editedTrip);
            assertEquals(expectedUniqueTripList, tripList);
        });
    }

    @Test
    void setTrip_editedTripHasDifferentIdentity_success() {
        TripList tripList = new TripList();
        assertDoesNotThrow(() -> {
            tripList.add(TRIP_A);
            tripList.set(TRIP_A, TRIP_B);
            TripList expectedUniqueTripList = new TripList();
            expectedUniqueTripList.add(TRIP_B);
            assertEquals(expectedUniqueTripList, tripList);
        });
    }

    @Test
    public void setTrip_editedTripHasNonUniqueIdentity_throwsDuplicateTripException() {
        TripList tripList = new TripList();
        assertDoesNotThrow(() -> {
            tripList.add(TRIP_A);
            tripList.add(TRIP_B);
            assertThrows(DuplicateTripException.class, () -> tripList.set(TRIP_A, TRIP_B));
        });
    }

    @Test
    public void remove_nullTrip_throwsNullPointerException() {
        TripList tripList = new TripList();
        assertThrows(NullPointerException.class, () -> tripList.remove((Trip) null));
    }

    @Test
    public void remove_tripDoesNotExist_throwsTripNotFoundException() {
        TripList tripList = new TripList();
        assertThrows(TripNotFoundException.class, () -> tripList.remove(TRIP_A));
    }

    @Test
    public void remove_existingTrip_removesTrip() {
        TripList tripList = new TripList();
        assertDoesNotThrow(() -> {
            tripList.add(TRIP_A);
            tripList.remove(TRIP_A);
            TripList expectedUniqueTripList = new TripList();
            assertEquals(expectedUniqueTripList, tripList);
        });
    }

    /*
    //note list references in these two tests were originally of type TripList
    @Test
    public void setTrips_nullUniqueTripList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tripList.set(null));
    }
    */

    @Test
    public void setTrips_uniqueTripList_replacesOwnListWithProvidedUniqueTripList() {
        TripList tripList = new TripList();
        assertDoesNotThrow(() -> {
            tripList.add(TRIP_A);
            List<Trip> expectedUniqueTripList = new ArrayList<Trip>();
            expectedUniqueTripList.add(TRIP_B);
            tripList.set(expectedUniqueTripList);
            assertEquals(expectedUniqueTripList, tripList.asUnmodifiableObservableList());
        });
    }
    //-------------------------------------------------------------------

    @Test
    public void setTrips_nullList_throwsNullPointerException() {
        TripList tripList = new TripList();
        assertThrows(NullPointerException.class, () -> tripList.set((List<Trip>) null));
    }

    @Test
    public void setTrips_list_replacesOwnListWithProvidedList() {
        TripList tripList = new TripList();
        assertDoesNotThrow(() -> {
            tripList.add(TRIP_A);
            List<Trip> trips = Collections.singletonList(TRIP_B);
            tripList.set(trips);
            TripList expectedUniqueTripList = new TripList();
            expectedUniqueTripList.add(TRIP_B);
            assertEquals(expectedUniqueTripList, tripList);
        });
    }

    @Test
    public void setTrips_listWithDuplicateTrips_throwsDuplicateTripException() {
        TripList tripList = new TripList();
        List<Trip> listWithDuplicateTrips = Arrays.asList(TRIP_A, TRIP_A);
        assertThrows(DuplicateTripException.class, () -> tripList.set(listWithDuplicateTrips));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        TripList tripList = new TripList();
        assertThrows(UnsupportedOperationException.class, () ->
                tripList.asUnmodifiableObservableList().remove(0));
    }

}
