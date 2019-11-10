package seedu.weme.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.weme.logic.commands.memecommand.MemeDeleteCommand.MESSAGE_DELETE_MEME_SUCCESS;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.weme.testutil.Assert.assertThrows;
import static seedu.weme.testutil.MemeUtil.isSameMemeImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.logic.commands.memecommand.MemeEditCommand;
import seedu.weme.model.Model;
import seedu.weme.model.Weme;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.meme.PathMatchesPathPredicate;
import seedu.weme.model.path.ImagePath;
import seedu.weme.model.template.NameContainsKeywordsPredicate;
import seedu.weme.model.template.Template;
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
     * Special method for executing the given AddCommand, checks whether the final model contains the newly added meme.
     * AddCommand requires a different test method as checking for model equality does not work anymore due to
     * the command generating a different hash for the filename every time.
     */
    public static void assertAddCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                               Meme expectedMeme) {
        try {
            int previousSize = actualModel.getFilteredMemeList().size();
            CommandResult result = command.execute(actualModel);
            List<Meme> actualFilteredList = new ArrayList<>(actualModel.getFilteredMemeList());
            Meme addedMeme = actualFilteredList.get(actualFilteredList.size() - 1);

            assertEquals(expectedCommandResult, result);
            assertEquals(previousSize + 1, actualModel.getFilteredMemeList().size());
            assertTrue(isSameMemeImage(expectedMeme, addedMeme));
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertAddCommandSuccess(Command, Model, CommandResult, Meme)} that takes
     * a string {@code expectedMessage}.
     */
    public static void assertAddCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                               Meme expectedMeme) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertAddCommandSuccess(command, actualModel, expectedCommandResult, expectedMeme);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - Weme, filtered meme list and selected meme in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Weme expectedWeme = new Weme(actualModel.getWeme());
        List<Meme> expectedFilteredList = new ArrayList<>(actualModel.getFilteredMemeList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedWeme, actualModel.getWeme());
        assertEquals(expectedFilteredList, actualModel.getFilteredMemeList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the meme at the given {@code targetIndex} in the
     * {@code model}'s Weme.
     */
    public static void showMemeAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMemeList().size());

        Meme meme = model.getFilteredMemeList().get(targetIndex.getZeroBased());
        final ImagePath filePath = meme.getImagePath();
        model.updateFilteredMemeList(new PathMatchesPathPredicate(filePath));

        assertEquals(1, model.getFilteredMemeList().size());
    }

    /**
     * Deletes first meme in filtered meme list.
     */
    public static void deleteFirstMeme(Model model) {
        int initialSize = model.getFilteredMemeList().size();

        assertTrue(initialSize > 0);

        Meme firstMeme = model.getFilteredMemeList().get(0);
        model.deleteMeme(firstMeme);
        String feedback = String.format(MESSAGE_DELETE_MEME_SUCCESS, firstMeme);
        model.commitWeme(feedback);

        assertTrue(initialSize - 1 == model.getFilteredMemeList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the template at the given {@code targetIndex} in the
     * {@code model}'s Weme.
     */
    public static void showTemplateAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTemplateList().size());

        Template template = model.getFilteredTemplateList().get(targetIndex.getZeroBased());
        final String[] splitName = template.getName().toString().split("\\s+");
        model.updateFilteredTemplateList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTemplateList().size());
    }

}
