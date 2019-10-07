package calofit.logic;

import calofit.commons.core.GuiSettings;
import calofit.logic.commands.CommandResult;
import calofit.logic.commands.exceptions.CommandException;
import calofit.logic.parser.exceptions.ParseException;
import calofit.model.Model;
import calofit.model.ReadOnlyAddressBook;
import calofit.model.meal.Meal;
import javafx.collections.ObservableList;

import java.nio.file.Path;

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
     * @see Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the filtered list of meals */
    ObservableList<Meal> getFilteredMealList();

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
