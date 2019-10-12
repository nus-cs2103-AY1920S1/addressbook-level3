package seedu.address.model.studyplan;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import seedu.address.model.Color;
import seedu.address.model.ModuleInfo;
import seedu.address.model.ModulesInfo;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.Name;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterName;
import seedu.address.model.semester.UniqueSemesterList;
import seedu.address.model.semester.exceptions.SemesterNotFoundException;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents a study plan in the module planner.
 */
public class StudyPlan implements Cloneable {

    private static int totalNumberOfStudyPlans = 0; // TODO: will this reset to zero every time application restarts?

    private UniqueSemesterList semesters;
    private Title title;
    private boolean isActive;
    private int index; // unique identifier of this study plan

    // the "Mega-List" of modules of this study plan. All modules in an *active* study plan refer to a module here.
    // note: this Mega-List is only constructed when a study plan gets activated.
    private HashMap<String, Module> modules;

    // the unique list of tags of this study plan.
    // All tags in an *active* study plan refer to a tag here.
    // note: this unique list of tags is only constructed when a study plan gets activated.
    private UniqueTagList tags;


    // to create a study plan without a Title
    public StudyPlan(ModulesInfo modulesInfo) {
        this(new Title(""), modulesInfo);
    }

    // to create a study plan with a Title
    public StudyPlan(Title title, ModulesInfo modulesInfo) {
        this.title = title;
        this.semesters = new UniqueSemesterList();
        setDefaultSemesters();

        // switch the current active plan to the newly created one. Reason: user can directly add modules to it.
        this.isActive = true;

        // add all default tags
        // TODO: initialise it
        tags = new UniqueTagList();

        setMegaModuleHashMap(modulesInfo);

        totalNumberOfStudyPlans++;
        this.index = totalNumberOfStudyPlans;
    }


    /**
     * This constructor is used for {@code JsonAdaptedStudyPlan}.
     */
    public StudyPlan(Title modelTitle, int modelIndex, boolean modelIsActive, List<Semester> modelSemesters,
                     HashMap<String, Module> modelModules, UniqueTagList modelTags) {
        title = modelTitle;
        index = modelIndex;
        isActive = modelIsActive;
        semesters = new UniqueSemesterList();
        semesters.setSemesters(modelSemesters);

        modules = modelModules;
        tags = modelTags;
    }

    // make a copy of the current study without incrementing the index, for version tracking commits
    public StudyPlan copyForCommit() throws CloneNotSupportedException {
        return this.clone();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    public Title getTitle() {
        return title;
    }

    public UniqueSemesterList getSemesters() {
        return semesters;
    }

    public int getIndex() {
        return index;
    }

    // "Mega-list" of modules
    public HashMap<String, Module> getModules() {
        return modules;
    }

    // "Mega-list" of tags
    public UniqueTagList getTags() {
        return tags;
    }

    /**
     * Populates the unique semester list with the 8 semesters in the normal 4-year candidature. These
     * semesters will be empty initially.
     */
    public void setDefaultSemesters() {
        semesters.add(new Semester(SemesterName.Y1S1));
        semesters.add(new Semester(SemesterName.Y1S2));
        semesters.add(new Semester(SemesterName.Y2S1));
        semesters.add(new Semester(SemesterName.Y2S2));
        semesters.add(new Semester(SemesterName.Y3S1));
        semesters.add(new Semester(SemesterName.Y3S2));
        semesters.add(new Semester(SemesterName.Y4S1));
        semesters.add(new Semester(SemesterName.Y4S2));
    }

    /**
     * Given a {@code ModuleInfo} object, convert it to a {@code Module}.
     */
    private Module convertModuleInfoToModule(ModuleInfo moduleInfo) {
        // TODO: Yi Wai: assign default tags to the result (Module).

        Name name = new Name(moduleInfo.getName());
        ModuleCode moduleCode = new ModuleCode(moduleInfo.getCode());
        int mcCount = moduleInfo.getMc();
        return new Module(name, moduleCode, mcCount, Color.RED, new UniqueTagList());
    }

    private void setMegaModuleHashMap(ModulesInfo modulesInfo) {
        HashMap<String, ModuleInfo> moduleInfoHashMap = modulesInfo.getModuleInfoHashMap();
        modules = new HashMap<>();
        for (ModuleInfo moduleInfo : moduleInfoHashMap.values()) {
            Module module = convertModuleInfoToModule(moduleInfo);
            modules.put(module.getModuleCode().toString(), module);
        }
    }

    /**
     * Adds a module to a semester, given the {@code ModuleCode} and {@code SemesterName}.
     *
     * @param moduleCode module code of the module to be added.
     * @param semesterName semester name of the target semester.
     * @throws SemesterNotFoundException
     */
    public void addModuleToSemester(ModuleCode moduleCode, SemesterName semesterName)
            throws SemesterNotFoundException {
        Semester targetSemester = null;
        Iterator<Semester> semesterIterator = semesters.iterator();
        while (semesterIterator.hasNext()) {
            Semester semester = semesterIterator.next();
            if (semester.getSemesterName().equals(semesterName)) {
                targetSemester = semester;
            }
        }
        if (targetSemester == null) {
            throw new SemesterNotFoundException();
        }

        Module moduleToAdd = modules.get(moduleCode.toString());

        targetSemester.addModule(moduleToAdd);
    }

    /**
     * Sets the current semester. The user cannot change any module before the current semester. But they can
     * still change those in the current semester and after the current semester.
     */
    public void setCurrentSemester() {
        // TODO: implement this
    }

    /**
     * Blocks a semester with the given {@code SemesterName} so that the user cannot add modules to that semester.
     * The user can enter a reason for blocking it (e.g. NOC, internship).
     */
    public void blockSemester(SemesterName semesterName, String reasonForBlock) {
        // TODO: implement this

    }

    /**
     * Returns true if both study plans of the same index have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two study plans.
     */
    public boolean isSameStudyPlan(StudyPlan other) {
        return this.index == other.index;
    }

    /**
     * Returns a copy of the current study plan.
     *
     * @return a clone of this study plan.
     * @throws CloneNotSupportedException
     */
    public StudyPlan clone() throws CloneNotSupportedException {
        StudyPlan clone = (StudyPlan) super.clone();
        clone.semesters = semesters.clone();
        clone.title = title.clone();
        clone.isActive = isActive;
        clone.index = index;

        // because of this, the mega-lists fields don't have final keyword
        clone.modules = new HashMap<>();
        for (Module module : modules.values()) {
            clone.modules.put(module.getModuleCode().toString(), module.clone());
        }

        clone.tags = (UniqueTagList) tags.clone();

        return clone;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof StudyPlan) {
            return this.index == ((StudyPlan) other).index
                    && this.semesters.equals(((StudyPlan) other).getSemesters());
        } else {
            return false;
        }
    }
}
