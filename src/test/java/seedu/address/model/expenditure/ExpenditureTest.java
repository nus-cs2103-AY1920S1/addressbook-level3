package seedu.address.model.expenditure;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AFRICA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOTAL_BUDGET_AFRICA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOTAL_BUDGET_BALI;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Name;
import seedu.address.testutil.ExpenditureBuilder;

public class ExpenditureTest {

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
    public void isSameExpenditure() {
        // same object -> returns true
        assertTrue(EXPENDITURE_A.isSameExpenditure(EXPENDITURE_A));

        // null -> returns false
        assertFalse(EXPENDITURE_A.isSameExpenditure(null));

        // different name -> returns false
        Expenditure editedExpenditureA = ExpenditureBuilder.of(EXPENDITURE_A)
                .setName(new Name(VALID_NAME_AFRICA)).build();
        assertFalse(EXPENDITURE_A.isSameExpenditure(editedExpenditureA));

        // same name, same day number, different budget -> returns true
        editedExpenditureA = ExpenditureBuilder.of(EXPENDITURE_A).setBudget(new Budget(VALID_TOTAL_BUDGET_AFRICA))
                .build();
        assertTrue(EXPENDITURE_A.isSameExpenditure(editedExpenditureA));

        // same name, same budget, same day number -> returns true
        editedExpenditureA = ExpenditureBuilder.of(EXPENDITURE_A).build();
        assertTrue(EXPENDITURE_A.isSameExpenditure(editedExpenditureA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Expenditure expenditureACopy = ExpenditureBuilder.of(EXPENDITURE_A).build();
        assertTrue(EXPENDITURE_A.equals(expenditureACopy));

        // same object -> returns true
        assertTrue(EXPENDITURE_A.equals(EXPENDITURE_A));

        // null -> returns false
        assertFalse(EXPENDITURE_A.equals(null));

        // different type -> returns false
        assertFalse(EXPENDITURE_A.equals(5));

        // different expenditure -> returns false
        assertFalse(EXPENDITURE_A.equals(EXPENDITURE_B));

        // different name -> returns false
        Expenditure editedExpenditureA = ExpenditureBuilder.of(EXPENDITURE_A)
                .setName(new Name(VALID_NAME_BALI)).build();
        assertFalse(EXPENDITURE_A.equals(editedExpenditureA));

        // different day number -> returns false
        editedExpenditureA = ExpenditureBuilder.of(EXPENDITURE_A).setDayNumber(Optional.of(
                new DayNumber("2"))).build();
        assertFalse(EXPENDITURE_A.equals(editedExpenditureA));

        // different budget -> returns false
        editedExpenditureA = ExpenditureBuilder.of(EXPENDITURE_A)
                .setBudget(new Budget(VALID_TOTAL_BUDGET_BALI)).build();
        assertFalse(EXPENDITURE_A.equals(editedExpenditureA));

    }

}
