package seedu.ezwatchlist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DATE_OF_RELEASE;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_IS_WATCHED;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NUM_OF_EPISODES;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_NUM_OF_SEASONS;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_RUNNING_TIME;
import static seedu.ezwatchlist.logic.parser.CliSyntax.PREFIX_TYPE;
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
import seedu.ezwatchlist.testutil.WatchShowDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_ANNABELLE = "Annabelle";
    public static final String VALID_NAME_BOB_THE_BUILDER = "Bob the Builder";
    public static final String VALID_TYPE_ANNABELLE = "movie";
    public static final String VALID_TYPE_BOB_THE_BUILDER = "tv";
    public static final String VALID_DATE_ANNABELLE = "10/10/2014";
    public static final String VALID_DATE_BOB_THE_BUILDER = "1998";
    public static final String VALID_WATCHED_ANNABELLE = "true";
    public static final String VALID_WATCHED_BOB_THE_BUILDER = "false";
    public static final String VALID_DESCRIPTION_ANNABELLE = "Horror film";
    public static final String VALID_DESCRIPTION_BOB_THE_BUILDER = "Cartoon about building things";
    public static final int VALID_RUNNING_TIME_ANNABELLE = 99;
    public static final int VALID_RUNNING_TIME_BOB_THE_BUILDER = 10;
    public static final String VALID_ACTOR_ANNABELLE = "Annabelle Wallis";
    public static final String VALID_ACTOR_BOB_THE_BUILDER = "Rob Rackstraw";
    public static final int VALID_NUM_OF_EPISODES_BOB_THE_BUILDER = 3;
    public static final int VALID_NUM_OF_SEASONS_BOB_THE_BUILDER = 1;

    public static final String NAME_DESC_ANNABELLE = " " + PREFIX_NAME + VALID_NAME_ANNABELLE;
    public static final String NAME_DESC_BOB_THE_BUILDER = " " + PREFIX_NAME + VALID_NAME_BOB_THE_BUILDER;
    public static final String TYPE_DESC_ANNABELLE = " " + PREFIX_TYPE + VALID_TYPE_ANNABELLE;
    public static final String TYPE_DESC_BOB_THE_BUILDER = " " + PREFIX_TYPE + VALID_TYPE_BOB_THE_BUILDER;
    public static final String DATE_DESC_ANNABELLE = " " + PREFIX_DATE_OF_RELEASE + VALID_DATE_ANNABELLE;
    public static final String DATE_DESC_BOB_THE_BUILDER = " " + PREFIX_DATE_OF_RELEASE + VALID_DATE_BOB_THE_BUILDER;
    public static final String WATCHED_DESC_ANNABELLE = " " + PREFIX_IS_WATCHED + VALID_WATCHED_ANNABELLE;
    public static final String WATCHED_DESC_BOB_THE_BUILDER = " " + PREFIX_IS_WATCHED + VALID_WATCHED_BOB_THE_BUILDER;
    public static final String RUNNING_TIME_DESC_ANNABELLE = " " + PREFIX_RUNNING_TIME + VALID_RUNNING_TIME_ANNABELLE;
    public static final String RUNNING_TIME_DESC_BOB_THE_BUILDER =
            " " + PREFIX_RUNNING_TIME + VALID_RUNNING_TIME_BOB_THE_BUILDER;
    public static final String DESCRIPTION_DESC_ANNABELLE = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_ANNABELLE;
    public static final String DESCRIPTION_DESC_BOB_THE_BUILDER =
            " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_BOB_THE_BUILDER;
    public static final String ACTOR_DESC_ANNABELLE = " " + PREFIX_TYPE + VALID_ACTOR_ANNABELLE;
    public static final String ACTOR_DESC_BOB_THE_BUILDER = " " + PREFIX_TYPE + VALID_ACTOR_BOB_THE_BUILDER;
    public static final String EPISODES_DESC_BOB_THE_BUILDER =
            " " + PREFIX_NUM_OF_EPISODES + VALID_NUM_OF_EPISODES_BOB_THE_BUILDER;
    public static final String SEASONS_DESC_BOB_THE_BUILDER =
            " " + PREFIX_NUM_OF_SEASONS + VALID_NUM_OF_SEASONS_BOB_THE_BUILDER;

    public static final String INVALID_TYPE_DESC =
            " " + PREFIX_TYPE + "TV show"; // types can only be "movie" or "tv"
    public static final String INVALID_IS_WATCHED_DESC =
            " " + PREFIX_IS_WATCHED + null; //null not allowed
    public static final String INVALID_RUNNING_TIME_DESC =
            " " + PREFIX_RUNNING_TIME; // empty string not allowed for running time
    public static final String INVALID_DESCRIPTION_DESC =
            " " + PREFIX_DESCRIPTION; // empty string not allowed for description

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditShowDescriptor DESC_ANNABELLE;
    public static final EditCommand.EditShowDescriptor DESC_BOB_THE_BUILDER;

    public static final WatchCommand.WatchShowDescriptor WATCH_DESC_ANNABELLE;
    public static final WatchCommand.WatchShowDescriptor WATCH_DESC_BOB_THE_BUILDER;

    static {
        DESC_ANNABELLE = new EditShowDescriptorBuilder().withName(VALID_NAME_ANNABELLE).withType(VALID_TYPE_ANNABELLE)
                .withDescription(VALID_DESCRIPTION_ANNABELLE).withIsWatched(VALID_WATCHED_ANNABELLE)
                .withDateOfRelease(VALID_DATE_ANNABELLE)
                .withRunningTime(VALID_RUNNING_TIME_ANNABELLE).withActors(VALID_ACTOR_ANNABELLE).build();
        DESC_BOB_THE_BUILDER = new EditShowDescriptorBuilder().withName(VALID_NAME_BOB_THE_BUILDER)
                .withType(VALID_TYPE_BOB_THE_BUILDER)
                .withDescription(VALID_DESCRIPTION_BOB_THE_BUILDER).withIsWatched(VALID_WATCHED_BOB_THE_BUILDER)
                .withDateOfRelease(VALID_DATE_BOB_THE_BUILDER)
                .withRunningTime(VALID_RUNNING_TIME_BOB_THE_BUILDER).withActors(VALID_ACTOR_BOB_THE_BUILDER).build();
    }

    static {
        WATCH_DESC_ANNABELLE = new WatchShowDescriptorBuilder().withName(VALID_NAME_ANNABELLE)
                .withType(VALID_TYPE_ANNABELLE)
                .withDescription(VALID_DESCRIPTION_ANNABELLE).withIsWatched(VALID_WATCHED_ANNABELLE)
                .withDateOfRelease(VALID_DATE_ANNABELLE)
                .withRunningTime(VALID_RUNNING_TIME_ANNABELLE).withActors(VALID_ACTOR_ANNABELLE).build();
        WATCH_DESC_BOB_THE_BUILDER = new WatchShowDescriptorBuilder().withName(VALID_NAME_BOB_THE_BUILDER)
                .withType(VALID_TYPE_BOB_THE_BUILDER)
                .withDescription(VALID_DESCRIPTION_BOB_THE_BUILDER).withIsWatched(VALID_WATCHED_BOB_THE_BUILDER)
                .withDateOfRelease(VALID_DATE_BOB_THE_BUILDER).withTotalNumOfEpisodes(100).withNumOfEpisodesWatched(40)
                .withRunningTime(VALID_RUNNING_TIME_BOB_THE_BUILDER).withActors(VALID_ACTOR_BOB_THE_BUILDER).build();
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
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false);
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
