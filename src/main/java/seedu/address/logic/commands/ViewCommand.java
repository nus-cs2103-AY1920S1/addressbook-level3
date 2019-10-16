package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.feature.Feature;

/**
 * Allows user to view calendar, attendance or performance.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Feature: "
            + "calendar / attendance / performance\n"
            + "Example: " + COMMAND_WORD + " "
            + "calendar\n"
            + COMMAND_WORD + " "
            + "attendance\n";

    public static final String MESSAGE_SUCCESS_1 = "Viewing your calendar";
    public static final String MESSAGE_SUCCESS_2 = "Viewing your team's attendance";
    public static final String MESSAGE_SUCCESS_3 = "Viewing your team's performance";

    public static final String MESSAGE_INVALID_FEATURE = "You have provided an invalid feature.";
    private final Feature feature;

    public ViewCommand(Feature feature) {
        this.feature = feature;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        switch (feature.toString()) {
        case "calendar":
            return new CommandResult(MESSAGE_SUCCESS_1, feature);
        case "attendance":
            return new CommandResult(MESSAGE_SUCCESS_2, feature);
        case "performance":
            return new CommandResult(MESSAGE_SUCCESS_3, feature);
        default:
            throw new CommandException(MESSAGE_INVALID_FEATURE);
        }
    }
}
