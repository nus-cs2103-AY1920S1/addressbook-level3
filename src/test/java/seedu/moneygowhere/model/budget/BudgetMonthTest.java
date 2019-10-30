package seedu.moneygowhere.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moneygowhere.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.commons.util.DateUtil;

class BudgetMonthTest {
    private LocalDate old = LocalDate.of(2010, 01, 01);
    private LocalDate now = DateUtil.getTodayDate();
    private LocalDate future = now.plusYears(10);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BudgetMonth(null));
    }

    @Test
    public void constructor_validBudgetMonth_success() {
        BudgetMonth temp = new BudgetMonth(old);
        assertEquals(old.getMonthValue(), temp.getMonth());
        assertEquals(old.getYear(), temp.getYear());
    }

    @Test
    public void parse_invalidBudgetMonth_throwsIllegalArgumentException() {
        String invalidBudgetMonth = " ";
        assertThrows(IllegalArgumentException.class, () -> BudgetMonth.parse(invalidBudgetMonth));
        assertThrows(NullPointerException.class, () -> BudgetMonth.parse(null));
    }

    @Test
    public void getMonth_validInput_success() {
        BudgetMonth temp = new BudgetMonth(old);
        assertEquals(old.getMonthValue(), temp.getMonth());
    }

    @Test
    public void getYear_validInput_success() {
        BudgetMonth temp = new BudgetMonth(old);
        assertEquals(old.getYear(), temp.getYear());
    }

    @Test
    public void now_validInput_success() {
        BudgetMonth temp = new BudgetMonth(now);
        assertEquals(temp, BudgetMonth.now());
    }

    @Test
    public void isValidBudgetMonth_validInput_success() {
        //null Budget Month
        assertThrows(NullPointerException.class, () -> BudgetMonth.isValidBudgetMonth(null));

        //invalid dates
        assertFalse(BudgetMonth.isValidBudgetMonth(" ")); //whitespace
        assertFalse(BudgetMonth.isValidBudgetMonth("")); //empty string
        assertFalse(BudgetMonth.isValidBudgetMonth("asdf")); //invalid format
        assertFalse(BudgetMonth.isValidBudgetMonth("01 2019")); //invalid format

        //valid dates
        assertTrue(BudgetMonth.isValidBudgetMonth("01/2010"));
        assertTrue(BudgetMonth.isValidBudgetMonth("12/2019"));
    }

    @Test
    public void isBehind_validInput_success() {
        assertTrue(BudgetMonth.now().isBehind(future));

        assertFalse(BudgetMonth.now().isBehind(old));
        assertFalse(BudgetMonth.now().isBehind(now));
    }

    @Test
    public void testEquals() {
        BudgetMonth temp = new BudgetMonth(old);
        BudgetMonth temp2 = new BudgetMonth(old.getMonthValue(), old.getYear());
        assertEquals(temp, temp2);
    }
}
