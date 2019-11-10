package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CARDNUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.NoteBook;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditNoteDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class NotesCommandTestUtil {

    public static final String VALID_STRING_COMMAND_ARG = "";
    public static final String VALID_TITLE_DIARYONE = "Diary One";
    public static final String VALID_TITLE_DIARYTWO = "Diary Two";
    public static final String VALID_DESCRIPTION_DIARYONE = "first diary";
    public static final String VALID_DESCRIPTION_DIARYTWO = "22222222";
    public static final String VALID_CONTENT_DIARYONE = "ABC123";
    public static final String VALID_CONTENT_DIARYTWO = "DEF456";
    public static final String VALID_TAG_WORK = "work";
    public static final String VALID_TAG_PERSONAL = "personal";

    public static final String TITLE_DESC_DIARYONE = " " + PREFIX_TITLE + VALID_TITLE_DIARYONE;
    public static final String TITLE_DESC_DIARYTWO = " " + PREFIX_TITLE + VALID_TITLE_DIARYTWO;
    public static final String DESC_DESC_DIARYONE = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_DIARYONE;
    public static final String DESC_DESC_DIARYTWO = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_DIARYTWO;
    public static final String CONTENT_DESC_DIARYONE = " " + PREFIX_CONTENT + VALID_CONTENT_DIARYONE;
    public static final String CONTENT_DESC_DIARYTWO = " " + PREFIX_CONTENT + VALID_CONTENT_DIARYTWO;
    public static final String TAG_DESC_WORK = " " + PREFIX_TAG + VALID_TAG_WORK;
    public static final String TAG_DESC_PERSONAL = " " + PREFIX_TAG + VALID_TAG_PERSONAL;

    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + ""; // '&' blanks not allowed
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION + ""; // blanks not allowed
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "Work*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditNoteCommand.EditNoteDescriptor DESC_DIARYONE;
    public static final EditNoteCommand.EditNoteDescriptor DESC_DIARYTWO;

    static {
        DESC_DIARYONE = new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_DIARYONE)
                .withDescription(VALID_DESCRIPTION_DIARYONE).withContent(VALID_DESCRIPTION_DIARYONE)
                .withTags(VALID_TAG_WORK).build();
        DESC_DIARYTWO = new EditNoteDescriptorBuilder().withTitle(VALID_TITLE_DIARYTWO)
                .withDescription(VALID_DESCRIPTION_DIARYTWO).withContent(VALID_DESCRIPTION_DIARYTWO)
                .withTags(VALID_TAG_WORK).build();
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
        NoteBook expectedNoteBook = new NoteBook(actualModel.getNoteBook());
        List<Note> expectedFilteredList = new ArrayList<>(actualModel.getFilteredNoteList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedNoteBook, actualModel.getNoteBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredNoteList());
    }
//    /**
//     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
//     * {@code model}'s address book.
//     */
//    public static void showNoteAtIndex(Model model, Index targetIndex) {
//        assertTrue(targetIndex.getZeroBased() < model.getFilteredNoteList().size());
//
//        Note note = model.getFilteredNoteList().get(targetIndex.getZeroBased());
//        final String[] splitTitle = note.getTitle().title.split("\\s+");
//        model.updateFilteredNoteList(new NoteContainsKeywordsPredicate(Arrays.asList(splitName[0])));
//
//        assertEquals(1, model.getFilteredPersonList().size());
//    }

}
