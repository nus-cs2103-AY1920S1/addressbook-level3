package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DukeCooks;
import seedu.address.model.Model;
import seedu.address.model.diary.Diary;
import seedu.address.model.diary.NameContainsKeywordsPredicate;
import seedu.address.testutil.EditDiaryDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";

    public static final String NAME_DESC_AMY = " " + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + VALID_NAME_BOB;

    public static final String INVALID_NAME_DESC = " " + "James&"; // '&' not allowed in names

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditDiaryDescriptor DESC_AMY;
    public static final EditCommand.EditDiaryDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditDiaryDescriptorBuilder().withName(VALID_NAME_AMY).build();
        DESC_BOB = new EditDiaryDescriptorBuilder().withName(VALID_NAME_BOB).build();
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
     * - Duke Cooks, filtered diary list and selected diary in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        DukeCooks expectedDukeCooks = new DukeCooks(actualModel.getDukeCooks());
        List<Diary> expectedFilteredList = new ArrayList<>(actualModel.getFilteredDiaryList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedDukeCooks, actualModel.getDukeCooks());
        assertEquals(expectedFilteredList, actualModel.getFilteredDiaryList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the diary at the given {@code targetIndex} in the
     * {@code model}'s Duke Cooks.
     */
    public static void showDiaryAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDiaryList().size());

        Diary diary = model.getFilteredDiaryList().get(targetIndex.getZeroBased());
        final String[] splitName = diary.getName().fullName.split("\\s+");
        model.updateFilteredDiaryList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDiaryList().size());
    }

}
