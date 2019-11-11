package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_NOTIFICATION_ON;

import seedu.address.logic.NotificationManager;
import seedu.address.ui.UserOutput;

/**
 * Represents a command that switches notifications on.
 */
public class NotificationOnCommand extends Command {

    private final NotificationManager manager;

    NotificationOnCommand (NotificationOnCommandBuilder builder) {
        this.manager = builder.getManager();
    }

    public static CommandBuilder newBuilder(NotificationManager manager) {
        return new NotificationOnCommandBuilder(manager).init();
    }

    @Override
    public UserOutput execute() {
        manager.switchOnNotifications();
        return new UserOutput(MESSAGE_NOTIFICATION_ON);
    }
}
