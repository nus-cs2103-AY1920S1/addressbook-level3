package seedu.flashcard.logic;

import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import seedu.flashcard.commons.core.GuiSettings;
import seedu.flashcard.logic.commands.CommandResult;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.ReadOnlyFlashcardList;
import seedu.flashcard.model.Statistics;
import seedu.flashcard.model.flashcard.Flashcard;



/**
 * API of the logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user
     * @return the result of the command execution
     * @throws CommandException if an error occurs during command execution
     * @throws ParseException if an error occurs during parsing
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Get the flashcard list.
     * @see seedu.flashcard.model.Model#getFlashcardList()
     */
    ReadOnlyFlashcardList getFlashcardList();

    /**
     * Get an unmodifiable view of the filtered list of flashcards
     * @return
     */
    ObservableList<Flashcard> getFilteredFlashcardList();

    /**
     * Get the user prefs' flashcard list file path
     */
    Path getFlashcardListFilePath();

    /**
     * Get the user prefs' GUI settings
     * @return
     */
    GuiSettings getGuiSettings();

    /**
     * set user prefs' GUI settings
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Get the desired statistics
     * @return
     */
    Statistics getStatistics();

    /**
     * Get the current quiz mode
     * @return
     */
    boolean isQuiz();

    /**
     *returns the duration .
     */
    IntegerProperty getDurationProperty();

    /**
     *returns the totalCards Property.
     */
    IntegerProperty getTotalCardsProperty();

    /**
     * returns the remainingCards Property.
     */
    IntegerProperty getRemainingCardsProperty();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();


}
