package seedu.address.model.appsettings;


/**
 * Difficulty levels for the Game
 */
public enum DifficultyEnum {
    EASY(8000),
    MEDIUM(6000),
    HARD(3000); // Hints are not allowed for HARD Mode

    private long timeAllowedPerQuestion;

    DifficultyEnum(long timeAllowedPerQuestion) {
        this.timeAllowedPerQuestion = timeAllowedPerQuestion;
    }

    public long getTimeAllowedPerQuestion() {
        return timeAllowedPerQuestion;
    }

}
