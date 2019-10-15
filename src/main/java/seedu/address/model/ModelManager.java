package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.versiontracking.CommitList;

/**
 * Represents the in-memory model of the module planner data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ModulePlanner modulePlanner;
    private final UserPrefs userPrefs;
    private final FilteredList<StudyPlan> filteredStudyPlans;

    /**
     * Initializes a ModelManager with the given ModulePlanner and userPrefs.
     */
    public ModelManager(ReadOnlyModulePlanner modulePlanner, ReadOnlyUserPrefs userPrefs, ModulesInfo modulesInfo) {
        super();
        requireAllNonNull(modulePlanner, userPrefs);

        logger.fine("Initializing with module planner: " + modulePlanner + " and user prefs " + userPrefs);

        this.modulePlanner = new ModulePlanner(modulePlanner, modulesInfo);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudyPlans = new FilteredList<>(this.modulePlanner.getStudyPlanList());
    }

    //    public ModelManager() {
    //        this(new ModulePlanner(), new UserPrefs());
    //    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
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
    public void setModulePlanner(ReadOnlyModulePlanner modulePlanner) {
        this.modulePlanner.resetData(modulePlanner);
    }

    @Override
    public ReadOnlyModulePlanner getModulePlanner() {
        return modulePlanner;
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
    public StudyPlan activateStudyPlan(int index) {
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
        updateFilteredStudyPlanList(PREDICATE_SHOW_ALL_STUDYPLANS);
    }

    @Override
    public void setStudyPlan(StudyPlan target, StudyPlan editedStudyPlan) {
        requireAllNonNull(target, editedStudyPlan);

        modulePlanner.setStudyPlan(target, editedStudyPlan);
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

    //=========== Module Information ============================================================================

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

        // state check
        ModelManager other = (ModelManager) obj;
        return modulePlanner.equals(other.modulePlanner)
                && userPrefs.equals(other.userPrefs)
                && filteredStudyPlans.equals(other.filteredStudyPlans);
    }


    @Override
    public boolean semesterHasModule(String moduleCode, SemesterName semesterName) {
        Semester semester = getSemester(semesterName);
        return semester.getModules().contains(moduleCode);
    }

    @Override
    public void addModule(String moduleCode, SemesterName semesterName) {
        this.getActiveStudyPlan().addModuleToSemester(new ModuleCode(moduleCode), semesterName);
    }

    @Override
    public void removeModule(String moduleCode, SemesterName semesterName) {
        this.getSemester(semesterName).removeModule(moduleCode);
    }

    @Override
    public Semester getSemester(SemesterName semesterName) {
        for (Semester current : modulePlanner.getActiveStudyPlan().getSemesters()) {
            if (current.getSemesterName() == semesterName) {
                return current;
            }
        }
        System.out.println("ERROR HERE");
        return null;
    }

    @Override
    public void setSemester(SemesterName semester) {
        // TODO: setCurrentSemester in StudyPlan class
        // this.modulePlanner.getActiveStudyPlan().setCurrentSemester(semester);
    }

    @Override
    public boolean semesterHasUE(SemesterName semesterName) {
        // TODO: UE implementation
        // getSemester(semesterName).getModules().
        return true;
    }

    public void renameUEInSemester(SemesterName semesterName, String moduleCode) {
        // TODO: UE implementation
    }

    public void blockSemester(SemesterName sem, String reason) {
        // TODO: blockSemester in StudyPlan class
        // this.modulePlanner.getActiveStudyPlan().blockSemester(semester);
    }

}
