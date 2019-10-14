package seedu.flashcard.logic;

import javafx.collections.ObservableList;
import seedu.flashcard.commons.core.GuiSettings;
import seedu.flashcard.logic.commands.exceptions.CommandException;
import seedu.flashcard.logic.commands.CommandResult;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.ReadOnlyFlashcardList;
import seedu.flashcard.model.flashcard.Flashcard;

import java.nio.file.Path;

/**
 * API of the logic component
 */
public interface Logic {

    /**
     * Executes the command and returns the result.
     * @param CommandText The command as entered by the user
     * @return the result of the command execution
     * @throws CommandException if an error occurs during command execution
     * @throws ParseException if an error occurs during parsing
     */
    CommandResult execute(String CommandText) throws CommandException, ParseException;

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
}
