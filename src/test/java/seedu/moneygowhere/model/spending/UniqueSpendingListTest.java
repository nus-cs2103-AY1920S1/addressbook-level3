package seedu.moneygowhere.model.spending;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.APPLE;
import static seedu.moneygowhere.testutil.TypicalSpendings.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.spending.exceptions.DuplicateSpendingException;
import seedu.moneygowhere.model.spending.exceptions.SpendingNotFoundException;
import seedu.moneygowhere.testutil.SpendingBuilder;

public class UniqueSpendingListTest {

    private final UniqueSpendingList uniqueSpendingList = new UniqueSpendingList();

    @Test
    public void contains_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSpendingList.contains(null));
    }

    @Test
    public void contains_spendingNotInList_returnsFalse() {
        assertFalse(uniqueSpendingList.contains(APPLE));
    }

    @Test
    public void contains_spendingInList_returnsTrue() {
        uniqueSpendingList.add(APPLE);
        assertTrue(uniqueSpendingList.contains(APPLE));
    }

    @Test
    public void contains_spendingWithSameIdentityFieldsInList_returnsTrue() {
        uniqueSpendingList.add(APPLE);
        Spending editedAlice = new SpendingBuilder(APPLE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueSpendingList.contains(editedAlice));
    }

    @Test
    public void add_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSpendingList.add(null));
    }

    @Test
    public void add_duplicateSpending_throwsDuplicateSpendingException() {
        uniqueSpendingList.add(APPLE);
        assertThrows(DuplicateSpendingException.class, () -> uniqueSpendingList.add(APPLE));
    }

    @Test
    public void setSpending_nullTargetSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSpendingList.setSpending(null, APPLE));
    }

    @Test
    public void setSpending_nullEditedSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSpendingList.setSpending(APPLE, null));
    }

    @Test
    public void setSpending_targetSpendingNotInList_throwsSpendingNotFoundException() {
        assertThrows(SpendingNotFoundException.class, () -> uniqueSpendingList.setSpending(APPLE, APPLE));
    }

    @Test
    public void setSpending_editedSpendingIsSameSpending_success() {
        uniqueSpendingList.add(APPLE);
        uniqueSpendingList.setSpending(APPLE, APPLE);
        UniqueSpendingList expectedUniqueSpendingList = new UniqueSpendingList();
        expectedUniqueSpendingList.add(APPLE);
        assertEquals(expectedUniqueSpendingList, uniqueSpendingList);
    }

    @Test
    public void setSpending_editedSpendingHasSameIdentity_success() {
        uniqueSpendingList.add(APPLE);
        Spending editedAlice = new SpendingBuilder(APPLE).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueSpendingList.setSpending(APPLE, editedAlice);
        UniqueSpendingList expectedUniqueSpendingList = new UniqueSpendingList();
        expectedUniqueSpendingList.add(editedAlice);
        assertEquals(expectedUniqueSpendingList, uniqueSpendingList);
    }

    @Test
    public void setSpending_editedSpendingHasDifferentIdentity_success() {
        uniqueSpendingList.add(APPLE);
        uniqueSpendingList.setSpending(APPLE, BOB);
        UniqueSpendingList expectedUniqueSpendingList = new UniqueSpendingList();
        expectedUniqueSpendingList.add(BOB);
        assertEquals(expectedUniqueSpendingList, uniqueSpendingList);
    }

    @Test
    public void setSpending_editedSpendingHasNonUniqueIdentity_throwsDuplicateSpendingException() {
        uniqueSpendingList.add(APPLE);
        uniqueSpendingList.add(BOB);
        assertThrows(DuplicateSpendingException.class, () -> uniqueSpendingList.setSpending(APPLE, BOB));
    }

    @Test
    public void remove_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSpendingList.remove(null));
    }

    @Test
    public void remove_spendingDoesNotExist_throwsSpendingNotFoundException() {
        assertThrows(SpendingNotFoundException.class, () -> uniqueSpendingList.remove(APPLE));
    }

    @Test
    public void remove_existingSpending_removesSpending() {
        uniqueSpendingList.add(APPLE);
        uniqueSpendingList.remove(APPLE);
        UniqueSpendingList expectedUniqueSpendingList = new UniqueSpendingList();
        assertEquals(expectedUniqueSpendingList, uniqueSpendingList);
    }

    @Test
    public void setSpendings_nullUniqueSpendingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueSpendingList.setSpendings((UniqueSpendingList) null));
    }

    @Test
    public void setSpendings_uniqueSpendingList_replacesOwnListWithProvidedUniqueSpendingList() {
        uniqueSpendingList.add(APPLE);
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
        uniqueSpendingList.add(APPLE);
        List<Spending> spendingList = Collections.singletonList(BOB);
        uniqueSpendingList.setSpendings(spendingList);
        UniqueSpendingList expectedUniqueSpendingList = new UniqueSpendingList();
        expectedUniqueSpendingList.add(BOB);
        assertEquals(expectedUniqueSpendingList, uniqueSpendingList);
    }

    @Test
    public void setSpendings_listWithDuplicateSpendings_throwsDuplicateSpendingException() {
        List<Spending> listWithDuplicateSpendings = Arrays.asList(APPLE, APPLE);
        assertThrows(DuplicateSpendingException.class, ()
            -> uniqueSpendingList.setSpendings(listWithDuplicateSpendings));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueSpendingList.asUnmodifiableObservableList().remove(0));
    }
}
