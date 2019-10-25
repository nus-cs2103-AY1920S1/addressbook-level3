package seedu.address.model.semester;

import seedu.address.model.semester.exceptions.SemesterNotFoundException;

/**
 * Represents the possible names of the semesters from Year 1 to 5.
 */
public enum SemesterName {

    Y1S1, Y1S2, Y1ST1, Y1ST2,
    Y2S1, Y2S2, Y2ST1, Y2ST2,
    Y3S1, Y3S2, Y3ST1, Y3ST2,
    Y4S1, Y4S2, Y4ST1, Y4ST2,
    Y5S1, Y5S2, Y5ST1, Y5ST2;

    public static final String VALIDATION_REGEX = "Y[1-5]ST?[1-2]";

    public static SemesterName getEnum(int year, int semester) {
        switch (year) {
        case 1:
            switch (semester) {
            case 1:
                return Y1S1;
            case 2:
                return Y1S2;
            default:
                throw new SemesterNotFoundException();
            }
        case 2:
            switch (semester) {
            case 1:
                return Y2S1;
            case 2:
                return Y2S2;
            default:
                throw new SemesterNotFoundException();
            }
        case 3:
            switch (semester) {
            case 1:
                return Y3S1;
            case 2:
                return Y3S2;
            default:
                throw new SemesterNotFoundException();
            }
        case 4:
            switch (semester) {
            case 1:
                return Y4S1;
            case 2:
                return Y4S2;
            default:
                throw new SemesterNotFoundException();
            }
        case 5:
            switch (semester) {
            case 1:
                return Y5S1;
            case 2:
                return Y5S2;
            default:
                throw new SemesterNotFoundException();
            }
        default:
            throw new SemesterNotFoundException();
        }
    }

    public static SemesterName getSpecialTermEnum(int year, int semester) {
        switch (year) {
        case 1:
            switch (semester) {
            case 1:
                return Y1ST1;
            case 2:
                return Y1ST2;
            default:
                throw new SemesterNotFoundException();
            }
        case 2:
            switch (semester) {
            case 1:
                return Y2ST1;
            case 2:
                return Y2ST2;
            default:
                throw new SemesterNotFoundException();
            }
        case 3:
            switch (semester) {
            case 1:
                return Y3ST1;
            case 2:
                return Y3ST2;
            default:
                throw new SemesterNotFoundException();
            }
        case 4:
            switch (semester) {
            case 1:
                return Y4ST1;
            case 2:
                return Y4ST2;
            default:
                throw new SemesterNotFoundException();
            }
        case 5:
            switch (semester) {
            case 1:
                return Y5ST1;
            case 2:
                return Y5ST2;
            default:
                throw new SemesterNotFoundException();
            }
        default:
            throw new SemesterNotFoundException();
        }
    }

    public static boolean isValidSemesterName(String test) {
        return test.toUpperCase().matches(VALIDATION_REGEX);
    }
}
