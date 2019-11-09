package seedu.moneygowhere.model.spending;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.APPLE;
import static seedu.moneygowhere.testutil.TypicalSpendings.BANANA;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.spending.exceptions.SpendingNotFoundException;
import seedu.moneygowhere.testutil.SpendingBuilder;

public class SpendingListTest {

    private final SpendingList spendingList = new SpendingList();

    @Test
    public void contains_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendingList.contains(null));
    }

    @Test
    public void contains_spendingNotInList_returnsFalse() {
        assertFalse(spendingList.contains(APPLE));
    }

    @Test
    public void contains_spendingInList_returnsTrue() {
        spendingList.add(APPLE);
        assertTrue(spendingList.contains(APPLE));
    }

    @Test
    public void contains_spendingWithSameIdentityFieldsInList_returnsTrue() {
        spendingList.add(APPLE);
        Spending editedAlice = new SpendingBuilder(APPLE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(spendingList.contains(editedAlice));
    }

    @Test
    public void add_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendingList.add((Spending) null));
    }

    @Test
    public void add_nullListSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendingList.add((List<Spending>) null));
    }

    @Test
    public void setSpending_nullTargetSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendingList.setSpending(null, APPLE));
    }

    @Test
    public void setSpending_nullEditedSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendingList.setSpending(APPLE, null));
    }

    @Test
    public void setSpending_targetSpendingNotInList_throwsSpendingNotFoundException() {
        assertThrows(SpendingNotFoundException.class, () -> spendingList.setSpending(APPLE, APPLE));
    }

    @Test
    public void setSpending_editedSpendingIsSameSpending_success() {
        spendingList.add(APPLE);
        spendingList.setSpending(APPLE, APPLE);
        SpendingList expectedSpendingList = new SpendingList();
        expectedSpendingList.add(APPLE);
        assertEquals(expectedSpendingList, spendingList);
    }

    @Test
    public void setSpending_editedSpendingHasSameIdentity_success() {
        spendingList.add(APPLE);
        Spending editedApple = new SpendingBuilder(APPLE).withTags(VALID_TAG_HUSBAND)
                .build();
        spendingList.setSpending(APPLE, editedApple);
        SpendingList expectedSpendingList = new SpendingList();
        expectedSpendingList.add(editedApple);
        assertEquals(expectedSpendingList, spendingList);
    }

    @Test
    public void setSpending_editedSpendingHasDifferentIdentity_success() {
        spendingList.add(APPLE);
        spendingList.setSpending(APPLE, BANANA);
        SpendingList expectedSpendingList = new SpendingList();
        expectedSpendingList.add(BANANA);
        assertEquals(expectedSpendingList, spendingList);
    }

    @Test
    public void remove_nullSpending_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendingList.remove(null));
    }

    @Test
    public void remove_spendingDoesNotExist_throwsSpendingNotFoundException() {
        assertThrows(SpendingNotFoundException.class, () -> spendingList.remove(APPLE));
    }

    @Test
    public void remove_existingSpending_removesSpending() {
        spendingList.add(APPLE);
        spendingList.remove(APPLE);
        SpendingList expectedSpendingList = new SpendingList();
        assertEquals(expectedSpendingList, spendingList);
    }

    @Test
    public void setSpendings_nullSpendingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendingList.setSpendings((SpendingList) null));
    }

    @Test
    public void setSpendings_uniqueSpendingList_replacesOwnListWithProvidedSpendingList() {
        spendingList.add(APPLE);
        SpendingList expectedSpendingList = new SpendingList();
        expectedSpendingList.add(BANANA);
        spendingList.setSpendings(expectedSpendingList);
        assertEquals(expectedSpendingList, spendingList);
    }

    @Test
    public void setSpendings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> spendingList.setSpendings((List<Spending>) null));
    }

    @Test
    public void setSpendings_list_replacesOwnListWithProvidedList() {
        spendingList.add(APPLE);
        List<Spending> spendingList = Collections.singletonList(BANANA);
        this.spendingList.setSpendings(spendingList);
        SpendingList expectedSpendingList = new SpendingList();
        expectedSpendingList.add(BANANA);
        assertEquals(expectedSpendingList, this.spendingList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> spendingList.asUnmodifiableObservableList().remove(0));
    }
}
