package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;

/**
 * Command to export visual representations
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_SUCCESS = "Exporting the full schedule at this week.\n"
            + "Exported image shows the schedules for the entire group or specific individual.";
    public static final String MESSAGE_FAILURE = "No schedule to export! Please ensure that there is a schedule in "
            + "your schedule window display before you use the command!";
    public static final String MESSAGE_USAGE = "Export command does not take in any arguments!";


    public ExportCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        //Does nothing to the model.
        requireNonNull(model);
        ScheduleState state = model.getState();
        if (!state.equals(ScheduleState.PERSON) && !state.equals(ScheduleState.GROUP)) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResultBuilder(MESSAGE_SUCCESS)
                .setExport().build();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ExportCommand;
    }

    @Override
    public boolean equals(Command o) {
        return o instanceof ExportCommand;
    }
}
