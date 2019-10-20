package seedu.address.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAppData;
import seedu.address.model.note.Note;
import seedu.address.model.question.Question;
import seedu.address.model.statistics.TempStatsQnsModel;
import seedu.address.model.task.Task;

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
     * Returns the AppData.
     *
     * @see seedu.address.model.Model#getAppData()
     */
    ReadOnlyAppData getAppData();

    /** Returns an unmodifiable view of the filtered list of lecture notes */
    ObservableList<Note> getFilteredNoteList();

    /**
     * Returns an unmodifiable view of the pie chart data.
     */
    ObservableList<PieChart.Data> getStatsChartData();

    ObservableList<TempStatsQnsModel> getStatsQnsList();

    /**
     * Returns the total number of quiz questions done.
     */
    int getTotalQuestionsDone();

    /** Returns an unmodifiable view of the filtered list of questions */
    ObservableList<Question> getFilteredQuestionList();

    /**
     * Returns an unmodifiable view of the filtered list of quiz questions.
     */
    ObservableList<Question> getFilteredQuizQuestionList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAppDataFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    ObservableList<Task> getFilteredTaskList();
}
