package seedu.address.model.semester;

/**
 * Represents the possible names of the semesters from Year 1 to 5.
 */
public enum SemesterName implements Comparable<SemesterName> {
    Y1S1(1), Y1ST1(2), Y1ST2(3), Y1S2(4),
    Y2S1(5), Y2ST1(6), Y2ST2(7), Y2S2(8),
    Y3S1(9), Y3ST1(10), Y3ST2(11), Y3S2(12),
    Y4S1(13), Y4ST1(14), Y4ST2(15), Y4S2(16),
    Y5S1(17), Y5ST1(18), Y5ST2(19), Y5S2(20);

    private int index;

    private SemesterName(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
