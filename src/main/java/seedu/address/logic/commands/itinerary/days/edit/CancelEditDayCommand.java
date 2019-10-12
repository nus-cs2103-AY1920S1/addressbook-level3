package seedu.address.logic.commands.itinerary.days.edit;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.itinerary.day.Day;

/**
 * Placeholder.
 */
public class CancelEditDayCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels editing or creating a new day.";

    public static final String MESSAGE_CANCEL_CREATE_SUCCESS = "Cancelled creating the day!";
    public static final String MESSAGE_CANCEL_EDIT_SUCCESS = "Cancelled editing the day: %1$s";

    public CancelEditDayCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Day currentlyEditingDay = model.getPageStatus().getDay();
        model.setPageStatus(model.getPageStatus()
                .withResetEditDayDescriptor()
                .withNewPageType(PageType.OVERALL_VIEW)
                .withResetDay());

        if (currentlyEditingDay == null) {
            return new CommandResult(MESSAGE_CANCEL_CREATE_SUCCESS, true);
        } else {
            return new CommandResult(
                    String.format(MESSAGE_CANCEL_EDIT_SUCCESS, currentlyEditingDay), true);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CancelEditDayCommand;
    }

}
