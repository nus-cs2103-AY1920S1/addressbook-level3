package seedu.address.logic.commands.preferences;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.preferences.EditPrefsFieldCommand.EditPrefsDescriptor;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.appstatus.PageType;

import static java.util.Objects.requireNonNull;

/**
 * Constructs a command that attempts to commit the current changes in the edit trip page.
 */
public class DoneEditPrefsCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commits your edited preferneces.";

    public static final String MESSAGE_EDIT_PREFERENCES_SUCCESS = "Edited Preferences: %1$s";
    public static final String MESSAGE_NOT_EDITED = "All the fields must be provided!";
    public static final String MESSAGE_CLASHING_TRIP = "This trip clashes with one of your other trips!";

    public DoneEditPrefsCommand() { }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        EditPrefsDescriptor editPrefsDescriptor = model.getPageStatus().getEditPrefsDescriptor();
        requireNonNull(editPrefsDescriptor);


        try {
            //buildUserPrefs() requires all fields to be non present, failing which
            //NullPointerException is caught below
            UserPrefs userPrefs = editPrefsDescriptor.buildUserPrefs();
            model.setUserPrefs(userPrefs);

            model.setPageStatus(model.getPageStatus()
                    .withNewEditPrefsDescriptor(null)
                    .withNewPageType(PageType.TRIP_MANAGER));

            return new CommandResult(String.format(MESSAGE_EDIT_PREFERENCES_SUCCESS, userPrefs), true);
        } catch (NullPointerException ex) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof DoneEditPrefsCommand;
    }

}
