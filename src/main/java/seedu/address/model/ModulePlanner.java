package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.Name;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.semester.Semester;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.studyplan.UniqueStudyPlanList;
import seedu.address.model.studyplan.exceptions.StudyPlanNotFoundException;
import seedu.address.model.versiontracking.VersionTrackingManager;

/**
 * Wraps all data at the module planner level
 * Duplicates are not allowed (by .isSameStudyPlan comparison)
 */
public class ModulePlanner implements ReadOnlyModulePlanner {

    private final UniqueStudyPlanList studyPlans;
    private StudyPlan activeStudyPlan;
    private final ModulesInfo modulesInfo;
    private final VersionTrackingManager versionTrackingManager;

    //    /*
    //     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
    //     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
    //     *
    //     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
    //     *   among constructors.
    //     */
    //    {
    //        studyPlans = new UniqueStudyPlanList();
    //    }

    public ModulePlanner() {
        studyPlans = new UniqueStudyPlanList();
        modulesInfo = new ModulesInfo();
        versionTrackingManager = new VersionTrackingManager();
    }

    public ModulePlanner(ModulesInfo modulesInfo) {
        studyPlans = new UniqueStudyPlanList();
        this.modulesInfo = modulesInfo;
        versionTrackingManager = new VersionTrackingManager();
    }

    /**
     * Creates an ModulePlanner using the studyPlans in the {@code toBeCopied}
     */
    public ModulePlanner(ReadOnlyModulePlanner toBeCopied, ModulesInfo modulesInfo) {
        studyPlans = new UniqueStudyPlanList();
        resetData(toBeCopied);
        this.modulesInfo = modulesInfo;
        versionTrackingManager = toBeCopied.getVersionTrackingManager();
    }

    /**
     * Creates an ModulePlanner from JSON. This is used in {@code JsonSerializableModulePlanner}.
     */
    public ModulePlanner(UniqueStudyPlanList uniqueStudyPlanList, /*StudyPlan activeStudyPlan,*/
                         ModulesInfo modulesInfo,
                         VersionTrackingManager versionTrackingManager) {
        this.studyPlans = uniqueStudyPlanList;
        // this.activeStudyPlan = activeStudyPlan;
        this.modulesInfo = modulesInfo;
        this.versionTrackingManager = versionTrackingManager;
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code studyPlans}.
     * {@code studyPlans} must not contain duplicate studyPlans.
     */
    public void setStudyPlans(List<StudyPlan> studyPlans) {
        this.studyPlans.setStudyPlans(studyPlans);
    }

    /**
     * Resets the existing data of this {@code ModulePlanner} with {@code newData}.
     */
    public void resetData(ReadOnlyModulePlanner newData) {
        requireNonNull(newData);

        setStudyPlans(newData.getStudyPlanList());
    }

    //// person-level operations

    /**
     * Returns true if a study plan with the same identity as {@code study plan} exists in the module planner.
     */
    public boolean hasStudyPlan(StudyPlan studyPlan) {
        requireNonNull(studyPlan);
        return studyPlans.contains(studyPlan);
    }

    /**
     * Adds a study plan to the module planner.
     * The study plan must not already exist in the module planner.
     */
    public void addStudyPlan(StudyPlan studyPlan) {
        studyPlans.add(studyPlan);
    }

    /**
     * Replaces the given study plan {@code target} in the list with {@code editedStudyPlan}.
     * {@code target} must exist in the module planner.
     * The person identity of {@code editedStudyPlan} must not be the same as another existing study plan
     * in the module planner.
     */
    public void setStudyPlan(StudyPlan target, StudyPlan editedStudyPlan) {
        requireNonNull(editedStudyPlan);

        studyPlans.setStudyPlan(target, editedStudyPlan);
    }

    /**
     * Returns the current active {@code StudyPlan}.
     */
    public StudyPlan getActiveStudyPlan() {
        return activeStudyPlan;
    }

    /**
     * Activates the study plan with the given index, and returns the active study plan populated with relevant
     * details.
     */
    public StudyPlan activateStudyPlan(int index) throws StudyPlanNotFoundException {
        Iterator<StudyPlan> iterator = studyPlans.iterator();
        while (iterator.hasNext()) {
            StudyPlan studyPlan = iterator.next();
            if (studyPlan.getIndex() == index) {
                activeStudyPlan = studyPlan;
            }
        }

        if (activeStudyPlan == null) {
            throw new StudyPlanNotFoundException();
        }

        // construct the mega list of modules
        HashMap<String, Module> megaModuleHash = activeStudyPlan.getModules();
        for (Module module : megaModuleHash.values()) {
            ModuleInfo moduleInfo = modulesInfo.find(module.getModuleCode().toString());
            module.setName(new Name(moduleInfo.getName()));
            module.setMcCount(moduleInfo.getMc());
        }

        // replace skeletal modules under semesters with the actual reference to modules in mega list
        Iterator<Semester> semesterIterator = activeStudyPlan.getSemesters().iterator();
        while (semesterIterator.hasNext()) {
            Semester semester = semesterIterator.next();
            UniqueModuleList uniqueModuleList = semester.getModules();
            Iterator<Module> moduleIterator = uniqueModuleList.iterator();
            while (moduleIterator.hasNext()) {
                Module skeletalModule = moduleIterator.next();
                Module actualModule = megaModuleHash.get(skeletalModule.getModuleCode().toString());
                uniqueModuleList.setModule(skeletalModule, actualModule);
            }
        }

        // TODO: get default tags from moduleInfo, and make the tags refer to the megalist of tags

        return activeStudyPlan;
    }

    /**
     * Removes {@code key} from this {@code ModulePlanner}.
     * {@code key} must exist in the module planner.
     */
    public void removeStudyPlan(StudyPlan key) {
        studyPlans.remove(key);
    }

    /**
     * Returns the version tracking manager of the current module planner.
     */
    public VersionTrackingManager getVersionTrackingManager() {
        return versionTrackingManager;
    }

    //// util methods

    @Override
    public String toString() {
        return studyPlans.asUnmodifiableObservableList().size() + " studyPlans";
        // TODO: refine later
    }

    @Override
    public ObservableList<StudyPlan> getStudyPlanList() {
        return studyPlans.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModulePlanner // instanceof handles nulls
                && studyPlans.equals(((ModulePlanner) other).studyPlans));
    }

    @Override
    public int hashCode() {
        return studyPlans.hashCode();
    }
}
