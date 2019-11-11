package seedu.address.model.appsettings;


/**
 * Difficulty levels for the Game
 */
public enum DifficultyEnum {
    EASY(15000), //15 secs for EASY
    MEDIUM(10000), //10 secs for MEDIUM
    HARD(5000); //5 secs for HARD

    /** Time allowed per question in milliseconds. */
    private long timeAllowedPerQuestion;

    DifficultyEnum(long timeAllowedPerQuestion) {
        this.timeAllowedPerQuestion = timeAllowedPerQuestion;
    }

    public long getTimeAllowedPerQuestion() {
        return timeAllowedPerQuestion;
    }

}
