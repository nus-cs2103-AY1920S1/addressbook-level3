package seedu.address.model.vehicle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalVehicles.V1;
import static seedu.address.testutil.TypicalVehicles.V2;
import static seedu.address.testutil.TypicalVehicles.V3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.vehicle.exceptions.DuplicateVehicleException;
import seedu.address.model.vehicle.exceptions.VehicleNotFoundException;

public class UniqueVehicleListTest {

    private final UniqueVehicleList uniqueVehicleList = new UniqueVehicleList();

    @Test
    public void contains_nullVehicle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVehicleList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueVehicleList.contains(V1));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueVehicleList.add(V1);
        assertTrue(uniqueVehicleList.contains(V1));
    }

    @Test
    public void add_nullVehicle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVehicleList.add(null));
    }

    @Test
    public void add_duplicateVehicle_throwsDuplicateVehicleException() {
        uniqueVehicleList.add(V2);
        assertThrows(DuplicateVehicleException.class, () -> uniqueVehicleList.add(V2));
    }

    @Test
    public void setVehicle_nullTargetVehicle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVehicleList.setVehicle(null, V1));
    }

    @Test
    public void setVehicle_nullEditedVehicle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVehicleList.setVehicle(V1, null));
    }

    @Test
    public void setVehicle_targetVehicleNotInList_throwsVehicleNotFoundException() {
        assertThrows(VehicleNotFoundException.class, () -> uniqueVehicleList.setVehicle(V3, V3));
    }

    @Test
    public void setVehicle_editedVehicleIsSameVehicle_success() {
        uniqueVehicleList.add(V1);
        uniqueVehicleList.setVehicle(V1, V1);
        UniqueVehicleList expectedUniqueVehicleList = new UniqueVehicleList();
        expectedUniqueVehicleList.add(V1);
        assertEquals(expectedUniqueVehicleList, uniqueVehicleList);
    }

    @Test
    public void setVehicle_editedVehicleHasSameIdentity_success() {
        uniqueVehicleList.add(V2);
        Vehicle editedV2 =
                new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("BBA2222F"),
                new District(6), new Availability("BUSY"));
        uniqueVehicleList.setVehicle(V2, editedV2);
        UniqueVehicleList expectedUniqueVehicleList = new UniqueVehicleList();
        expectedUniqueVehicleList.add(editedV2);
        assertEquals(expectedUniqueVehicleList, uniqueVehicleList);
    }

    @Test
    public void setVehicle_editedVehicleHasDifferentIdentity_success() {
        uniqueVehicleList.add(V3);
        uniqueVehicleList.setVehicle(V3, V2);
        UniqueVehicleList expectedUniqueVehicleList = new UniqueVehicleList();
        expectedUniqueVehicleList.add(V2);
        assertEquals(expectedUniqueVehicleList, uniqueVehicleList);
    }

    @Test
    public void setVehicle_editedVehicleHasNonUniqueIdentity_throwsDuplicateVehicleException() {
        uniqueVehicleList.add(V1);
        uniqueVehicleList.add(V2);
        Vehicle vDuplicate =
                new Vehicle(new VehicleType("Ambulance"), new VehicleNumber("BBA2222F"),
                new District(6), new Availability("BUSY"));
        assertThrows(DuplicateVehicleException.class, () -> uniqueVehicleList.setVehicle(V1, vDuplicate));
    }

    @Test
    public void remove_nullVehicle_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVehicleList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsVehicleNotFoundException() {
        assertThrows(VehicleNotFoundException.class, () -> uniqueVehicleList.remove(V1));
    }

    @Test
    public void remove_existingVehicle_removesVehicle() {
        uniqueVehicleList.add(V1);
        uniqueVehicleList.remove(V1);
        UniqueVehicleList expectedUniqueVehicleList = new UniqueVehicleList();
        assertEquals(expectedUniqueVehicleList, uniqueVehicleList);
    }

    @Test
    public void setVehicles_nullUniqueVehicleList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVehicleList.setVehicles((UniqueVehicleList) null));
    }

    @Test
    public void setVehicles_uniqueVehicleList_replacesOwnListWithProvidedUniqueVehicleList() {
        uniqueVehicleList.add(V1);
        UniqueVehicleList expectedUniqueVehicleList = new UniqueVehicleList();
        expectedUniqueVehicleList.add(V2);
        uniqueVehicleList.setVehicles(expectedUniqueVehicleList);
        assertEquals(expectedUniqueVehicleList, uniqueVehicleList);
    }

    @Test
    public void setVehicles_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueVehicleList.setVehicles((List<Vehicle>) null));
    }

    @Test
    public void setVehicles_list_replacesOwnListWithProvidedList() {
        uniqueVehicleList.add(V2);
        List<Vehicle> personList = Collections.singletonList(V3);
        uniqueVehicleList.setVehicles(personList);
        UniqueVehicleList expectedUniqueVehicleList = new UniqueVehicleList();
        expectedUniqueVehicleList.add(V3);
        assertEquals(expectedUniqueVehicleList, uniqueVehicleList);
    }

    @Test
    public void setVehicles_listWithDuplicateVehicles_throwsDuplicateVehicleException() {
        List<Vehicle> listWithDuplicateVehicles = Arrays.asList(V1, V1);
        assertThrows(DuplicateVehicleException.class, () -> uniqueVehicleList.setVehicles(listWithDuplicateVehicles));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueVehicleList.asUnmodifiableObservableList().remove(0));
    }
}
