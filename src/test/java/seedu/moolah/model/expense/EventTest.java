package seedu.moolah.model.expense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_CATEGORY_BIRTHDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_CATEGORY_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_BIRTHDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_PRICE_BIRTHDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_PRICE_BUFFET;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_TIMESTAMP_BIRTHDAY;
import static seedu.moolah.logic.commands.CommandTestUtil.VALID_EVENT_TIMESTAMP_BUFFET;
import static seedu.moolah.model.expense.Timestamp.createTimestampIfValid;
import static seedu.moolah.testutil.Assert.assertThrows;
import static seedu.moolah.testutil.TypicalMooLah.BIRTHDAY;
import static seedu.moolah.testutil.TypicalMooLah.BUFFET;
import static seedu.moolah.testutil.TypicalMooLah.SCHOOL;

import org.junit.jupiter.api.Test;

import seedu.moolah.model.budget.Budget;
import seedu.moolah.testutil.BudgetBuilder;
import seedu.moolah.testutil.EventBuilder;
import seedu.moolah.testutil.ExpenseBuilder;

public class EventTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Event(null, new Price("1"),
                        new Category("FOOD"), null));
        assertThrows(NullPointerException.class, () ->
                new Event(new Description("meat"), null,
                        new Category("FOOD"), null));
        assertThrows(NullPointerException.class, () ->
                new Event(new Description("meat"), new Price("1"),
                        null, null));
        assertThrows(NullPointerException.class, () ->
                new Event(null, null,
                        null, null, new Description("Default Budget")));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event birthdayCopy = new EventBuilder(BIRTHDAY).build();
        assertTrue(BIRTHDAY.equals(birthdayCopy));

        // same object -> returns true
        assertTrue(BIRTHDAY.equals(BIRTHDAY));

        // null -> returns false
        assertFalse(BIRTHDAY.equals(null));

        // different type -> returns false
        assertFalse(BIRTHDAY.equals(5));

        // differet event -> returns false
        assertFalse(BIRTHDAY.equals(BUFFET));

        // different description -> returns false
        Event editedBirthday = new EventBuilder(BIRTHDAY).withDescription(VALID_EVENT_DESCRIPTION_BUFFET).build();
        assertFalse(BIRTHDAY.equals(editedBirthday));

        // different price -> returns false
        editedBirthday = new EventBuilder(BIRTHDAY).withPrice(VALID_EVENT_PRICE_BUFFET).build();
        assertFalse(BIRTHDAY.equals(editedBirthday));

        // different timestamp -> returns false
        editedBirthday = new EventBuilder(BIRTHDAY).withTimestamp(VALID_EVENT_TIMESTAMP_BUFFET).build();
        assertFalse(BIRTHDAY.equals(editedBirthday));

        // different category -> returns false
        editedBirthday = new EventBuilder(BIRTHDAY).withCategory(VALID_EVENT_CATEGORY_BUFFET).build();
        assertFalse(BIRTHDAY.equals(editedBirthday));
    }

    @Test void setBudget() {
        Event event = new EventBuilder(BIRTHDAY).build();
        Budget schoolCopy = new BudgetBuilder(SCHOOL).build();
        event.setBudget(schoolCopy);
        Description expectedBudgetName = schoolCopy.getDescription();
        assertEquals(expectedBudgetName, event.getBudgetName());
    }

    @Test
    public void convertToExpense() {
        Event event = new EventBuilder(BIRTHDAY).build();
        Expense convertedExpense = event.convertToExpense();
        Expense expectedExpense = new ExpenseBuilder()
                .withDescription(VALID_EVENT_DESCRIPTION_BIRTHDAY)
                .withPrice(VALID_EVENT_PRICE_BIRTHDAY)
                .withTimestamp(VALID_EVENT_TIMESTAMP_BIRTHDAY)
                .withCategory(VALID_EVENT_CATEGORY_BIRTHDAY)
                .withUniqueIdentifier(convertedExpense.getUniqueIdentifier().toString())
                .build();
        assertEquals(expectedExpense, convertedExpense);
    }

    @Test
    public void testToString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Description: ")
                .append(new Description(VALID_EVENT_DESCRIPTION_BIRTHDAY))
                .append(" Price: ")
                .append(new Price(VALID_EVENT_PRICE_BIRTHDAY))
                .append(" Category: ")
                .append(new Category(VALID_EVENT_CATEGORY_BIRTHDAY))
                .append(" Timestamp: ")
                .append(createTimestampIfValid(VALID_EVENT_TIMESTAMP_BIRTHDAY).get());
        String expectedString = builder.toString();
        Event event = new EventBuilder(BIRTHDAY).build();
        assertEquals(expectedString, event.toString());
    }
}
