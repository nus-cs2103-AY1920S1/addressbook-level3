package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_ADD_EVENT_SUCCESS;

import java.util.Objects;

import seedu.address.model.Model;
import seedu.address.model.events.DateTime;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class DayViewCommand extends Command {

    private final Model model;

    DayViewCommand(DayViewCommandBuilder builder) {
        DateTime start = Objects.requireNonNull(builder.getStart());

        this.model = builder.getModel();
    }

    public static CommandBuilder newBuilder(Model model) {
        return new DayViewCommandBuilder(model).init();
    }

    @Override
    public UserOutput execute() {
        return new UserOutput(String.format(MESSAGE_ADD_EVENT_SUCCESS));
    }
}
