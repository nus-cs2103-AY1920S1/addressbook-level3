package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.studyplan.StudyPlan;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<StudyPlan> PREDICATE_SHOW_ALL_STUDYPLANS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' module planner file path.
     */
    Path getModulePlannerFilePath();

    /**
     * Sets the user prefs' module planner file path.
     */
    void setModulePlannerFilePath(Path modulePlannerFilePath);

    /**
     * Replaces module planner data with the data in {@code modulePlanner}.
     */
    void setModulePlanner(ReadOnlyModulePlanner modulePlanner);

    /**
     * Returns the ModulePlanner
     */
    ReadOnlyModulePlanner getModulePlanner();

    /**
     * Returns true if a studyPlan with the same identity as {@code studyPlan} exists in the module planner.
     */
    boolean hasStudyPlan(StudyPlan studyPlan);

    /**
     * Deletes the given studyPlan.
     * The studyPlan must exist in the module planner.
     */
    void deleteStudyPlan(StudyPlan target);

    /**
     * Adds the given studyPlan.
     * {@code studyPlan} must not already exist in the module planner.
     */
    void addStudyPlan(StudyPlan studyPlan);

    /**
     * Replaces the given studyPlan {@code target} with {@code editedStudyPlan}.
     * {@code target} must exist in the module planner.
     * The studyPlan identity of {@code editedStudyPlan} must not be the same as another existing studyPlan in the module planner.
     */
    void setStudyPlan(StudyPlan target, StudyPlan editedStudyPlan);

    /**
     * Returns an unmodifiable view of the filtered studyPlan list
     */
    ObservableList<StudyPlan> getFilteredStudyPlanList();

    /**
     * Updates the filter of the filtered studyPlan list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudyPlanList(Predicate<StudyPlan> predicate);
}
