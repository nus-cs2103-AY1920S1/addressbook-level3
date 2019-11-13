package calofit.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;

import calofit.commons.core.GuiSettings;
import calofit.logic.commands.CommandResult;
import calofit.logic.commands.exceptions.CommandException;
import calofit.logic.parser.exceptions.ParseException;
import calofit.model.Model;
import calofit.model.dish.Dish;
import calofit.model.dish.ReadOnlyDishDatabase;
import calofit.model.meal.Meal;
import calofit.model.util.Statistics;

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

    /**
     * Returns a list of meals that has been consumed today.
     */
    ObservableList<Meal> getTodayMealList();

    /**
     * Returns the {@code Statistics} object that wraps around the data in CaloFit to be displayed to the user.
     */
    Statistics getStatistics();

    /**
     * Returns the {@code Model} that the {@code Logic} is operating on.
     */
    Model getModel();
}
