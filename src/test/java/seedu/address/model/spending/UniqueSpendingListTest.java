package seedu.address.model.spending;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalSpendings.ALICE;
import static seedu.address.testutil.TypicalSpendings.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.spending.exceptions.DuplicateSpendingException;
import seedu.address.model.spending.exceptions.SpendingNotFoundException;
import seedu.address.testutil.SpendingBuilder;

public class UniqueSpendingListTest {

    private final UniqueSpendingList uniqueSpendingList = new UniqueSpendingList();

    @Test
    public void contains_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSpendingList.contains(null));
    }

    @Test
    public void contains_spendingNotInList_returnsFalse() {
        assertFalse(uniqueSpendingList.contains(ALICE));
    }

    @Test
    public void contains_spendingInList_returnsTrue() {
        uniqueSpendingList.add(ALICE);
        assertTrue(uniqueSpendingList.contains(ALICE));
    }

    @Test
    public void contains_spendingWithSameIdentityFieldsInList_returnsTrue() {
        uniqueSpendingList.add(ALICE);
        Spending editedAlice = new SpendingBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueSpendingList.contains(editedAlice));
    }

    @Test
    public void add_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSpendingList.add(null));
    }

    @Test
    public void add_duplicateSpending_throwsDuplicateSpendingException() {
        uniqueSpendingList.add(ALICE);
        assertThrows(DuplicateSpendingException.class, () -> uniqueSpendingList.add(ALICE));
    }

    @Test
    public void setSpending_nullTargetSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSpendingList.setSpending(null, ALICE));
    }

    @Test
    public void setSpending_nullEditedSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSpendingList.setSpending(ALICE, null));
    }

    @Test
    public void setSpending_targetSpendingNotInList_throwsSpendingNotFoundException() {
        assertThrows(SpendingNotFoundException.class, () -> uniqueSpendingList.setSpending(ALICE, ALICE));
    }

    @Test
    public void setSpending_editedSpendingIsSameSpending_success() {
        uniqueSpendingList.add(ALICE);
        uniqueSpendingList.setSpending(ALICE, ALICE);
        UniqueSpendingList expectedUniqueSpendingList = new UniqueSpendingList();
        expectedUniqueSpendingList.add(ALICE);
        assertEquals(expectedUniqueSpendingList, uniqueSpendingList);
    }

    @Test
    public void setSpending_editedSpendingHasSameIdentity_success() {
        uniqueSpendingList.add(ALICE);
        Spending editedAlice = new SpendingBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueSpendingList.setSpending(ALICE, editedAlice);
        UniqueSpendingList expectedUniqueSpendingList = new UniqueSpendingList();
        expectedUniqueSpendingList.add(editedAlice);
        assertEquals(expectedUniqueSpendingList, uniqueSpendingList);
    }

    @Test
    public void setSpending_editedSpendingHasDifferentIdentity_success() {
        uniqueSpendingList.add(ALICE);
        uniqueSpendingList.setSpending(ALICE, BOB);
        UniqueSpendingList expectedUniqueSpendingList = new UniqueSpendingList();
        expectedUniqueSpendingList.add(BOB);
        assertEquals(expectedUniqueSpendingList, uniqueSpendingList);
    }

    @Test
    public void setSpending_editedSpendingHasNonUniqueIdentity_throwsDuplicateSpendingException() {
        uniqueSpendingList.add(ALICE);
        uniqueSpendingList.add(BOB);
        assertThrows(DuplicateSpendingException.class, () -> uniqueSpendingList.setSpending(ALICE, BOB));
    }

    @Test
    public void remove_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSpendingList.remove(null));
    }

    @Test
    public void remove_spendingDoesNotExist_throwsSpendingNotFoundException() {
        assertThrows(SpendingNotFoundException.class, () -> uniqueSpendingList.remove(ALICE));
    }

    @Test
    public void remove_existingSpending_removesSpending() {
        uniqueSpendingList.add(ALICE);
        uniqueSpendingList.remove(ALICE);
        UniqueSpendingList expectedUniqueSpendingList = new UniqueSpendingList();
        assertEquals(expectedUniqueSpendingList, uniqueSpendingList);
    }

    @Test
    public void setSpendings_nullUniqueSpendingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSpendingList.setSpendings((UniqueSpendingList) null));
    }

    @Test
    public void setSpendings_uniqueSpendingList_replacesOwnListWithProvidedUniqueSpendingList() {
        uniqueSpendingList.add(ALICE);
        UniqueSpendingList expectedUniqueSpendingList = new UniqueSpendingList();
        expectedUniqueSpendingList.add(BOB);
        uniqueSpendingList.setSpendings(expectedUniqueSpendingList);
        assertEquals(expectedUniqueSpendingList, uniqueSpendingList);
    }

    @Test
    public void setSpendings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSpendingList.setSpendings((List<Spending>) null));
    }

    @Test
    public void setSpendings_list_replacesOwnListWithProvidedList() {
        uniqueSpendingList.add(ALICE);
        List<Spending> spendingList = Collections.singletonList(BOB);
        uniqueSpendingList.setSpendings(spendingList);
        UniqueSpendingList expectedUniqueSpendingList = new UniqueSpendingList();
        expectedUniqueSpendingList.add(BOB);
        assertEquals(expectedUniqueSpendingList, uniqueSpendingList);
    }

    @Test
    public void setSpendings_listWithDuplicateSpendings_throwsDuplicateSpendingException() {
        List<Spending> listWithDuplicateSpendings = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateSpendingException.class, ()
            -> uniqueSpendingList.setSpendings(listWithDuplicateSpendings));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueSpendingList.asUnmodifiableObservableList().remove(0));
    }
}
