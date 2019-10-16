package seedu.address.model.gamedifficulty;

/**
 * Difficulty levels for the Game
 */
public enum DifficultyEnum {
    EASY(6000), MEDIUM(4000), HARD(2000);

    private long timeAllowedPerQuestion;

    private DifficultyEnum(long timeAllowedPerQuestion) {
        this.timeAllowedPerQuestion = timeAllowedPerQuestion;
    }

    public long getTimeAllowedPerQuestion() {
        return timeAllowedPerQuestion;
    }
}
