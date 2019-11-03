package seedu.address.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_CATEGORY_BUFFET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_BUFFET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_PRICE_BUFFET;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIMESTAMP_BUFFET;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalMooLah.BIRTHDAY;
import static seedu.address.testutil.TypicalMooLah.BUFFET;

import org.junit.jupiter.api.Test;

import seedu.address.model.category.Category;
import seedu.address.testutil.EventBuilder;

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
}
