package calofit.logic;

import calofit.commons.core.GuiSettings;
import calofit.logic.commands.CommandResult;
import calofit.logic.commands.exceptions.CommandException;
import calofit.logic.parser.exceptions.ParseException;
import calofit.model.Model;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.dish.Dish;
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
     * Returns the DishDatabase.
     *
     * @see Model#getDishDatabase()
     */
    ReadOnlyDishDatabase getDishDatabase();

    /** Returns an unmodifiable view of the filtered list of dishes */
    ObservableList<Dish> getFilteredDishList();

    /**
     * Returns the user prefs' dish database file path.
     */
    Path getDishDatbaseFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
