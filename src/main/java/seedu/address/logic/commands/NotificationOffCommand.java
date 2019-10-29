package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_NOTIFICATION_OFF;

import seedu.address.logic.NotificationManager;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command that switches notifications off.
 */
public class NotificationOffCommand extends Command {

    private final NotificationManager manager;

    NotificationOffCommand (NotificationOffCommandBuilder builder) {
        this.manager = builder.getManager();
    }

    public static CommandBuilder newBuilder(NotificationManager manager) {
        return new NotificationOffCommandBuilder(manager).init();
    }

    @Override
    public UserOutput execute() {
        manager.switchOffNotifications();
        return new UserOutput(MESSAGE_NOTIFICATION_OFF);
    }
}
