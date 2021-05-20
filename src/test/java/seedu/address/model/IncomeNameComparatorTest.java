package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_FUNDRAISING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_SHIRTSALES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_FUNDRAISING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_SHIRTSALES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.income.Income;
import seedu.address.testutil.IncomeBuilder;

class IncomeNameComparatorTest {

    public static final Income INCOME_1 = new IncomeBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withDescription(VALID_DESCRIPTION_FUNDRAISING)
            .withAmount(VALID_AMOUNT_FUNDRAISING).withDate("28-09-2019").build();

    public static final Income INCOME_2 = new IncomeBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withDescription(VALID_DESCRIPTION_SHIRTSALES)
            .withAmount(VALID_AMOUNT_SHIRTSALES).withDate("28-09-2018").build();

    private IncomeNameComparator incomeNameComparator = new IncomeNameComparator();

    @Test
    public void comparator_lessThan_negativeInteger() {
        int result = incomeNameComparator.compare(INCOME_1, INCOME_2);
        assertEquals(result, -1);
    }

    @Test
    public void comparator_equal_zero() {
        int result = incomeNameComparator.compare(INCOME_1, INCOME_1);
        assertEquals(result, 0);
    }

    @Test
    public void comparator_moreThan_positiveInteger() {
        int result = incomeNameComparator.compare(INCOME_2, INCOME_1);
        assertEquals(result, 1);
    }
}
