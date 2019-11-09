package seedu.system.logic.commands;

/**
 * Represents different ranking methods of participants in a competition.
 * Guarantees: immutable
 */
public enum RankMethod {
    BENCH("bench"),
    DEADLIFT("deadlift"),
    SQUAT("squat"),
    OVERALL("overall");

    public static final String MESSAGE_CONSTRAINTS =
        "Rank method should be provided as either bench, deadlift, squat or overall";

    private String name;

    private RankMethod(String name) {
        this.name = name;
    }

    /**
     * Checks {@code gender} corresponds to a  format.
     */
    public static boolean isValidRankMethod(String rankMethod) {
        return rankMethod.toLowerCase().equals(BENCH.toString())
            || rankMethod.toLowerCase().equals(SQUAT.toString())
            || rankMethod.toLowerCase().equals(DEADLIFT.toString())
            || rankMethod.toLowerCase().equals(OVERALL.toString());
    }

    public static RankMethod getRankMethodCorrespondingToName(String name) {
        if (name.equals((BENCH.toString()))) {
            return BENCH;
        } else if (name.equals((SQUAT.toString()))) {
            return SQUAT;
        } else if (name.equals((DEADLIFT.toString()))) {
            return DEADLIFT;
        } else if (name.equals((OVERALL.toString()))) {
            return OVERALL;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
