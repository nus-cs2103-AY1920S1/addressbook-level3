package seedu.address.logic.commands.preferences;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.preferences.EditPrefsFieldCommand.EditPrefsDescriptor;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.model.trip.Trip;

import static java.util.Objects.requireNonNull;

public class CancelEditPrefsCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels editing your preferences.";

    public static final String MESSAGE_CANCEL_EDIT_SUCCESS = "Cancelled editing your preferences!\n"
            + "Your changes have been discarded. %1$s";

    public CancelEditPrefsCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        EditPrefsDescriptor currentEditedPrefs = model.getPageStatus().getEditPrefsDescriptor();
        model.setPageStatus(model.getPageStatus()
                .withNewEditPrefsDescriptor(null)
                .withNewPageType(PageType.TRIP_MANAGER));


        return new CommandResult(
                String.format(MESSAGE_CANCEL_EDIT_SUCCESS, currentEditedPrefs),
                true);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CancelEditPrefsCommand;
    }
}
