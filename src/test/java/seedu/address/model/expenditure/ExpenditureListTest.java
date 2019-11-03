package seedu.address.model.expenditure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.expenditure.exceptions.DuplicateExpenditureException;
import seedu.address.model.expenditure.exceptions.ExpenditureNotFoundException;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;
import seedu.address.testutil.ExpenditureBuilder;

public class ExpenditureListTest {

    public static final Expenditure EXPENDITURE_A = ExpenditureBuilder.newInstance().setName(new Name("Expenditure A"))
            .setBudget(new Budget(123))
            .setDayNumber(Optional.of(new DayNumber("1")))
            .setRemovability(true)
            .build();
    public static final Expenditure EXPENDITURE_B = ExpenditureBuilder.newInstance().setName(new Name("Expenditure B"))
            .setBudget(new Budget(123.12))
            .setDayNumber(Optional.of(new DayNumber("1")))
            .setRemovability(false)
            .build();

    @Test
    void contains_nullExpenditure_throwsNullPointerException() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertThrows(NullPointerException.class, () -> expenditureList.contains(null));
    }

    @Test
    void contains_expenditureNotInList_returnsFalse() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertFalse(expenditureList.contains(EXPENDITURE_A));
    }

    @Test
    void contains_expenditureInList_returnsTrue() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertDoesNotThrow(() -> {
            expenditureList.add(EXPENDITURE_A);
            assertTrue(expenditureList.contains(EXPENDITURE_A));
        });
    }

    @Test
    void contains_expenditureWithSameIdentityFieldsInList_returnsTrue() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertDoesNotThrow(() -> {
            expenditureList.add(EXPENDITURE_A);
            Expenditure editedExpenditureA = ExpenditureBuilder.of(EXPENDITURE_A).setBudget(new Budget(10))
                    .build();
            assertTrue(expenditureList.contains(editedExpenditureA));
        });
    }

    @Test
    void add_nullExpenditure_throwsNullPointerException() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertThrows(NullPointerException.class, () -> expenditureList.add(null));
    }

    @Test
    void add_duplicateExpenditure_throwsDuplicateExpenditureException() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertDoesNotThrow(() -> {
            expenditureList.add(EXPENDITURE_A);
            assertThrows(DuplicateExpenditureException.class, () -> expenditureList.add(EXPENDITURE_A));
        });
    }

    @Test
    void setExpenditure_nullTargetExpenditure_throwsNullPointerException() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertThrows(NullPointerException.class, () -> expenditureList.set(null, EXPENDITURE_A));
    }

    @Test
    void setExpenditure_nullEditedExpenditure_throwsNullPointerException() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertThrows(NullPointerException.class, () -> expenditureList.set(EXPENDITURE_A, null));
    }

    @Test
    void setExpenditure_targetExpenditureNotInList_throwsExpenditureNotFoundException() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertThrows(ExpenditureNotFoundException.class, () -> expenditureList.set(EXPENDITURE_A, EXPENDITURE_A));
    }

    @Test
    void setExpenditure_editedExpenditureIsSameExpenditure_success() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertDoesNotThrow(() -> {
            expenditureList.add(EXPENDITURE_A);
            expenditureList.set(EXPENDITURE_A, EXPENDITURE_A);
            ExpenditureList expectedUniqueExpenditureList = new ExpenditureList();
            expectedUniqueExpenditureList.add(EXPENDITURE_A);
            assertEquals(expectedUniqueExpenditureList, expenditureList);
        });
    }

    @Test
    void setExpenditure_editedExpenditureHasSameIdentity_success() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertDoesNotThrow(() -> {
            expenditureList.add(EXPENDITURE_A);
            Expenditure editedExpenditure = ExpenditureBuilder.of(EXPENDITURE_A)
                    .setBudget(new Budget("100"))
                    .build();
            expenditureList.set(EXPENDITURE_A, editedExpenditure);
            ExpenditureList expectedUniqueExpenditureList = new ExpenditureList();
            expectedUniqueExpenditureList.add(editedExpenditure);
            assertEquals(expectedUniqueExpenditureList, expenditureList);
        });
    }

    @Test
    void setExpenditure_editedExpenditureHasDifferentIdentity_success() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertDoesNotThrow(() -> {
            expenditureList.add(EXPENDITURE_A);
            expenditureList.set(EXPENDITURE_A, EXPENDITURE_B);
            ExpenditureList expectedUniqueExpenditureList = new ExpenditureList();
            expectedUniqueExpenditureList.add(EXPENDITURE_B);
            assertEquals(expectedUniqueExpenditureList, expenditureList);
        });
    }

    @Test
    public void setExpenditure_editedExpenditureHasNonUniqueIdentity_throwsDuplicateExpenditureException() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertDoesNotThrow(() -> {
            expenditureList.add(EXPENDITURE_A);
            expenditureList.add(EXPENDITURE_B);
            assertThrows(DuplicateExpenditureException.class, () -> expenditureList.set(EXPENDITURE_A, EXPENDITURE_B));
        });
    }

    @Test
    public void remove_nullExpenditure_throwsNullPointerException() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertThrows(NullPointerException.class, () -> expenditureList.remove((Expenditure) null));
    }

    @Test
    public void remove_expenditureDoesNotExist_throwsExpenditureNotFoundException() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertThrows(ExpenditureNotFoundException.class, () -> expenditureList.remove(EXPENDITURE_A));
    }

    @Test
    public void remove_existingExpenditure_removesExpenditure() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertDoesNotThrow(() -> {
            expenditureList.add(EXPENDITURE_A);
            expenditureList.removeByUser(EXPENDITURE_A);
            ExpenditureList expectedUniqueExpenditureList = new ExpenditureList();
            assertEquals(expectedUniqueExpenditureList, expenditureList);
        });
    }

    /*
    //note list references in these two tests were originally of type ExpenditureList
    @Test
    public void setExpenditures_nullUniqueExpenditureList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> expenditureList.set(null));
    }
    */

    @Test
    public void setExpenditures_uniqueExpenditureList_replacesOwnListWithProvidedUniqueExpenditureList() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertDoesNotThrow(() -> {
            expenditureList.add(EXPENDITURE_A);
            List<Expenditure> expectedUniqueExpenditureList = new ArrayList<Expenditure>();
            expectedUniqueExpenditureList.add(EXPENDITURE_B);
            expenditureList.set(expectedUniqueExpenditureList);
            assertEquals(expectedUniqueExpenditureList, expenditureList.asUnmodifiableObservableList());
        });
    }
    //-------------------------------------------------------------------

    @Test
    public void setExpenditures_nullList_throwsNullPointerException() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertThrows(NullPointerException.class, () -> expenditureList.set((List<Expenditure>) null));
    }

    @Test
    public void setExpenditures_list_replacesOwnListWithProvidedList() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertDoesNotThrow(() -> {
            expenditureList.add(EXPENDITURE_A);
            List<Expenditure> expenditures = Collections.singletonList(EXPENDITURE_B);
            expenditureList.set(expenditures);
            ExpenditureList expectedUniqueExpenditureList = new ExpenditureList();
            expectedUniqueExpenditureList.add(EXPENDITURE_B);
            assertEquals(expectedUniqueExpenditureList, expenditureList);
        });
    }

    @Test
    public void setExpenditures_listWithDuplicateExpenditures_throwsDuplicateExpenditureException() {
        ExpenditureList expenditureList = new ExpenditureList();
        List<Expenditure> listWithDuplicateExpenditures = Arrays.asList(EXPENDITURE_A, EXPENDITURE_A);
        assertThrows(DuplicateExpenditureException.class, () -> expenditureList.set(listWithDuplicateExpenditures));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        ExpenditureList expenditureList = new ExpenditureList();
        assertThrows(UnsupportedOperationException.class, () ->
                expenditureList.asUnmodifiableObservableList().remove(0));
    }

}
