package seedu.address.logic.cap;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.cap.commands.CommandResult;
import seedu.address.logic.cap.commands.exceptions.CommandException;
import seedu.address.logic.cap.parser.exceptions.ParseException;
import seedu.address.model.cap.ReadOnlyCapLog;
import seedu.address.model.cap.person.Semester;
import seedu.address.model.common.Module;

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
     * @see seedu.address.model.cap#getCapLog()
     */
    ReadOnlyCapLog getCapLog();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Module> getFilteredModuleList();

    ObservableList<Semester> getFilteredSemesterList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getCapLogFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    double getFilteredCapInformation();

    double getFilteredMcInformation();

    ObservableList<PieChart.Data> getFilteredGradeCounts();

    boolean downRank();

    boolean upRank();

    Image getRankImage();

    String getRankTitle();

    void updateRank(double cap);
}
