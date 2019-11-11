package seedu.eatme.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.eatme.commons.core.GuiSettings;
import seedu.eatme.logic.commands.CommandResult;
import seedu.eatme.logic.commands.exceptions.CommandException;
import seedu.eatme.logic.parser.exceptions.ParseException;
import seedu.eatme.model.FeedList;
import seedu.eatme.model.ReadOnlyEateryList;
import seedu.eatme.model.ReadOnlyFeedList;
import seedu.eatme.model.eatery.Eatery;
import seedu.eatme.model.eatery.Review;
import seedu.eatme.model.statistics.Statistics;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the EateryList.
     *
     * @see seedu.eatme.model.Model#getEateryList()
     */
    ReadOnlyEateryList getEateryList();

    /**
     * Returns an unmodifiable view of the filtered list of eateries
     */
    ObservableList<Eatery> getFilteredEateryList();

    /** Returns an unmodifiable view of the filtered list of todos */
    ObservableList<Eatery> getFilteredTodoList();

    /** Returns an unmodifiable view of the active reviews */
    ObservableList<Review> getActiveReviews();

    boolean isMainMode();

    /**
     * Returns the user prefs' eatery list file path.
     */
    Path getEateryListFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the FeedList.
     *
     * @see FeedList#getFeedList() ()
     */
    ReadOnlyFeedList getFeedList();

    /**
     * Returns the user prefs' feed list file path.
     */
    Path getFeedListFilePath();

    /**
     * Saves the feed list to disk.
     */
    void saveFeedList();

    /**
     * Gets the statistics of the eateries and reviews.
     */
    Statistics getStatistics();
}
