package seedu.guilttrip.model.entry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.testutil.TypicalReminders.generateTypicalReminder;
import static seedu.guilttrip.testutil.TypicalEntries.SALARY_INCOME;
import static seedu.guilttrip.testutil.TypicalEntries.CLOTHING_EXPENSE;
import static seedu.guilttrip.testutil.TypicalEntries.FOOD_EXPENSE;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.model.reminders.GeneralReminder;
import seedu.guilttrip.model.reminders.Reminder;
import seedu.guilttrip.model.reminders.conditions.Condition;


public class GeneralReminderTest {
    @Test
    public void equals() {
        GeneralReminder typicalGeneralReminder = generateTypicalReminder();

        // same values -> returns true
        assertTrue(typicalGeneralReminder.equals(generateTypicalReminder()));

        // same object -> returns true
        assertTrue(typicalGeneralReminder.equals(typicalGeneralReminder));

        // null -> returns false
        assertFalse(typicalGeneralReminder.equals(null));

        // different type -> returns false
        assertFalse(typicalGeneralReminder.equals(5));
    }

    @Test
    public void checkStatus() {
        GeneralReminder typicalGeneralReminder = generateTypicalReminder();

        //check that reminder status is unmet by default.
        assertTrue(typicalGeneralReminder.getStatus().equals(Reminder.Status.unmet));

        //check that reminder does not trigger when no conditions are met.
        Income testIncome = SALARY_INCOME;
        for (Condition condition : typicalGeneralReminder.getConditions()) {
            condition.addEntryUpdate(testIncome);
        }
        assertTrue(typicalGeneralReminder.getStatus().equals(Reminder.Status.unmet));

        //check that reminder does not trigger even when some conditions are met.
        Expense testExpense = CLOTHING_EXPENSE;
        for (Condition condition : typicalGeneralReminder.getConditions()) {
            condition.addEntryUpdate(testExpense);
        }
        assertTrue(typicalGeneralReminder.getStatus().equals(Reminder.Status.unmet));

        //Check that reminder triggers when all conditions are met
        Expense passCaseExpense = FOOD_EXPENSE;
        for (Condition condition : typicalGeneralReminder.getConditions()) {
            condition.addEntryUpdate(testExpense);
        }
        assertTrue(typicalGeneralReminder.getStatus().equals(Reminder.Status.met));

        //Check that reset command works
        typicalGeneralReminder.reset();
        assertTrue(typicalGeneralReminder.getStatus().equals(Reminder.Status.unmet));
    }
}
