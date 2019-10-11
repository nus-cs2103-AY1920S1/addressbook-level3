package seedu.address.logic.commands.common;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.preferences.EditPrefsFieldCommand.EditPrefsDescriptor;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;

/**
 * Command class responsible for navigating to the preferences screen.
 */
public class EnterPrefsCommand extends Command {
    public static final String COMMAND_WORD = "prefs";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enters the preferences page of TravelPal.";

    public static final String MESSAGE_SUCCESS = "Entered the preferences screen.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        EditPrefsDescriptor editPrefsDescriptor = new EditPrefsDescriptor(model.getUserPrefs());

        model.setPageStatus(model.getPageStatus()
                .withNewPageType(PageType.PREFERENCES)
                .withNewEditPrefsDescriptor(editPrefsDescriptor));

        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof EnterPrefsCommand;
    }
}
