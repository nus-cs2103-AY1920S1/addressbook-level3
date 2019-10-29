package seedu.address.logic.commands.schedule;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.processor.DistinctDatesProcessor;
import seedu.address.model.Model;
import seedu.address.model.distinctdate.DistinctDate;

/**
 * Generates a new list of DistinctDate objects, which represented all the Dates in the current
 * schedule. Each Date will display all the events that is held on the corresponding date.
 */
public class GenerateScheduleCommand extends Command {
    public static final String COMMAND_WORD = "generate_schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates a new list and displays all Dates and the events that corresponds to those dates"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Schedule Generated";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<DistinctDate> distinctDates = DistinctDatesProcessor.generateAllDistinctDateList(model);
        model.updateEventDistinctDatesList(distinctDates);
        return new CommandResult(MESSAGE_SUCCESS, "Generate");
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
