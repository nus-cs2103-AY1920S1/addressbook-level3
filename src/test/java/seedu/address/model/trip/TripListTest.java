package seedu.address.model.trip;

import org.junit.jupiter.api.Test;
import seedu.address.model.itinerary.Expenditure;
import seedu.address.model.trip.exceptions.ClashingTripException;
import seedu.address.model.trip.exceptions.DuplicateTripException;
import seedu.address.model.trip.exceptions.TripNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalTrips.TRIP_A;
import static seedu.address.testutil.TypicalTrips.TRIP_B;

public class TripListTest {
    private final TripList tripList = new TripList();

    @Test
    public void contains_nullTrip_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tripList.contains(null));
    }

    @Test
    public void contains_tripNotInList_returnsFalse() {
        assertFalse(tripList.contains(TRIP_A));
    }

    @Test
    public void contains_tripInList_returnsTrue() {
        try {
            tripList.add(TRIP_A);
            assertTrue(tripList.contains(TRIP_A));
        } catch (ClashingTripException e) {

        }
    }

    @Test
    public void contains_tripWithSameIdentityFieldsInList_returnsTrue() {
        try {
            tripList.add(TRIP_A);
            Trip editedTripA = Trip.Builder.of(TRIP_A).setTotalBudget(new Expenditure("10"))
                    .build();
            assertTrue(tripList.contains(editedTripA));
        } catch (ClashingTripException e){

        }
    }

    @Test
    public void add_nullTrip_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tripList.add(null));
    }

    @Test
    public void add_duplicateTrip_throwsDuplicateTripException() {
        try {
            tripList.add(TRIP_A);
            assertThrows(DuplicateTripException.class, () -> tripList.add(TRIP_A));
        } catch(ClashingTripException e){

        }
    }

    @Test
    public void setTrip_nullTargetTrip_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tripList.set(null, TRIP_A));
    }

    @Test
    public void setTrip_nullEditedTrip_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tripList.set(TRIP_A, null));
    }

    @Test
    public void setTrip_targetTripNotInList_throwsTripNotFoundException() {
        assertThrows(TripNotFoundException.class, () -> tripList.set(TRIP_A, TRIP_A));
    }

    @Test
    public void setTrip_editedTripIsSameTrip_success() {
        try {
            tripList.add(TRIP_A);
            tripList.set(TRIP_A, TRIP_A);
            TripList expectedUniqueTripList = new TripList();
            expectedUniqueTripList.add(TRIP_A);
            assertEquals(expectedUniqueTripList, tripList);
        } catch (ClashingTripException | TripNotFoundException e){

        }
    }

    @Test
    public void setTrip_editedTripHasSameIdentity_success() {
        try{
            tripList.add(TRIP_A);
            Trip editedTrip = Trip.Builder.of(TRIP_A).setTotalBudget(new Expenditure("100"))
                    .build();
            tripList.set(TRIP_A, editedTrip);
            TripList expectedUniqueTripList = new TripList();
            expectedUniqueTripList.add(editedTrip);
            assertEquals(expectedUniqueTripList, tripList);
        } catch (ClashingTripException | TripNotFoundException e){

        }
}

    @Test
    public void setTrip_editedTripHasDifferentIdentity_success() {
        try{
            tripList.add(TRIP_A);
            tripList.set(TRIP_A, TRIP_B);
            TripList expectedUniqueTripList = new TripList();
            expectedUniqueTripList.add(TRIP_B);
            assertEquals(expectedUniqueTripList, tripList);
        } catch (ClashingTripException | TripNotFoundException e){

        }
    }

    @Test
    public void setTrip_editedTripHasNonUniqueIdentity_throwsDuplicateTripException() {
        try {
            tripList.add(TRIP_A);
            tripList.add(TRIP_B);
            assertThrows(DuplicateTripException.class, () -> tripList.set(TRIP_A, TRIP_B));
        } catch (ClashingTripException e){

        }
    }

    @Test
    public void remove_nullTrip_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tripList.remove(null));
    }

    @Test
    public void remove_tripDoesNotExist_throwsTripNotFoundException() {
        assertThrows(TripNotFoundException.class, () -> tripList.remove(TRIP_A));
    }

    @Test
    public void remove_existingTrip_removesTrip() {
        try {
            tripList.add(TRIP_A);
            tripList.remove(TRIP_A);
            TripList expectedUniqueTripList = new TripList();
            assertEquals(expectedUniqueTripList, tripList);
        } catch (ClashingTripException | TripNotFoundException e){

        }
    }

//    //note list references in these two tests were originally of type TripList
//    @Test
//    public void setTrips_nullUniqueTripList_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> tripList.set(null));
//    }

    @Test
    public void setTrips_uniqueTripList_replacesOwnListWithProvidedUniqueTripList() {
        try {
            tripList.add(TRIP_A);
            List<Trip> expectedUniqueTripList = new ArrayList<Trip>();
            expectedUniqueTripList.add(TRIP_B);
            tripList.set(expectedUniqueTripList);
            assertEquals(expectedUniqueTripList, tripList);
        } catch (ClashingTripException e){

        }
    }
    //-------------------------------------------------------------------

    @Test
    public void setTrips_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> tripList.set((List<Trip>) null));
    }

    @Test
    public void setTrips_list_replacesOwnListWithProvidedList() {
        try {
            tripList.add(TRIP_A);
            List<Trip> trips = Collections.singletonList(TRIP_B);
            tripList.set(trips);
            TripList expectedUniqueTripList = new TripList();
            expectedUniqueTripList.add(TRIP_B);
            assertEquals(expectedUniqueTripList, tripList);
        } catch (ClashingTripException e){

        }
    }

    @Test
    public void setTrips_listWithDuplicateTrips_throwsDuplicateTripException() {
        List<Trip> listWithDuplicateTrips = Arrays.asList(TRIP_A, TRIP_A);
        assertThrows(DuplicateTripException.class, () -> tripList.set(listWithDuplicateTrips));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> tripList.asUnmodifiableObservableList().remove(0));
    }

}
