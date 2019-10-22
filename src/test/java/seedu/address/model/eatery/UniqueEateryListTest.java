package seedu.address.model.eatery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEateries.ALICE;
import static seedu.address.testutil.TypicalEateries.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.eatery.exceptions.DuplicateEateryException;
import seedu.address.model.eatery.exceptions.EateryNotFoundException;
import seedu.address.testutil.EateryBuilder;

public class UniqueEateryListTest {

    private final UniqueEateryList uniqueEateryList = new UniqueEateryList();

    @Test
    public void contains_nullEatery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.contains(null));
    }

    @Test
    public void contains_eateryNotInList_returnsFalse() {
        assertFalse(uniqueEateryList.contains(ALICE));
    }

    @Test
    public void contains_eateryInList_returnsTrue() {
        uniqueEateryList.add(ALICE);
        assertTrue(uniqueEateryList.contains(ALICE));
    }

    @Test
    public void contains_eateryWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEateryList.add(ALICE);
        Eatery editedAlice = new EateryBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueEateryList.contains(editedAlice));
    }

    @Test
    public void add_nullEatery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.add(null));
    }

    @Test
    public void add_duplicateEatery_throwsDuplicateEateryException() {
        uniqueEateryList.add(ALICE);
        assertThrows(DuplicateEateryException.class, () -> uniqueEateryList.add(ALICE));
    }

    @Test
    public void setEatery_nullTargetEatery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.setEatery(null, ALICE));
    }

    @Test
    public void setEatery_nullEditedEatery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.setEatery(ALICE, null));
    }

    @Test
    public void setEatery_targetEateryNotInList_throwsEateryNotFoundException() {
        assertThrows(EateryNotFoundException.class, () -> uniqueEateryList.setEatery(ALICE, ALICE));
    }

    @Test
    public void setEatery_editedEateryIsSameEatery_success() {
        uniqueEateryList.add(ALICE);
        uniqueEateryList.setEatery(ALICE, ALICE);
        UniqueEateryList expectedUniqueEateryList = new UniqueEateryList();
        expectedUniqueEateryList.add(ALICE);
        assertEquals(expectedUniqueEateryList, uniqueEateryList);
    }

    @Test
    public void setEatery_editedEateryHasSameIdentity_success() {
        uniqueEateryList.add(ALICE);
        Eatery editedAlice = new EateryBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueEateryList.setEatery(ALICE, editedAlice);
        UniqueEateryList expectedUniqueEateryList = new UniqueEateryList();
        expectedUniqueEateryList.add(editedAlice);
        assertEquals(expectedUniqueEateryList, uniqueEateryList);
    }

    @Test
    public void setEatery_editedEateryHasDifferentIdentity_success() {
        uniqueEateryList.add(ALICE);
        uniqueEateryList.setEatery(ALICE, BOB);
        UniqueEateryList expectedUniqueEateryList = new UniqueEateryList();
        expectedUniqueEateryList.add(BOB);
        assertEquals(expectedUniqueEateryList, uniqueEateryList);
    }

    @Test
    public void setEatery_editedEateryHasNonUniqueIdentity_throwsDuplicateEateryException() {
        uniqueEateryList.add(ALICE);
        uniqueEateryList.add(BOB);
        assertThrows(DuplicateEateryException.class, () -> uniqueEateryList.setEatery(ALICE, BOB));
    }

    @Test
    public void remove_nullEatery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.remove(null));
    }

    @Test
    public void remove_eateryDoesNotExist_throwsEateryNotFoundException() {
        assertThrows(EateryNotFoundException.class, () -> uniqueEateryList.remove(ALICE));
    }

    @Test
    public void remove_existingEatery_removesEatery() {
        uniqueEateryList.add(ALICE);
        uniqueEateryList.remove(ALICE);
        UniqueEateryList expectedUniqueEateryList = new UniqueEateryList();
        assertEquals(expectedUniqueEateryList, uniqueEateryList);
    }

    @Test
    public void setEateries_nullUniqueEateryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.setEateries((UniqueEateryList) null));
    }

    @Test
    public void setEateries_uniqueEateryList_replacesOwnListWithProvidedUniqueEateryList() {
        uniqueEateryList.add(ALICE);
        UniqueEateryList expectedUniqueEateryList = new UniqueEateryList();
        expectedUniqueEateryList.add(BOB);
        uniqueEateryList.setEateries(expectedUniqueEateryList);
        assertEquals(expectedUniqueEateryList, uniqueEateryList);
    }

    @Test
    public void setEateries_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.setEateries((List<Eatery>) null));
    }

    @Test
    public void setEateries_list_replacesOwnListWithProvidedList() {
        uniqueEateryList.add(ALICE);
        List<Eatery> eateryList = Collections.singletonList(BOB);
        uniqueEateryList.setEateries(eateryList);
        UniqueEateryList expectedUniqueEateryList = new UniqueEateryList();
        expectedUniqueEateryList.add(BOB);
        assertEquals(expectedUniqueEateryList, uniqueEateryList);
    }

    @Test
    public void setEateries_listWithDuplicateEateries_throwsDuplicateEateryException() {
        List<Eatery> listWithDuplicateEateries = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateEateryException.class, () -> uniqueEateryList.setEateries(listWithDuplicateEateries));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueEateryList.asUnmodifiableObservableList().remove(0));
    }
}
