package seedu.address.model.module;

/**
 * Semester number of the Semester.
 */
public class SemesterNo {
    private String semesterNo;

    public SemesterNo(String semesterNo) {
        this.semesterNo = semesterNo;
    }

    @Override
    public String toString() {
        return semesterNo;
    }

    /**
     * Checks if this SemesterNo is equal to other SemesterNo.
     * @param other to be compared
     * @return boolean
     */
    public boolean equals(SemesterNo other) {
        if (other == null) {
            return false;
        } else if (other.toString().equals(this.semesterNo)) {
            return true;
        } else {
            return false;
        }
    }
}
