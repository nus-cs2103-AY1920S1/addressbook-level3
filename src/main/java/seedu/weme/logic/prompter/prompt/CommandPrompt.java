package seedu.weme.logic.prompter.prompt;

/**
 * A Wrapper class for command prompt and complete command for auto-completion.
 */
public class CommandPrompt {
    private final String commandPrompt;
    private final String completeCommand;

    public CommandPrompt(String completeCommand) {
        this.commandPrompt = "";
        this.completeCommand = completeCommand;
    }

    public CommandPrompt(String commandPrompt, String completeCommand) {
        this.commandPrompt = commandPrompt;
        this.completeCommand = completeCommand;
    }

    public static CommandPrompt empty() {
        return new CommandPrompt("", "");
    }

    public String getCommandPrompt() {
        return commandPrompt;
    }

    public String getCompleteCommand() {
        return completeCommand;
    }

    @Override
    public String toString() {
        return String.format("Command prompt: %s\nComplete command: %s\n", commandPrompt, completeCommand);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandPrompt)) {
            return false;
        }

        // state check
        CommandPrompt prompt = (CommandPrompt) other;
        return commandPrompt.equals(prompt.getCommandPrompt())
                && completeCommand.equals((prompt.getCompleteCommand()));
    }
}
