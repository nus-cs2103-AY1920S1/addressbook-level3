package seedu.address.logic.commands.switches;

import seedu.address.logic.commands.CommandResult;

/**
 * Represents the command result returned by {@code SwitchToStartCommand}.
 * Used mainly to pass the info of the title to the GameManager.
 */
public class StartCommandResult extends CommandResult {

    public static final String MESSAGE_GAME_START_SUCCESS = "Game session in progress for: ";
    public static final String FIRST_QUESTION_MESSAGE = "guess the keyword! ";

    private String title;

    public StartCommandResult(String title, String firstQuestion) {
        super(MESSAGE_GAME_START_SUCCESS + title + ", " + FIRST_QUESTION_MESSAGE
                        + "\n"
                        + firstQuestion,
                true);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isStartCommandResult() {
        return true;
    }
}
