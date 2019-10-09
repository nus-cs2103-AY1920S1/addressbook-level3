package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.note.Note;
import seedu.address.model.note.TitleContainsKeywordsPredicate;
import seedu.address.testutil.EditNoteDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_TITLE_AMY = "Amy Bee";
    public static final String VALID_TITLE_BOB = "Bob Choo";
    public static final String VALID_CONTENT_AMY = "Block 312, Amy Street 1";
    public static final String VALID_CONTENT_BOB = "Block 123, Bobby Street 3";

    public static final String TITLE_DESC_AMY = " " + PREFIX_TITLE + VALID_TITLE_AMY;
    public static final String TITLE_DESC_BOB = " " + PREFIX_TITLE + VALID_TITLE_BOB;
    public static final String CONTENT_DESC_AMY = " " + PREFIX_CONTENT + VALID_CONTENT_AMY;
    public static final String CONTENT_DESC_BOB = " " + PREFIX_CONTENT + VALID_CONTENT_BOB;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "f\nsecond"; // \n not allowed for titles
    public static final String INVALID_CONTENT_DESC = " " + PREFIX_CONTENT; // empty not allowed for content

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditNoteCommand.EditNoteDescriptor DESC_AMY;
    public static final EditNoteCommand.EditNoteDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_AMY).withContent(VALID_CONTENT_AMY).build();
        DESC_BOB = new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_BOB).withContent(VALID_CONTENT_BOB).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered person list and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Note> expectedFilteredList = new ArrayList<>(actualModel.getFilteredNoteList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getAddressBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredNoteList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredNoteList().size());

        Note note = model.getFilteredNoteList().get(targetIndex.getZeroBased());
        final String[] splitName = note.getTitle().title.split("\\s+");
        model.updateFilteredNoteList(new TitleContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredNoteList().size());
    }

}
