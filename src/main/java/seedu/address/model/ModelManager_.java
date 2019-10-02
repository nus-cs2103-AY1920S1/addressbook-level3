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
import seedu.address.model.studyplan.StudyPlan;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager_ implements Model_ {
    private static final Logger logger = LogsCenter.getLogger(ModelManager_.class);

    private final ModulePlanner ModulePlanner;
    private final UserPrefs userPrefs;
    private final FilteredList<StudyPlan> filteredStudyPlans;

    /**
     * Initializes a ModelManager with the given ModulePlanner and userPrefs.
     */
    public ModelManager_(ReadOnlyModulePlanner ModulePlanner, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(ModulePlanner, userPrefs);

        logger.fine("Initializing with address book: " + ModulePlanner + " and user prefs " + userPrefs);

        this.ModulePlanner = new ModulePlanner(ModulePlanner);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudyPlans = new FilteredList<>(this.ModulePlanner.getStudyPlanList());
    }

    public ModelManager_() {
        this(new ModulePlanner(), new UserPrefs());
    }

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
    public void setModulePlannerFilePath(Path ModulePlannerFilePath) {
        requireNonNull(ModulePlannerFilePath);
        userPrefs.setModulePlannerFilePath(ModulePlannerFilePath);
    }

    //=========== ModulePlanner ================================================================================

    @Override
    public void setModulePlanner(ReadOnlyModulePlanner ModulePlanner) {
        this.ModulePlanner.resetData(ModulePlanner);
    }

    @Override
    public ReadOnlyModulePlanner getModulePlanner() {
        return ModulePlanner;
    }

    @Override
    public boolean hasStudyPlan(StudyPlan StudyPlan) {
        requireNonNull(StudyPlan);
        return ModulePlanner.hasStudyPlan(StudyPlan);
    }

    @Override
    public void deleteStudyPlan(StudyPlan target) {
        ModulePlanner.removeStudyPlan(target);
    }

    @Override
    public void addStudyPlan(StudyPlan StudyPlan) {
        ModulePlanner.addStudyPlan(StudyPlan);
        updateFilteredStudyPlanList(PREDICATE_SHOW_ALL_STUDY_PLANS);
    }

    @Override
    public void setStudyPlan(StudyPlan target, StudyPlan editedStudyPlan) {
        requireAllNonNull(target, editedStudyPlan);

        ModulePlanner.setStudyPlan(target, editedStudyPlan);
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
        if (!(obj instanceof ModelManager_)) {
            return false;
        }

        // state check
        ModelManager_ other = (ModelManager_) obj;
        return ModulePlanner.equals(other.ModulePlanner)
                && userPrefs.equals(other.userPrefs)
                && filteredStudyPlans.equals(other.filteredStudyPlans);
    }

}