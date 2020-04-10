package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.performance.Event;
import seedu.address.ui.feature.Feature;

/**
 * Allows user to view calendar, attendance or performance.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";
    public static final String MESSAGE_USAGE = "Usage: 1) " + COMMAND_WORD + " calendar 2) "
            + COMMAND_WORD + " attendance 3) " + COMMAND_WORD + " performance 4)" + COMMAND_WORD
            + "records EVENT_NAME\n" + "Example: " + COMMAND_WORD + " calendar\n"
            + COMMAND_WORD + " records freestyle 50m";
    public static final String MESSAGE_SUCCESS_CALENDAR = "Viewing your calendar";
    public static final String MESSAGE_SUCCESS_ATTENDANCE = "Viewing your team's attendance";
    public static final String MESSAGE_SUCCESS_PERFORMANCE = "Here are your events stored Athletick.\n"
        + "To view the performance for an event, type in the command: records EVENT_NAME";
    public static final String MESSAGE_SUCCESS_RECORDS = "Here are the records for %1$s event.\n";
    public static final String MESSAGE_INVALID_FEATURE = "You have provided an invalid feature.";
    private final Feature feature;

    public ViewCommand(Feature feature) {
        this.feature = feature;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        switch (feature.getName()) {
        case "calendar":
            return new CommandResult(MESSAGE_SUCCESS_CALENDAR, feature, model);
        case "attendance":
            return new CommandResult(MESSAGE_SUCCESS_ATTENDANCE, feature, model);
        case "performance":
            return new CommandResult(MESSAGE_SUCCESS_PERFORMANCE, feature, model);
        case "records":
            boolean hasEvent = model.hasEvent(new Event(feature.getEventName()));
            if (!hasEvent) {
                throw new CommandException(String.format(Event.MESSAGE_NO_SUCH_EVENT, feature.getEventName()));
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS_RECORDS, feature.getEventName()), feature, model,
                    feature.getEventName());
        default:
            throw new CommandException(MESSAGE_INVALID_FEATURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ViewCommand // instanceof handles nulls
                        && feature.equals(((ViewCommand) other).feature));
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
    @Override
    public String toString() {
        return "View Command";
    }
}
