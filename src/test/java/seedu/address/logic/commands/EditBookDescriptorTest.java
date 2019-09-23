package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOOK_1;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AUTHOR_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SERIAL_NUMBER_BOOK_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENRE_ACTION;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditBookDescriptor;
import seedu.address.testutil.EditBookDescriptorBuilder;

public class EditBookDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditBookDescriptor descriptorWithSameValues = new EditCommand.EditBookDescriptor(DESC_BOOK_1);
        assertTrue(DESC_BOOK_1.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_BOOK_1.equals(DESC_BOOK_1));

        // null -> returns false
        assertFalse(DESC_BOOK_1.equals(null));

        // different types -> returns false
        assertFalse(DESC_BOOK_1.equals(5));

        // different values -> returns false
        assertFalse(DESC_BOOK_1.equals(DESC_BOOK_2));

        // different name -> returns false
        EditBookDescriptor editedAmy = new EditBookDescriptorBuilder(DESC_BOOK_1).withTitle(VALID_TITLE_BOOK_2).build();
        assertFalse(DESC_BOOK_1.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditBookDescriptorBuilder(DESC_BOOK_1).withSerialNumber(VALID_SERIAL_NUMBER_BOOK_2).build();
        assertFalse(DESC_BOOK_1.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditBookDescriptorBuilder(DESC_BOOK_1).withAuthor(VALID_AUTHOR_BOOK_2).build();
        assertFalse(DESC_BOOK_1.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditBookDescriptorBuilder(DESC_BOOK_1).withGenres(VALID_GENRE_ACTION).build();
        assertFalse(DESC_BOOK_1.equals(editedAmy));
    }
}
