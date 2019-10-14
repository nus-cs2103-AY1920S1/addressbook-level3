package seedu.address.model.semester;

/**
 * Represents the possible names of the semesters from Year 1 to 5.
 */
public enum SemesterName {
    Y1S1, Y1S2, Y2S1, Y2S2, Y3S1, Y3S2, Y4S1, Y4S2, Y5S1, Y5S2,
    Y1ST1, Y1ST2, Y2ST1, Y2ST2, Y3ST1, Y3ST2, Y4ST1, Y4ST2, Y5ST1, Y5ST2;

    public static SemesterName getEnum(int year, int semester) {
        switch (year) {
            case 1:
                switch (semester) {
                    case 1: return Y1S1;
                    case 2: return Y1S2;
                }
            case 2:
                switch (semester) {
                    case 1: return Y2S1;
                    case 2: return Y2S2;
                }
            case 3:
                switch (semester) {
                    case 1: return Y3S1;
                    case 2: return Y3S2;
                }
            case 4:
                switch (semester) {
                    case 1: return Y4S1;
                    case 2: return Y4S2;
                }
            case 5:
                switch (semester) {
                    case 1: return Y5S1;
                    case 2: return Y5S2;
                }
            default:
                // TODO: Throw an exception here
                return Y5S1;
        }
    }

    public static SemesterName getSpecialTermEnum(int year, int semester) {
        switch (year) {
            case 1:
                switch (semester) {
                    case 1: return Y1ST1;
                    case 2: return Y1ST2;
                }
            case 2:
                switch (semester) {
                    case 1: return Y2ST1;
                    case 2: return Y2ST2;
                }
            case 3:
                switch (semester) {
                    case 1: return Y3ST1;
                    case 2: return Y3ST2;
                }
            case 4:
                switch (semester) {
                    case 1: return Y4ST1;
                    case 2: return Y4ST2;
                }
            case 5:
                switch (semester) {
                    case 1: return Y5ST1;
                    case 2: return Y5ST2;
                }
            default:
                // TODO: Throw an exception here
                return Y5ST1;
        }
    }
}
