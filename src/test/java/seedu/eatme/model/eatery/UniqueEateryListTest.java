package seedu.eatme.model.eatery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_ADDRESS_NO_PREFIX_KFC;
import static seedu.eatme.logic.commands.CommandTestUtil.VALID_TAG_NO_PREFIX_CHEAP;
import static seedu.eatme.testutil.Assert.assertThrows;
import static seedu.eatme.testutil.TypicalEateries.KENTUCKY;
import static seedu.eatme.testutil.TypicalEateries.MCDONALD;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.eatme.model.eatery.exceptions.DuplicateEateryException;
import seedu.eatme.model.eatery.exceptions.EateryNotFoundException;
import seedu.eatme.testutil.EateryBuilder;

public class UniqueEateryListTest {

    private final UniqueEateryList uniqueEateryList = new UniqueEateryList();

    @Test
    public void contains_nullEatery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.contains(null));
    }

    @Test
    public void contains_eateryNotInList_returnsFalse() {
        assertFalse(uniqueEateryList.contains(MCDONALD));
    }

    @Test
    public void contains_eateryInList_returnsTrue() {
        uniqueEateryList.add(MCDONALD);
        assertTrue(uniqueEateryList.contains(MCDONALD));
    }

    @Test
    public void contains_eateryWithSameIdentityFieldsInList_returnsTrue() {
        uniqueEateryList.add(MCDONALD);
        Eatery editedAlice = new EateryBuilder(MCDONALD)
                .withAddress(VALID_ADDRESS_NO_PREFIX_KFC)
                .withTags(VALID_TAG_NO_PREFIX_CHEAP)
                .build();
        assertFalse(uniqueEateryList.contains(editedAlice));
    }

    @Test
    public void add_nullEatery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.add(null));
    }

    @Test
    public void add_duplicateEatery_throwsDuplicateEateryException() {
        uniqueEateryList.add(MCDONALD);
        assertThrows(DuplicateEateryException.class, () -> uniqueEateryList.add(MCDONALD));
    }

    @Test
    public void setEatery_nullTargetEatery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.setEatery(null, MCDONALD));
    }

    @Test
    public void setEatery_nullEditedEatery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.setEatery(MCDONALD, null));
    }

    @Test
    public void setEatery_targetEateryNotInList_throwsEateryNotFoundException() {
        assertThrows(EateryNotFoundException.class, () -> uniqueEateryList.setEatery(MCDONALD, MCDONALD));
    }

    @Test
    public void setEatery_editedEateryIsSameEatery_success() {
        uniqueEateryList.add(MCDONALD);
        uniqueEateryList.setEatery(MCDONALD, MCDONALD);
        UniqueEateryList expectedUniqueEateryList = new UniqueEateryList();
        expectedUniqueEateryList.add(MCDONALD);
        assertEquals(expectedUniqueEateryList, uniqueEateryList);
    }

    @Test
    public void setEatery_editedEateryHasSameIdentity_success() {
        uniqueEateryList.add(MCDONALD);
        Eatery editedAlice = new EateryBuilder(MCDONALD)
                .withAddress(VALID_ADDRESS_NO_PREFIX_KFC)
                .withTags(VALID_TAG_NO_PREFIX_CHEAP)
                .build();
        uniqueEateryList.setEatery(MCDONALD, editedAlice);
        UniqueEateryList expectedUniqueEateryList = new UniqueEateryList();
        expectedUniqueEateryList.add(editedAlice);
        assertEquals(expectedUniqueEateryList, uniqueEateryList);
    }

    @Test
    public void setEatery_editedEateryHasDifferentIdentity_success() {
        uniqueEateryList.add(MCDONALD);
        uniqueEateryList.setEatery(MCDONALD, KENTUCKY);
        UniqueEateryList expectedUniqueEateryList = new UniqueEateryList();
        expectedUniqueEateryList.add(KENTUCKY);
        assertEquals(expectedUniqueEateryList, uniqueEateryList);
    }

    @Test
    public void setEatery_editedEateryHasNonUniqueIdentity_throwsDuplicateEateryException() {
        uniqueEateryList.add(MCDONALD);
        uniqueEateryList.add(KENTUCKY);
        assertThrows(DuplicateEateryException.class, () -> uniqueEateryList.setEatery(MCDONALD, KENTUCKY));
    }

    @Test
    public void remove_nullEatery_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.remove(null));
    }

    @Test
    public void remove_eateryDoesNotExist_throwsEateryNotFoundException() {
        assertThrows(EateryNotFoundException.class, () -> uniqueEateryList.remove(MCDONALD));
    }

    @Test
    public void remove_existingEatery_removesEatery() {
        uniqueEateryList.add(MCDONALD);
        uniqueEateryList.remove(MCDONALD);
        UniqueEateryList expectedUniqueEateryList = new UniqueEateryList();
        assertEquals(expectedUniqueEateryList, uniqueEateryList);
    }

    @Test
    public void setEateries_nullUniqueEateryList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.setEateries((UniqueEateryList) null));
    }

    @Test
    public void setEateries_uniqueEateryList_replacesOwnListWithProvidedUniqueEateryList() {
        uniqueEateryList.add(MCDONALD);
        UniqueEateryList expectedUniqueEateryList = new UniqueEateryList();
        expectedUniqueEateryList.add(KENTUCKY);
        uniqueEateryList.setEateries(expectedUniqueEateryList);
        assertEquals(expectedUniqueEateryList, uniqueEateryList);
    }

    @Test
    public void setEateries_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueEateryList.setEateries((List<Eatery>) null));
    }

    @Test
    public void setEateries_list_replacesOwnListWithProvidedList() {
        uniqueEateryList.add(MCDONALD);
        List<Eatery> eateryList = Collections.singletonList(KENTUCKY);
        uniqueEateryList.setEateries(eateryList);
        UniqueEateryList expectedUniqueEateryList = new UniqueEateryList();
        expectedUniqueEateryList.add(KENTUCKY);
        assertEquals(expectedUniqueEateryList, uniqueEateryList);
    }

    @Test
    public void setEateries_listWithDuplicateEateries_throwsDuplicateEateryException() {
        List<Eatery> listWithDuplicateEateries = Arrays.asList(MCDONALD, MCDONALD);
        assertThrows(DuplicateEateryException.class, () -> uniqueEateryList.setEateries(listWithDuplicateEateries));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueEateryList.asUnmodifiableObservableList().remove(0));
    }
}
