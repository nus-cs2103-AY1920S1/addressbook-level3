package seedu.address.model.semester;

/**
 * Represents a list of semesters.
 */
public class SemesterList implements Cloneable {
    private Semester[] semesters;

    // generate a new SemesterList with all the default 8 semesters
    public SemesterList() {
        semesters = new Semester[8];
        semesters[0] = new Semester(SemesterName.Y1S1);
        semesters[1] = new Semester(SemesterName.Y1S2);
        semesters[2] = new Semester(SemesterName.Y2S1);
        semesters[3] = new Semester(SemesterName.Y2S2);
        semesters[4] = new Semester(SemesterName.Y3S1);
        semesters[5] = new Semester(SemesterName.Y3S2);
        semesters[6] = new Semester(SemesterName.Y4S1);
        semesters[7] = new Semester(SemesterName.Y4S2);
    }

    public void setSemester(SemesterName semesterName, Semester semester) {
        // TODO: implement
    }

    public Semester[] getSemesters() {
        return semesters;
    }

    public SemesterList clone() throws CloneNotSupportedException {
        return (SemesterList) super.clone();
    }
}
