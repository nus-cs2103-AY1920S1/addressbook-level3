package seedu.algobase.logic;

import java.nio.file.Path;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.logic.commands.CommandResult;
import seedu.algobase.logic.commands.exceptions.CommandException;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.ReadOnlyAlgoBase;
import seedu.algobase.model.gui.GuiState;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.model.task.Task;

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
     * Returns the AlgoBase.
     *
     * @see seedu.algobase.model.Model#getAlgoBase()
     */
    ReadOnlyAlgoBase getAlgoBase();

    /**
     * Returns the current state of the GUI.
     */
    GuiState getGuiState();

    /**
     * Returns an unmodifiable view of the list of commands entered by the user.
     * The list is ordered from the least recent command to the most recent command.
     */
    ObservableList<String> getHistory();

    /**
     * Returns an unmodifiable view of the processed list of problems.
     */
    ObservableList<Problem> getProcessedProblemList();

    /** Returns an unmodifiable view of the filtered list of tags */
    ObservableList<Tag> getProcessedTagList();

    /** Returns an unmodifiable view of the filtered list of plans */
    ObservableList<Plan> getProcessedPlanList();

    /** Returns an unmodifiable view of the filtered list of tasks */
    ObservableList<Task> getProcessedTaskList();

    /** Returns the current plan. */
    StringProperty getCurrentPlan();

    /** Returns the number of done tasks in current plan. */
    IntegerProperty getCurrentDoneCount();

    /** Returns the number of undone tasks in current plan. */
    IntegerProperty getCurrentUndoneCount();

    /** Returns the total number of tasks in current plan. */
    IntegerProperty getCurrentTaskCount();

    /**
     * Returns an unmodifiable view of the filtered list of find rules.
     */
    ObservableList<ProblemSearchRule> getProcessedFindRuleList();

    /**
     * Returns the user prefs' algobase file path.
     */
    Path getAlgoBaseFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
