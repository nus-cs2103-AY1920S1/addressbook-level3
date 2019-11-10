package seedu.ichifund.model.repeater;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.TypicalFundBook.REPEATER_PHONE_BILLS;
import static seedu.ichifund.testutil.TypicalFundBook.REPEATER_SALARY;

import org.junit.jupiter.api.Test;

import seedu.ichifund.testutil.RepeaterBuilder;

public class RepeaterTest {

    @Test
    public void equals() {
        // same object -> returns true
        assertTrue(REPEATER_PHONE_BILLS.equals(REPEATER_PHONE_BILLS));

        // different object -> returns false
        assertFalse(REPEATER_PHONE_BILLS.equals(REPEATER_SALARY));

        // null -> returns false
        assertFalse(REPEATER_PHONE_BILLS.equals(null));

        // different month start offset -> returns false
        Repeater editedRepeater;
        if (REPEATER_SALARY.getMonthStartOffset().isIgnored()) {
            editedRepeater = new RepeaterBuilder(REPEATER_SALARY).withMonthStartOffset("1").build();
        } else {
            int editedMonthOffset = Integer.valueOf(REPEATER_SALARY.getMonthStartOffset().toString());
            editedMonthOffset = ((editedMonthOffset + 1) % 28) + 1;
            editedRepeater = new RepeaterBuilder(REPEATER_SALARY)
                .withMonthStartOffset(String.valueOf(editedMonthOffset)).build();
        }
        assertFalse(REPEATER_SALARY.equals(editedRepeater));


        // different unique id = > returns false.
        if (REPEATER_PHONE_BILLS.getUniqueId().toString().equals("42")) {
            editedRepeater = new RepeaterBuilder(REPEATER_PHONE_BILLS)
                    .withUniqueId(String.valueOf(43)).build();
        } else {
            editedRepeater = new RepeaterBuilder(REPEATER_PHONE_BILLS)
                    .withUniqueId(String.valueOf(42)).build();
        }
    }
}
