package seedu.weme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.weme.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.model.MemeBook;
import seedu.weme.model.Model;
import seedu.weme.model.meme.ImagePath;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.PathMatchesPathPredicate;
import seedu.weme.testutil.EditMemeDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_DESCRIPTION_CHARMANDER = "A meme about Char and charmander.";
    public static final String VALID_DESCRIPTION_JOKER = "A meme about joker.";
    public static final String VALID_FILEPATH_CHARMANDER = "src/test/data/memes/charmander_meme.jpg";
    public static final String VALID_FILEPATH_JOKER = "src/test/data/memes/joker_meme.jpg";
    public static final String VALID_TAG_CHARMANDER = "charmander";
    public static final String VALID_TAG_JOKER = "joker";

    public static final String DESCRIPTION_DESC_CHARMANDER = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_CHARMANDER;
    public static final String DESCRIPTION_DESC_JOKER = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_JOKER;
    public static final String FILEPATH_DESC_CHARMANDER = " " + PREFIX_FILEPATH + VALID_FILEPATH_CHARMANDER;
    public static final String FILEPATH_DESC_JOKER = " " + PREFIX_FILEPATH + VALID_FILEPATH_JOKER;
    public static final String TAG_DESC_CHARMANDER = " " + PREFIX_TAG + VALID_TAG_CHARMANDER;
    public static final String TAG_DESC_JOKER = " " + PREFIX_TAG + VALID_TAG_JOKER;

    public static final String INVALID_FILEPATH_DESC = " " + PREFIX_FILEPATH; // empty string not allowed for file path
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final MemeEditCommand.EditMemeDescriptor DESC_CHARMANDER;
    public static final MemeEditCommand.EditMemeDescriptor DESC_JOKER;

    static {
        DESC_CHARMANDER = new EditMemeDescriptorBuilder().withFilePath(VALID_FILEPATH_CHARMANDER)
                .withDescription(VALID_DESCRIPTION_CHARMANDER).withTags(VALID_TAG_CHARMANDER).build();
        DESC_JOKER = new EditMemeDescriptorBuilder().withFilePath(VALID_FILEPATH_JOKER)
                .withDescription(VALID_DESCRIPTION_JOKER).withTags(VALID_TAG_JOKER).build();
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
     * - the meme book, filtered meme list and selected meme in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        MemeBook expectedMemeBook = new MemeBook(actualModel.getMemeBook());
        List<Meme> expectedFilteredList = new ArrayList<>(actualModel.getFilteredMemeList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedMemeBook, actualModel.getMemeBook());
        assertEquals(expectedFilteredList, actualModel.getFilteredMemeList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the meme at the given {@code targetIndex} in the
     * {@code model}'s meme book.
     */
    public static void showMemeAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMemeList().size());

        Meme meme = model.getFilteredMemeList().get(targetIndex.getZeroBased());
        final ImagePath filePath = meme.getFilePath();
        model.updateFilteredMemeList(new PathMatchesPathPredicate(filePath));

        assertEquals(1, model.getFilteredMemeList().size());
    }

}
