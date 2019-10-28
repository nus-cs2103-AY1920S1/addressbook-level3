package seedu.address.achievements.logic;

import javafx.scene.chart.XYChart;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * API of the AchievementsLogic component
 */
public interface AchievementsLogic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Get statistics of number of total contacts in Address Book.
     * @return total number of contacts in Address Book
     */
    int getTotalPersons();

    /**
     * Horizontal bar chart data for Address Book.
     * @return horizontal bar chart data for Address Book
     */
    XYChart.Series<Integer, String> getAddressChartData();
}
