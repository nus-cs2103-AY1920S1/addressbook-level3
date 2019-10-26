package seedu.address.model.appsettings;


/**
 * Difficulty levels for the Game
 */
public enum DifficultyEnum {
    EASY(15000),
    MEDIUM(10000),
    HARD(5000);

    private long timeAllowedPerQuestion;

    DifficultyEnum(long timeAllowedPerQuestion) {
        this.timeAllowedPerQuestion = timeAllowedPerQuestion;
    }

    public long getTimeAllowedPerQuestion() {
        return timeAllowedPerQuestion;
    }

}
