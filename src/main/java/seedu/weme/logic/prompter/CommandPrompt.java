package seedu.weme.logic.prompter;

/**
 * A Wrapper class for command prompt.
 */
public class CommandPrompt {
    private final String commandPrompt;

    public CommandPrompt(String commandPrompt) {
        this.commandPrompt = commandPrompt;
    }

    public static CommandPrompt empty() {
        return new CommandPrompt("");
    }

    @Override
    public String toString() {
        return commandPrompt;
    }
}
