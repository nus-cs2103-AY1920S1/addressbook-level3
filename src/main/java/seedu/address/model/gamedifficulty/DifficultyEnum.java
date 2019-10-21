package seedu.address.model.gamedifficulty;

/**
 * Difficulty levels for the Game
 */
public enum DifficultyEnum {
    EASY(8000, true),
    MEDIUM(6000, true),
    HARD(3000, false); // Hints are not allowed for HARD Mode

    private long timeAllowedPerQuestion;
    private boolean hintsEnabled;

    DifficultyEnum(long timeAllowedPerQuestion, boolean hintsEnabled) {
        this.timeAllowedPerQuestion = timeAllowedPerQuestion;
        this.hintsEnabled = hintsEnabled;
    }

    public long getTimeAllowedPerQuestion() {
        return timeAllowedPerQuestion;
    }

    public boolean hintsAreEnabled() {
        return hintsEnabled;
    }
}
