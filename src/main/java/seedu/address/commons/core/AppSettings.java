package seedu.address.commons.core;

import java.io.Serializable;
import java.util.Objects;

import seedu.address.model.module.AcadYear;
import seedu.address.model.module.SemesterNo;

/**
 * A Serializable class that contains the App settings.
 */
public class AppSettings implements Serializable {

    // Has to be updated from time to time
    private static final AcadYear DEFAULT_ACAD_YEAR = new AcadYear("2019/2020");
    private static final SemesterNo DEFAULT_ACAD_SEMESTER = new SemesterNo("1");

    private String acadYear;
    private String acadSemester;

    public AppSettings() {
        acadYear = DEFAULT_ACAD_YEAR.toString();
        acadSemester = DEFAULT_ACAD_SEMESTER.toString();
    }

    public AppSettings(String acadYear, String acadSemester) {
        this.acadYear = acadYear;
        this.acadSemester = acadSemester;
    }

    public AcadYear getAcadYear() {
        return new AcadYear(acadYear);
    }

    public SemesterNo getAcadSemester() {
        return new SemesterNo(acadSemester);
    }

    public void setAcadYear(String acadYear) {
        this.acadYear = acadYear;
    }

    public void setAcadSemester(String acadSemester) {
        this.acadSemester = acadSemester;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AppSettings)) { //this handles null as well.
            return false;
        }

        AppSettings o = (AppSettings) other;

        return acadYear.equals(o.getAcadYear())
                && acadSemester.equals(o.getAcadSemester());
    }

    @Override
    public int hashCode() {
        return Objects.hash(acadYear, acadSemester);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Academic Year : " + acadYear + "\n");
        sb.append("Academic Semester : " + acadSemester);
        return sb.toString();
    }
}
