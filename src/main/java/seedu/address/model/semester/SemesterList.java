package seedu.address.model.semester;

import java.util.ArrayList;
import java.util.Set;

/**
 * Represents a list of semesters.
 */
public class SemesterList implements Cloneable {
    // private Semester[] semesters;
    private ArrayList<Semester> semesters;

    // generate a new SemesterList with all the default 8 semesters
    public SemesterList() {
    //        semesters = new Semester[8];
    //        semesters[0] = new Semester(SemesterName.Y1S1);
    //        semesters[1] = new Semester(SemesterName.Y1S2);
    //        semesters[2] = new Semester(SemesterName.Y2S1);
    //        semesters[3] = new Semester(SemesterName.Y2S2);
    //        semesters[4] = new Semester(SemesterName.Y3S1);
    //        semesters[5] = new Semester(SemesterName.Y3S2);
    //        semesters[6] = new Semester(SemesterName.Y4S1);
    //        semesters[7] = new Semester(SemesterName.Y4S2);
        semesters = new ArrayList<>();
    }

    private SemesterList(ArrayList<Semester> semesters) {
        this.semesters = semesters;
    }

    public void setSemester(SemesterName semesterName, Semester semester) {
        // TODO: implement
    }

    //    public Semester[] getSemesters() {
    //        return semesters;
    //    }

    public ArrayList<Semester> getSemesters() {
        return semesters;
    }

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
