package seedu.address.model.earnings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEarnings.CS2103T_EARNINGS;
import static seedu.address.testutil.TypicalEarnings.CS2107_EARNINGS;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.earnings.earningsexception.DuplicateEarningsException;
import seedu.address.model.earnings.earningsexception.EarningsNotFoundException;
import seedu.address.testutil.EarningsBuilder;

public class UniqueEarningsListTest {

    private static final String VALID_AMOUNT_CS2100 = "50.00";
    private final UniqueEarningsList uniqueEarningsList = new UniqueEarningsList();

    @Test
    public void contains_nullEarnings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEarningsList.contains(null));
    }

    @Test
    public void contains_earningsNotInList_returnsFalse() {
        assertFalse(uniqueEarningsList.contains(CS2103T_EARNINGS));
    }

    @Test
    public void contains_earningsInList_returnsTrue() {
        uniqueEarningsList.add(CS2103T_EARNINGS);
        assertTrue(uniqueEarningsList.contains(CS2103T_EARNINGS));
    }

    @Test
    public void add_nullEarnigns_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEarningsList.add(null));
    }

    @Test
    public void add_duplicateEarnings_throwsDuplicatePersonException() {
        uniqueEarningsList.add(CS2103T_EARNINGS);
        assertThrows(DuplicateEarningsException.class, () -> uniqueEarningsList.add(CS2103T_EARNINGS));
    }

    @Test
    public void setEarnings_nullTargetEarnings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEarningsList.setEarnings(null, CS2103T_EARNINGS));
    }

    @Test
    public void setEarnings_nullEditedEarnings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> uniqueEarningsList.setEarnings(CS2103T_EARNINGS, null));
    }

    @Test
    public void setEarnings_targetEarningsNotInList_throwsPersonNotFoundException() {
        assertThrows(EarningsNotFoundException.class, ()
            -> uniqueEarningsList.setEarnings(CS2103T_EARNINGS, CS2103T_EARNINGS));
    }

    @Test
    public void setEarnings_editedEarningsIsSameEarnings_success() {
        uniqueEarningsList.add(CS2103T_EARNINGS);
        uniqueEarningsList.setEarnings(CS2103T_EARNINGS, CS2103T_EARNINGS);
        UniqueEarningsList expectedUniqueEarningsList = new UniqueEarningsList();
        expectedUniqueEarningsList.add(CS2103T_EARNINGS);
        assertEquals(expectedUniqueEarningsList, uniqueEarningsList);
    }

    @Test
    public void setEarnings_editedEarningsHasSameIdentity_success() {
        uniqueEarningsList.add(CS2103T_EARNINGS);
        Earnings editedCS2103T = new EarningsBuilder(CS2103T_EARNINGS).withAmount(VALID_AMOUNT_CS2100)
                .build();
        uniqueEarningsList.setEarnings(CS2103T_EARNINGS, editedCS2103T);
        UniqueEarningsList expectedUniqueEarningsList = new UniqueEarningsList();
        expectedUniqueEarningsList.add(editedCS2103T);
        assertEquals(expectedUniqueEarningsList, uniqueEarningsList);
    }

    @Test
    public void setEarnings_editedEarningsHasDifferentIdentity_success() {
        uniqueEarningsList.add(CS2103T_EARNINGS);
        uniqueEarningsList.setEarnings(CS2103T_EARNINGS, CS2107_EARNINGS);
        UniqueEarningsList expectedUniqueEarningsList = new UniqueEarningsList();
        expectedUniqueEarningsList.add(CS2107_EARNINGS);
        assertEquals(expectedUniqueEarningsList, uniqueEarningsList);
    }

    @Test
    public void setEarnings_editedEarningsHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueEarningsList.add(CS2103T_EARNINGS);
        uniqueEarningsList.add(CS2107_EARNINGS);
        assertThrows(DuplicateEarningsException.class, ()
            -> uniqueEarningsList.setEarnings(CS2103T_EARNINGS, CS2107_EARNINGS));
    }

    @Test
    public void remove_nullEarnings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEarningsList.remove(null));
    }

    @Test
    public void remove_earningsDoesNotExist_throwsEarningsNotFoundException() {
        assertThrows(EarningsNotFoundException.class, () -> uniqueEarningsList.remove(CS2103T_EARNINGS));
    }

    @Test
    public void remove_existingEarnings_removesEarnings() {
        uniqueEarningsList.add(CS2103T_EARNINGS);
        uniqueEarningsList.remove(CS2103T_EARNINGS);
        UniqueEarningsList expectedUniqueEarningsList = new UniqueEarningsList();
        assertEquals(expectedUniqueEarningsList, uniqueEarningsList);
    }

    @Test
    public void setEarnings_nullUniqueEarningsList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEarningsList.setEarnings((UniqueEarningsList) null));
    }

    @Test
    public void setEarnings_uniqueEarningsList_replacesOwnListWithProvidedUniqueEarningsList() {
        uniqueEarningsList.add(CS2103T_EARNINGS);
        UniqueEarningsList expectedUniqueEarningsList = new UniqueEarningsList();
        expectedUniqueEarningsList.add(CS2107_EARNINGS);
        uniqueEarningsList.setEarnings(expectedUniqueEarningsList);
        assertEquals(expectedUniqueEarningsList, uniqueEarningsList);
    }

    @Test
    public void setEarnings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEarningsList.setEarnings((List<Earnings>) null));
    }

    @Test
    public void setEarnings_list_replacesOwnListWithProvidedList() {
        uniqueEarningsList.add(CS2107_EARNINGS);
        List<Earnings> personList = Collections.singletonList(CS2103T_EARNINGS);
        uniqueEarningsList.setEarnings(personList);
        UniqueEarningsList expectedUniqueEarningsList = new UniqueEarningsList();
        expectedUniqueEarningsList.add(CS2103T_EARNINGS);
        assertEquals(expectedUniqueEarningsList, uniqueEarningsList);
    }

    @Test
    public void setEarnings_listWithDuplicateEarnings_throwsDuplicateEarningsException() {
        List<Earnings> listWithDuplicateEarnings = Arrays.asList(CS2103T_EARNINGS, CS2103T_EARNINGS);
        assertThrows(DuplicateEarningsException.class, () -> uniqueEarningsList.setEarnings(listWithDuplicateEarnings));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueEarningsList.asUnmodifiableObservableList().remove(0));
    }
}
