package seedu.address.model.studyplan;

import java.util.Set;

import seedu.address.model.semester.Semester;
import seedu.address.model.semester.SemesterList;

/**
 * Represents a study plan in the module planner.
 */
public class StudyPlan implements Cloneable {

    private static int totalNumberOfStudyPlans = 0; // TODO: this will reset to zero every time application restarts

    private SemesterList semesterList;
    private Title title;
    private boolean isActive;
    private int index; // unique identifier of this study plan

    // to create a study plan without a Title
    public StudyPlan() {
        this.semesterList = new SemesterList();
        // switch the current active plan to the newly created one. Reason: user can directly add modules to it.
        this.isActive = true;

        totalNumberOfStudyPlans++;
        this.index = totalNumberOfStudyPlans;
    }

    // to create a study plan with a Title
    public StudyPlan(Title title) {
        this.title = title;
        this.semesterList = new SemesterList();
        // switch the current active plan to the newly created one. Reason: user can directly add modules to it.
        this.isActive = true;

        totalNumberOfStudyPlans++;
        this.index = totalNumberOfStudyPlans;
    }

    // TODO: furnish this constructor. This is created so that JsonAdaptedStudyPlan works
    public StudyPlan(Title modelTitle, int modelIndex, boolean modelIsActive, Set<Semester> modelSemesters) {
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

    public SemesterList getSemesterList() {
        return semesterList;
    }

    public int getIndex() {
        return index;
    }

    /**
     * Returns true if both study plans of the same index have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two study plans.
     */
    public boolean isSameStudyPlan(StudyPlan other) {
        return this.index == other.index;
    }

    // TODO: implement/override equals

    /**
     * Returns a copy of the current study plan.
     *
     * @return a clone of this study plan.
     * @throws CloneNotSupportedException
     */
    public StudyPlan clone() throws CloneNotSupportedException {
        StudyPlan clone = (StudyPlan) super.clone();
        clone.semesterList = semesterList.clone();
        clone.title = title.clone();
        return clone;
    }
}
