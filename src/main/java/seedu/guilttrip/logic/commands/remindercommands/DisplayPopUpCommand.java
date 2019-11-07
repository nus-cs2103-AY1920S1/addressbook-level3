package seedu.guilttrip.logic.commands.remindercommands;

import seedu.guilttrip.logic.CommandHistory;
import seedu.guilttrip.logic.commands.Command;
import seedu.guilttrip.logic.commands.CommandResult;
import seedu.guilttrip.logic.commands.exceptions.CommandException;
import seedu.guilttrip.model.Model;

/**
 * Displays popup for GeneralReminder.
 */
public class DisplayPopupCommand extends Command {
    public static final String COMMAND_WORD = "popup";
    public static final String NO_REMINDER_SELECTED = "Please select a generalReminder to inspect.";
    public static final String REMINDER_POPUP_DISABLED = "Please enable pop up before viewing or editing.";
    public static final String MESSAGE_DISPLAY_SUCCESS = "Display popup.";
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (model.getReminderSelected() == null) {
            throw new CommandException(NO_REMINDER_SELECTED);
        } else if (!model.getReminderSelected().willDisplayPopUp()) {
            throw new CommandException(REMINDER_POPUP_DISABLED);
        }
        CommandResult commandResult =
                new CommandResult(MESSAGE_DISPLAY_SUCCESS, model.getReminderSelected().getMessage());
        return commandResult;
    }
    @Override
    public boolean equals(Object other) {
        return (other instanceof DisplayPopupCommand);
    }
}
