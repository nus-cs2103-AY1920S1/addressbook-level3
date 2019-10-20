package seedu.address.logic.commands;

import seedu.address.model.Model;

public class UnknownCommand extends Command {

    public static final String SHOWING_UNKNOWN_MESSAGE = ": We don't know what that means. " +
            "Help us understand so we can add this to our command library for your personal usage. " +
            "Please enter what you really meant or type \"cancel\" if it was a genuine mistake.";
    private final String unknown;

    public UnknownCommand(String unknown) {
        this.unknown = unknown;
    }

    @Override
    public CommandResult execute(Model model) {
        model.saveCommand(this.unknown);
        return new CommandResult(this.unknown + SHOWING_UNKNOWN_MESSAGE,
                false, false, false, true);
    }
}
