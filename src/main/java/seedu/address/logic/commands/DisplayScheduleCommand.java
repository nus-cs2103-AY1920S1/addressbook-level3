package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.processor.DistinctDatesProcessor;
import seedu.address.model.Model;
import seedu.address.model.distinctDate.DistinctDate;
import seedu.address.model.event.EventContainsKeyDatePredicate;

import java.util.List;

/**
 * Finds and lists all events in event list whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class DisplayScheduleCommand extends Command {
    public static final String COMMAND_WORD = "display";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Display all Dates and the events on that is on those dates"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed entire schedule";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<DistinctDate> distinctDates = DistinctDatesProcessor.generateDistinctDateList(model);
        model.updateDistinctDatesList(distinctDates);
        return new CommandResult(MESSAGE_SUCCESS, "DisplaySchedule");
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
