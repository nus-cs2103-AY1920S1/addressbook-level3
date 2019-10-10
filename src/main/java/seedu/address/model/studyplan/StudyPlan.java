package seedu.address.model.studyplan;

import java.util.HashMap;
import java.util.List;

import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;
import seedu.address.model.semester.UniqueSemesterList;
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
    public StudyPlan() {
        this.semesters = new UniqueSemesterList();
        // switch the current active plan to the newly created one. Reason: user can directly add modules to it.
        this.isActive = true;

        // TODO: initialise modules and (default) tags. this should be done when module info is ready.
        //  get the list from Module?
        modules = new HashMap<>(); //TODO need to change this.
        tags = new UniqueTagList();

        totalNumberOfStudyPlans++;
        this.index = totalNumberOfStudyPlans;
    }

    // to create a study plan with a Title
    public StudyPlan(Title title) {
        this.title = title;
        this.semesters = new UniqueSemesterList();
        // switch the current active plan to the newly created one. Reason: user can directly add modules to it.
        this.isActive = true;

        // TODO: initialise modules and (default) tags. this should be done when module info is ready.
        //  get the list from Module?
        modules = new HashMap<>(); //TODO need to change this.
        tags = new UniqueTagList();

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
            return this.index == ((StudyPlan) other).index &&
                    this.semesters.equals(((StudyPlan) other).getSemesters());
        } else {
            return false;
        }
    }
}
