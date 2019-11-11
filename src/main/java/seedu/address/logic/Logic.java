package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyKeyboardFlashCards;
import seedu.address.model.category.Category;
import seedu.address.model.deadline.Deadline;
import seedu.address.model.flashcard.FlashCard;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the KeyboardFlashCards.
     *
     * @see seedu.address.model.Model#getKeyboardFlashCards()
     */
    ReadOnlyKeyboardFlashCards getAddressBook();

    /** Returns an unmodifiable view of the filtered list of flashcards */
    ObservableList<FlashCard> getFilteredFlashCardList();


    /** Return an unmodifiable view of the category list**/
    ObservableList<Category> getCategoryList();

    /** Returns an unmodifiable view of the filtered list of deadline */
    ObservableList<Deadline> getFilteredDeadlineList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getKeyboardFlashCardsFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    Model getModel();
}
