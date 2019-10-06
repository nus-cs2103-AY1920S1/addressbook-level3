package seedu.address.model.semester;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents a list of semesters.
 */
public class SemesterList implements Cloneable {
    private List<Semester> semesters;

    public SemesterList() {
        semesters = new ArrayList<>();
    }

    public SemesterList(List<Semester> semesters) {
        this.semesters = semesters;
    }

    public void setSemester(SemesterName semesterName, Semester semester) {
        // TODO: implement
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    // this may not be needed anymore, depending on the implementation of JsonAdaptedStudyPlan
    public static SemesterList toSemesterList(Set<Semester> semesters) {
        // TODO: make a deep copy?
        ArrayList<Semester> semesterArrayList = new ArrayList<>();
        semesterArrayList.addAll(semesters);
        return new SemesterList(semesterArrayList);
    }

    public SemesterList clone() throws CloneNotSupportedException {
        return (SemesterList) super.clone();
    }
}
