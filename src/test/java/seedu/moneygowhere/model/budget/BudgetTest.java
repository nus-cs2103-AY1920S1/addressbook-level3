package seedu.moneygowhere.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.testutil.Assert.assertThrows;
import static seedu.moneygowhere.testutil.TypicalSpendings.getSpendingSum;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.spending.Spending;
import seedu.moneygowhere.testutil.SpendingBuilder;

class BudgetTest {

    private Budget oldBudget;
    private LocalDate oldDate;
    private Budget newBudget;
    private LocalDate newDate;

    @BeforeEach
    public void setUp() {
        oldBudget = new Budget(100, "01/2010");
        newBudget = new Budget(123, "12/2020");

        // oldDate is date of spendings in TypicalSPendingBook
        oldDate = LocalDate.of(2019, 01, 01);

        // newDate is not in typicalSpendingBook
        newDate = LocalDate.of(2020, 12, 01);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Budget(null));
    }

    @Test
    public void constructor_invalidBudgetValue_throwsIllegalArgumentException() {
        String invalidBudget = "";
        assertThrows(IllegalArgumentException.class, () -> new Budget(invalidBudget));
    }

    @Test
    public void isValidBudget_validInput_success() {
        //valid Budget
        assertTrue(Budget.isValidBudget("0"));
        assertTrue(Budget.isValidBudget("0.1"));
        assertTrue(Budget.isValidBudget("0.12"));
        assertTrue(Budget.isValidBudget("1000000000"));
    }

    @Test
    public void isValidBudget_invalidInput_false() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Budget.isValidBudget(null));

        //invalid Budgets
        assertFalse(Budget.isValidBudget("")); // empty string
        assertFalse(Budget.isValidBudget(" ")); // spaces only
        assertFalse(Budget.isValidBudget("incorrect")); // strings need to be numbers
        assertFalse(Budget.isValidBudget("-1")); //  numeric strings need to be positive
        assertFalse(Budget.isValidBudget("1000000001")); //  Cannot exceed max amount of 1000000000
        assertFalse(Budget.isValidBudget("100.00000")); //  can only have 2 decimal points
        assertFalse(Budget.isValidBudget("0.000000000000001")); //  can only have 2 decimal points
    }

    @Test
    public void initialize_validInput_success() {
        Budget temp = new Budget(100, "01/2010");
        temp.initialize(newDate, getTypicalSpendingBook().getSpendingList());
        assertEquals(100, temp.getAmount());
        assertEquals((new BudgetMonth(newDate)).toString(), temp.getMonthString());
        assertEquals(0, temp.getSum());

        temp = new Budget(1000, "01/2010");
        temp.initialize(oldDate, getTypicalSpendingBook().getSpendingList());
        assertEquals(1000, temp.getAmount());
        assertEquals((new BudgetMonth(oldDate)).toString(), temp.getMonthString());
        assertEquals(getSpendingSum(), temp.getSum());


    }

    @Test
    public void inSameMonth_validInput_success() {
        Spending temp = new SpendingBuilder().withName("name").withCost("10").withDate("10/01/2010").build();
        assertTrue(oldBudget.inSameMonth(temp));
        assertFalse(newBudget.inSameMonth(temp));
    }

    @Test
    public void addSpending_differentDate_noAdd() {
        Spending temp = new SpendingBuilder().withName("name").withCost("10").withDate("10/01/2010").build();
        Budget newBudgetTemp = new Budget(123, "12/2020");
        newBudgetTemp.addSpending(temp);
        assertEquals(0, newBudgetTemp.getSum());
    }

    @Test
    public void addSpending_validDate_success() {
        Spending temp = new SpendingBuilder().withName("name").withCost("10").withDate("10/01/2010").build();
        Budget oldBudgetTemp = new Budget(100, "01/2010");
        oldBudgetTemp.addSpending(temp);
        assertEquals(10, oldBudgetTemp.getSum());
    }

    @Test
    public void addSpending_multipleSpending_success() {
        List<Spending> spendingList = new ArrayList<>(
            Arrays.asList(
                new SpendingBuilder().withName("name").withCost("10").withDate("10/01/2010").build(),
                new SpendingBuilder().withName("name2").withCost("10").withDate("10/01/2010").build(),
                new SpendingBuilder().withName("name3").withCost("10").withDate("10/01/2010").build()
            )
        );
        Budget oldBudgetTemp = new Budget(100, "01/2010");
        oldBudgetTemp.addSpending(spendingList);
        assertEquals(30, oldBudgetTemp.getSum());
    }

    @Test
    public void deleteSpending_wrongDate_noAdd() {
        Spending temp = new SpendingBuilder().withName("name").withCost("10").withDate("10/01/2010").build();
        double originalValue = newBudget.getAmount();
        double originalSum = newBudget.getSum();

        newBudget.deleteSpending(temp);
        assertEquals(originalValue, newBudget.getAmount());
        assertEquals(originalSum, newBudget.getSum());
    }

    @Test
    public void deleteSpending_validDate_success() {
        Spending temp = new SpendingBuilder().withName("name").withCost("10").withDate("10/01/2010").build();
        double originalSum = oldBudget.getSum();
        oldBudget.deleteSpending(temp);
        assertEquals(originalSum - 10, oldBudget.getSum());
    }
}
