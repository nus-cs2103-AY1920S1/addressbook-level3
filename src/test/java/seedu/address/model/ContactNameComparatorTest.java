package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalObjects.INCOME_1;
import static seedu.address.testutil.TypicalObjects.INCOME_2;

import org.junit.jupiter.api.Test;

class ContactNameComparatorTest {

    private IncomeNameComparator incomeNameComparator = new IncomeNameComparator();

    @Test
    public void lessThan() {
        int result = incomeNameComparator.compare(INCOME_1, INCOME_2);
        assertEquals(result, -1);
    }

    @Test
    public void equal() {
        int result = incomeNameComparator.compare(INCOME_1, INCOME_1);
        assertEquals(result, 0);
    }

    @Test
    public void moreThan() {
        int result = incomeNameComparator.compare(INCOME_2, INCOME_1);
        assertEquals(result, 1);
    }


}
