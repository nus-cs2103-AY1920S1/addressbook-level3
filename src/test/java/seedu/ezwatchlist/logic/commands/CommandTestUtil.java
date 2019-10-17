package seedu.ezwatchlist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.logic.parser.CliSyntax.*;
import static seedu.ezwatchlist.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.ezwatchlist.commons.core.index.Index;
import seedu.ezwatchlist.logic.commands.exceptions.CommandException;

import seedu.ezwatchlist.model.Model;
import seedu.ezwatchlist.model.WatchList;
import seedu.ezwatchlist.model.show.NameContainsKeywordsPredicate;
import seedu.ezwatchlist.model.show.Show;
import seedu.ezwatchlist.testutil.EditShowDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy";
    public static final String VALID_NAME_BOB = "Bob the Builder";
    public static final String VALID_DATE_AMY = "11/11/11";
    public static final String VALID_DATE_BOB = "22/2/2222";
    public static final boolean VALID_WATCHED_AMY = true;
    public static final boolean VALID_WATCHED_BOB = false;
    public static final String VALID_DESCRIPTION_AMY = "Horror film";
    public static final String VALID_DESCRIPTION_BOB = "Cartoon about building things";
    public static final int VALID_RUNNING_TIME_AMY = 122;
    public static final int VALID_RUNNING_TIME_BOB = 30;
    public static final String VALID_ACTOR_AMY = "Horror";
    public static final String VALID_ACTOR_BOB = "Kid-friendly";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String DATE_DESC_AMY = " " + PREFIX_DATE_OF_RELEASE + VALID_DATE_AMY;
    public static final String DATE_DESC_BOB = " " + PREFIX_DATE_OF_RELEASE + VALID_DATE_BOB;
    public static final String WATCHED_DESC_AMY = " " + PREFIX_IS_WATCHED + VALID_WATCHED_AMY;
    public static final String WATCHED_DESC_BOB = " " + PREFIX_IS_WATCHED + VALID_WATCHED_BOB;
    public static final String RUNNING_TIME_DESC_AMY = " " + PREFIX_RUNNING_TIME + VALID_RUNNING_TIME_AMY;
    public static final String RUNNING_TIME_DESC_BOB = " " + PREFIX_RUNNING_TIME + VALID_RUNNING_TIME_BOB;
    public static final String DESCRIPTION_DESC_AMY = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_AMY;
    public static final String DESCRIPTION_DESC_BOB = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BOB;
    public static final String ACTOR_DESC_AMY = " " + PREFIX_TYPE + VALID_ACTOR_AMY;
    public static final String ACTOR_DESC_BOB = " " + PREFIX_TYPE + VALID_ACTOR_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE_OF_RELEASE + "911a"; // 'a' not allowed in date
    public static final String INVALID_IS_WATCHED_DESC = " " + PREFIX_IS_WATCHED + null; //null not allowed
    public static final String INVALID_RUNNING_TIME_DESC = " " + PREFIX_RUNNING_TIME; // empty string not allowed for running time
    public static final String INVALID_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION; // empty string not allowed for description
    public static final String INVALID_ACTOR_DESC = " " + PREFIX_ACTOR + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditShowDescriptor DESC_AMY;
    public static final EditCommand.EditShowDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditShowDescriptorBuilder().withName(VALID_NAME_AMY)
                .withDescription(VALID_DESCRIPTION_AMY).withIsWatched(VALID_WATCHED_AMY).withDateOfRelease(VALID_DATE_AMY)
                .withRunningTime(VALID_RUNNING_TIME_AMY).withActors(VALID_ACTOR_AMY).build();
        DESC_BOB = new EditShowDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDescription(VALID_DESCRIPTION_BOB).withIsWatched(VALID_WATCHED_BOB).withDateOfRelease(VALID_DATE_BOB)
                .withRunningTime(VALID_RUNNING_TIME_BOB).withActors(VALID_ACTOR_BOB).build();
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
     * - the watch list, filtered show list and selected show in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        WatchList expectedWatchList = new WatchList(actualModel.getWatchList());
        List<Show> expectedFilteredList = new ArrayList<>(actualModel.getFilteredShowList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedWatchList, actualModel.getWatchList());
        assertEquals(expectedFilteredList, actualModel.getFilteredShowList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the show at the given {@code targetIndex} in the
     * {@code model}'s watch list.
     */
    public static void showShowAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredShowList().size());

        Show show = model.getFilteredShowList().get(targetIndex.getZeroBased());
        final String[] splitName = show.getName().showName.split("\\s+");
        model.updateFilteredShowList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredShowList().size());
    }

}
