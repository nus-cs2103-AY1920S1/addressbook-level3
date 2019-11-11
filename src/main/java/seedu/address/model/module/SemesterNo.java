package seedu.address.model.module;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.module.exceptions.SemesterNoNotFoundException;

/**
 * Semester number of the Semester.
 */
public enum SemesterNo {
    SEMESTER_1("1", "sem-1"),
    SEMESTER_2("2", "sem-2"),
    SPECIAL_TERM_I("3", "st-i"),
    SPECIAL_TERM_II("4", "st-ii");

    private final String semesterNo;
    private final String shortSemesterName;

    SemesterNo(String semesterNo, String shortSemesterName) {
        this.semesterNo = semesterNo;
        this.shortSemesterName = shortSemesterName;
    }

    public String semesterNo() {
        return semesterNo;
    }

    public String shortSemesterName() {
        return shortSemesterName;
    }

    /**
     * Find the SemesterNo whose semesterNo or shortSemesterName matches the input string.
     * @param stringToMatch input string to match.
     * @return SemesterNo matched.
     */
    public static SemesterNo findSemesterNo(String stringToMatch) throws SemesterNoNotFoundException {
        for (SemesterNo sNo : SemesterNo.values()) {
            if (sNo.shortSemesterName.equals(stringToMatch) || sNo.semesterNo.equals(stringToMatch)) {
                return sNo;
            }
        }
        throw new SemesterNoNotFoundException();
    }

    /**
     * Get all short semester names.
     */
    public static Set<String> getShortSemesterNames() {
        Set<String> shortSemesterNames = new HashSet<>();
        for (SemesterNo sNo : SemesterNo.values()) {
            shortSemesterNames.add(sNo.shortSemesterName);
        }
        return shortSemesterNames;
    }
}
