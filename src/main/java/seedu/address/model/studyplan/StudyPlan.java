package seedu.address.model.studyplan;

import seedu.address.model.semester.SemesterList;

public class StudyPlan implements Cloneable{
    private SemesterList semesterList;
    private Title title;
    private boolean isActive;
    private int index; // unique identifier of this study plan

    private static int totalNumberOfStudyPlans = 0;

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

    public StudyPlan clone() throws CloneNotSupportedException {
        StudyPlan clone = (StudyPlan) super.clone();
        clone.semesterList = semesterList.clone();
        clone.title = title.clone();
        return clone;
    }
}
