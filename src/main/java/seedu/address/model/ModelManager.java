package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.Pair;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.exceptions.StudyPlanNotFoundException;
import seedu.address.model.tag.PriorityTag;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.UserTag;
import seedu.address.model.versiontracking.CommitList;
import seedu.address.model.versiontracking.exception.StudyPlanCommitManagerNotFoundException;

/**
 * Represents the in-memory model of the module planner data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedModulePlanner modulePlanner;
    private final UserPrefs userPrefs;
    private final FilteredList<StudyPlan> filteredStudyPlans;

    /**
     * Initializes a ModelManager with the given ModulePlanner and userPrefs.
     */
    public ModelManager(ReadOnlyModulePlanner modulePlanner, ReadOnlyUserPrefs userPrefs, ModulesInfo modulesInfo) {
        super();
        requireAllNonNull(modulePlanner, userPrefs);

        logger.fine("Initializing with module planner: " + modulePlanner + " and user prefs " + userPrefs);

        this.modulePlanner = new VersionedModulePlanner(modulePlanner, modulesInfo);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudyPlans = new FilteredList<>(this.modulePlanner.getStudyPlanList());
    }

    //    public ModelManager() {
    //        this(new ModulePlanner(), new UserPrefs());
    //    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getModulePlannerFilePath() {
        return userPrefs.getModulePlannerFilePath();
    }

    @Override
    public void setModulePlannerFilePath(Path modulePlannerFilePath) {
        requireNonNull(modulePlannerFilePath);
        userPrefs.setModulePlannerFilePath(modulePlannerFilePath);
    }

    //=========== ModulePlanner ================================================================================

    @Override
    public ReadOnlyModulePlanner getModulePlanner() {
        return modulePlanner;
    }

    @Override
    public void setModulePlanner(ReadOnlyModulePlanner modulePlanner) {
        this.modulePlanner.resetData(modulePlanner);
    }

    @Override
    public boolean hasStudyPlan(StudyPlan studyPlan) {
        requireNonNull(studyPlan);
        return modulePlanner.hasStudyPlan(studyPlan);
    }

    @Override
    public StudyPlan getActiveStudyPlan() {
        return modulePlanner.getActiveStudyPlan();
    }

    @Override
    public StudyPlan activateStudyPlan(int index) throws StudyPlanNotFoundException {
        return modulePlanner.activateStudyPlan(index);
    }

    @Override
    public boolean activateFirstStudyPlan() {
        return modulePlanner.activateFirstStudyPlan();
    }

    @Override
    public void deleteStudyPlan(StudyPlan target) {
        modulePlanner.removeStudyPlan(target);
    }

    @Override
    public void addStudyPlan(StudyPlan studyPlan) {
        modulePlanner.addStudyPlan(studyPlan);
        updateFilteredStudyPlanList(PREDICATE_SHOW_ALL_STUDY_PLANS);
    }

    @Override
    public void setStudyPlan(StudyPlan target, StudyPlan editedStudyPlan) {
        requireAllNonNull(target, editedStudyPlan);

        modulePlanner.setStudyPlan(target, editedStudyPlan);
    }

    @Override
    public void changeActiveStudyPlanTitle(String title) {
        requireNonNull(title);

        modulePlanner.changeActiveStudyPlanTitle(title);
    }

    @Override
    public void deleteAllModulesInSemester(SemesterName semesterName) {
        requireNonNull(semesterName);

        modulePlanner.deleteAllModulesInSemester(semesterName);
    }

    @Override
    public void deleteSemester(SemesterName semesterName) {
        requireNonNull(semesterName);

        modulePlanner.deleteSemester(semesterName);
    }

    //=========== Version Tracking ============================================================================

    @Override
    public void commitActiveStudyPlan(String commitMessage) {
        modulePlanner.commitActiveStudyPlan(commitMessage);
    }

    @Override
    public CommitList getCommitListByStudyPlanIndex(int index) {
        return modulePlanner.getCommitListByStudyPlanIndex(index);
    }

    @Override
    public void deleteStudyPlanCommitManagerByIndex(int index) throws StudyPlanCommitManagerNotFoundException {
        modulePlanner.deleteStudyPlanCommitManagerByIndex(index);
    }

    @Override
    public void revertToCommit(int studyPlanIndex, int commitNumber) {
        modulePlanner.revertToCommit(studyPlanIndex, commitNumber);
    }

    @Override
    public void deleteCommit(int studyPlanIndex, int commitNumber) {
        modulePlanner.deleteCommit(studyPlanIndex, commitNumber);
    }

    //=========== Module Information and Verification ===========================================================

    @Override
    public boolean isValidModuleCode(String moduleCode) {
        return modulePlanner.getModule(moduleCode) != null;
    }

    @Override
    public String getModuleInformation(String moduleCode) {
        return modulePlanner.getModuleInformation(moduleCode);
    }

    @Override
    public ModulesInfo getModulesInfo() {
        return modulePlanner.getModulesInfo();
    }

    @Override
    public void refresh() {
        this.modulePlanner.updatePrereqs();
    }

    @Override
    public List<Module> getValidMods(SemesterName semName) {
        return this.modulePlanner.getValidMods(semName);
    }

    @Override
    public int clearInvalidMods() {
        if (getActiveStudyPlan() == null) {
            return 0;
        }
        List<Pair<SemesterName, String>> invalidModuleCodes = this.modulePlanner.getInvalidModuleCodes();
        for (Pair<SemesterName, String> p : invalidModuleCodes) {
            SemesterName semName = p.getKey();
            String moduleCode = p.getValue();
            this.getSemester(semName).removeModule(moduleCode);
        }
        updateFilteredStudyPlanList(PREDICATE_SHOW_ALL_STUDY_PLANS);
        return invalidModuleCodes.size();
    }

    //=========== Filtered StudyPlan List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code StudyPlan} backed by the internal list of
     * {@code versionedModulePlanner}
     */
    @Override
    public ObservableList<StudyPlan> getFilteredStudyPlanList() {
        return filteredStudyPlans;
    }

    @Override
    public void updateFilteredStudyPlanList(Predicate<StudyPlan> predicate) {
        requireNonNull(predicate);
        filteredStudyPlans.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state checks
        ModelManager other = (ModelManager) obj;

        // check filtered study plans
        try {
            for (int i = 0; i < filteredStudyPlans.size(); i++) {
                StudyPlan sp1 = filteredStudyPlans.get(i);
                StudyPlan sp2 = other.filteredStudyPlans.get(i);
                if (!sp1.getTitle().equals(sp2.getTitle())) {
                    return false;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }

        return modulePlanner.equals(other.modulePlanner)
                && userPrefs.equals(other.userPrefs);
    }

    @Override
    public boolean semesterHasModule(String moduleCode, SemesterName semesterName) {
        Semester semester = getSemester(semesterName);
        return semester.getModules().contains(moduleCode);
    }

    @Override
    public void addModule(String moduleCode, SemesterName semesterName) {
        this.getActiveStudyPlan().addModuleToSemester(new ModuleCode(moduleCode), semesterName);
        updateFilteredStudyPlanList(PREDICATE_SHOW_ALL_STUDY_PLANS);
    }

    @Override
    public void removeModule(String moduleCode, SemesterName semesterName) {
        this.getSemester(semesterName).removeModule(moduleCode);
        updateFilteredStudyPlanList(PREDICATE_SHOW_ALL_STUDY_PLANS);
    }

    @Override
    public Semester getSemester(SemesterName semesterName) {
        for (Semester current : modulePlanner.getActiveStudyPlan().getSemesters()) {
            if (current.getSemesterName() == semesterName) {
                return current;
            }
        }
        return null;
    }

    @Override
    public void setSemester(SemesterName semester) {
        this.modulePlanner.setCurrentSemester(semester);
    }

    @Override
    public void blockSemester(SemesterName sem, String reason) {
        this.modulePlanner.getActiveStudyPlan().blockSemester(sem, reason);
    }

    @Override
    public void unblockSemester(SemesterName sem) {
        this.modulePlanner.getActiveStudyPlan().unblockSemester(sem);
    }
    // ===================== TAGGING ==========================

    public boolean addModuleTagToActiveSp(UserTag tag, String moduleCode) {
        return modulePlanner.addTagToActiveSp(tag, moduleCode);
    }

    public void addStudyPlanTagToSp(Tag tag, int index) {
        modulePlanner.addStudyPlanTagToSp(tag, index);
    }

    public void removeStudyPlanTagFromSp(Tag tag, int index) {
        modulePlanner.removeStudyPlanTagFromSp(tag, index);
    }

    public PriorityTag getPriorityTagFromSp(int index) {
        return modulePlanner.getPriorityTagFromSp(index);
    }

    public boolean spContainsPriorityTag(int index) {
        return modulePlanner.spContainsPriorityTag(index);
    }

    public boolean activeSpContainsModuleTag(String tagName) {
        return modulePlanner.activeSpContainsModuleTag(tagName);
    }

    public boolean spContainsStudyPlanTag(String tagName, int index) {
        return modulePlanner.spContainsStudyPlanTag(tagName, index);
    }

    public Tag getModuleTagFromActiveSp(String tagName) {
        return modulePlanner.getTagFromActiveSp(tagName);
    }

    public UniqueTagList getModuleTagsFromActiveSp() {
        return modulePlanner.getTagsFromActiveSp();
    }

    public UniqueTagList getModuleTagsFromActiveSp(String moduleCode) {
        return modulePlanner.getModuleTagsFromActiveSp(moduleCode);
    }

    public void deleteModuleTagFromActiveSp(UserTag toDelete) {
        modulePlanner.deleteTagFromActiveSp(toDelete);
    }

    public boolean removeTagFromAllModulesInActiveSp(UserTag toRemove) {
        return modulePlanner.removeTagFromAllModulesInActiveSp(toRemove);
    }

    public boolean removeTagFromModuleInActiveSp(UserTag toRemove, String moduleCode) {
        return modulePlanner.removeTagFromModuleInActiveSp(toRemove, moduleCode);
    }

    public void updateAllCompletedTags() {
        modulePlanner.updateAllCompletedTags();
    }

    public HashMap<String, Module> getModulesFromActiveSp() {
        return modulePlanner.getModulesFromActiveSp();
    }

    public UniqueSemesterList getSemestersFromActiveSp() {
        return modulePlanner.getSemestersFromActiveSp();
    }

    public StudyPlan getStudyPlan(int index) throws StudyPlanNotFoundException {
        return modulePlanner.getStudyPlan(index);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoModulePlanner() {
        return modulePlanner.canUndo();
    }

    @Override
    public boolean canRedoModulePlanner() {
        return modulePlanner.canRedo();
    }

    @Override
    public void undoModulePlanner() {
        modulePlanner.undo();
        updateFilteredStudyPlanList(PREDICATE_SHOW_ALL_STUDY_PLANS);
    }

    @Override
    public void redoModulePlanner() {
        modulePlanner.redo();
        updateFilteredStudyPlanList(PREDICATE_SHOW_ALL_STUDY_PLANS);
    }

    @Override
    public void addToHistory() {
        modulePlanner.commit();
    }
}
