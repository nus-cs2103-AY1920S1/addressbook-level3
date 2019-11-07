package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.NotificationManager;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;

/**
 * Represents a CommandBuilder responsible for creating {@link NotificationOnCommand}.
 */
public class NotificationOnCommandBuilder extends CommandBuilder {
    private final NotificationManager manager;

    NotificationOnCommandBuilder(NotificationManager manager) {
        this.manager = manager;
    }

    NotificationManager getManager() {
        return manager;
    }

    @Override
    protected RequiredArgumentList defineCommandArguments() {
        return null;
    }

    @Override
    protected Map<String, OptionalArgumentList> defineCommandOptions() {
        return null;
    }

    @Override
    protected Command commandBuild() {
        return new NotificationOnCommand(this);
    }
}
