package seedu.address.model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;
import seedu.address.model.versiontracking.CommitList;

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
     * Activates the first study plan in the list. If the list is null, prompts the user.
     * Returns true if successful, and returns false if no study plan exists.
     */
    boolean activateFirstStudyPlan();

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

    /**
     * Changes the current active study plan's title to a new title.
     *
     * @param title the new title of the current active stucy plan.
     */
    void changeActiveStudyPlanTitle(String title);

    // ===================== VERSION TRACKING ==========================

    /**
     * Commits the current active study plan with a commit message.
     */
    void commitActiveStudyPlan(String commitMessage);

    /**
     * Returns all the commits of the study plan with a given index.
     */
    CommitList getCommitListByStudyPlanIndex(int index);

    /**
     * Deletes a StudyPlanCommitManager by the given StudyPlan index.
     */
    void deleteStudyPlanCommitManagerByIndex(int index);

    /**
     * Reverts the current active study plan to the commit specified by the given index. Make this version
     * of the study plan active.
     */
    void revertToCommit(int studyPlanIndex, int commitNumber);

    /**
     * Deletes the commit specified by the given study plan index and commit number.
     */
    void deleteCommit(int studyPlanIndex, int commitNumber);

    // ===================== MODULE INFORMATION AND VERIFICATION ==========================

    /**
     * Returns true if the module code is a valid, false otherwise.
     */
    boolean isValidModuleCode(String moduleCode);

    /**
     * Returns the ModulesInfo object in the module planner.
     */
    ModulesInfo getModulesInfo();

    /**
     * Returns the module information.
     */
    String getModuleInformation(String moduleCode);

    /**
     * Returns a list of valid modules that can be taken in a given semester.
     */
    List<String> getValidMods(SemesterName semName);

    /**
     * Returns true if a Semester contains a module with same identity as {@code module}.
     */
    boolean semesterHasModule(String moduleCode, SemesterName semesterName);

    /**
     * Refreshes the model by updating the prerequisites, MC count, etc.
     */
    void refresh();

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
    boolean semesterHasUe(SemesterName semesterName);

    void renameUeInSemester(SemesterName semesterName, String moduleCode);

    void setSemester(SemesterName semester);

    Semester getSemester(SemesterName semesterName);

    // ===================== TAGGING ==========================

    boolean addTagToActiveSp(UserTag tag, String moduleCode);

    boolean activeSpContainsTag(String tagName);

    void deleteTagFromActiveSp(UserTag toDelete);

    void removeTagFromAllModulesInActiveSp(UserTag toRemove);

    boolean removeTagFromModuleInActiveSp(UserTag toRemove, String moduleCode);

    void updateAllCompletedTags();

    Tag getTagFromActiveSp(String tagName);

    UniqueTagList getTagsFromActiveSp();

    UniqueTagList getModuleTagsFromActiveSp(String moduleCode);

    HashMap<String, Module> getModulesFromActiveSp();

}
