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
    public static final AcadYear DEFAULT_ACAD_YEAR = new AcadYear("2019/2020");
    public static final SemesterNo DEFAULT_SEMESTER_NO = new SemesterNo("1");

    private String acadYear;
    private String semesterNo;

    public AppSettings() {
        acadYear = DEFAULT_ACAD_YEAR.toString();
        semesterNo = DEFAULT_SEMESTER_NO.toString();
    }

    public AppSettings(String acadYear, String semesterNo) {
        this.acadYear = acadYear;
        this.semesterNo = semesterNo;
    }

    public AcadYear getAcadYear() {
        return new AcadYear(acadYear);
    }

    public SemesterNo getSemesterNo() {
        return new SemesterNo(semesterNo);
    }

    public void setAcadYear(String acadYear) {
        this.acadYear = acadYear;
    }

    public void setSemesterNo(String semesterNo) {
        this.semesterNo = semesterNo;
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

        return acadYear.equals(o.getAcadYear().toString())
                && semesterNo.equals(o.getSemesterNo().toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(acadYear, semesterNo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Academic Year : " + acadYear + "\n");
        sb.append("Academic Semester : " + semesterNo + "\n");
        return sb.toString();
    }
}
