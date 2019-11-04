package seedu.address.logic.quiz;

import java.nio.file.Path;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.quiz.commands.CommandResult;
import seedu.address.logic.quiz.commands.exceptions.CommandException;
import seedu.address.logic.quiz.parser.exceptions.ParseException;
import seedu.address.model.quiz.ReadOnlyQuizBook;
import seedu.address.model.quiz.person.Question;


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
     * Returns the AddressBook.
     *
     * @see seedu.address.model.quiz.Model#getAddressBook()
     */
    ReadOnlyQuizBook getAddressBook();

    /**
     * Return the Question which I am currently viewing.
     */
    int getQuestionNumber();

    /**
     * Return a boolean whether to show the answer or not from the question list.
     */
    boolean getShowAnswer();

    /** Returns an unmodifiable view of the filtered list of questions */
    ObservableList<Question> getFilteredQuestionList();

    /** Returns an unmodifiable view of the filtered list to shown of questions */
    ObservableList<Question> getFilteredShowQuestionList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
