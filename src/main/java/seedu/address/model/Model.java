package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
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
     * Replaces module planner data with the data in {@code ModulePlanner}.
     */
    void setModulePlanner(ReadOnlyModulePlanner modulePlanner);

    /**
     * Returns the ModulePlanner
     */
    ReadOnlyModulePlanner getModulePlanner();

    /**
     * Returns true if a StudyPlan with the same identity as {@code StudyPlan} exists in the module planner.
     */
    boolean hasStudyPlan(StudyPlan studyPlan);

    /**
     * Returns the current active {@code StudyPlan}.
     */
    StudyPlan getActiveStudyPlan();

    /**
     * Returns an activated study plan with the given index populated with relevant details.
     */
    StudyPlan activateStudyPlan(int index);

    /**
     * Deletes the given StudyPlan.
     * The StudyPlan must exist in the module planner.
     */
    void deleteStudyPlan(StudyPlan target);

    /**
     * Adds the given StudyPlan.
     * {@code StudyPlan} must not already exist in the module planner.
     */
    void addStudyPlan(StudyPlan studyPlan);

    /**
     * Replaces the given StudyPlan {@code target} with {@code editedStudyPlan}.
     * {@code target} must exist in the module planner.
     * The StudyPlan identity of {@code editedStudyPlan} must not be the same as another existing StudyPlan
     * in the module planner.
     */
    void setStudyPlan(StudyPlan target, StudyPlan editedStudyPlan);

    /**
     * Returns an unmodifiable view of the filtered StudyPlan list
     */
    ObservableList<StudyPlan> getFilteredStudyPlanList();

    /**
     * Updates the filter of the filtered StudyPlan list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudyPlanList(Predicate<StudyPlan> predicate);

    // ===================== MODULE INFORMATION ==========================

    /**
     * Returns true if the module code is a valid, false otherwise.
     */
    boolean isValidModuleCode(String moduleCode);

    /**
     * Returns the module information.
     */
    String getModuleInformation(String moduleCode);


    /**
     * Returns true if a Semester contains a module with same identity as {@code module}.
     */
    boolean semesterHasModule(String moduleCode, SemesterName semesterName);

    /**
     * Adds specified module to specified semester
     */
    void addModule(String moduleCode, SemesterName sem);

    /**
     * Blocks specified semester with given reason
     */
    void blockSemester(SemesterName sem, String reason);

    /**
     * Removes module from semester
     */
    void removeModule(String moduleCode, SemesterName semesterName);

    /**
     * Checks whether or not the specified semester contains a UE
     */
    boolean semesterHasUE(SemesterName semesterName);

    void renameUEInSemester(SemesterName semesterName, String moduleCode);

    void setSemester(SemesterName semester);

    Semester getSemester(SemesterName semesterName);

}
