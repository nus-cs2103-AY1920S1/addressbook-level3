package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.NotificationManager;
import seedu.address.logic.commands.options.Option;
import seedu.address.logic.commands.options.OptionBuilder;

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
    OptionBuilder getCommandArguments() {
        return Option.newBuilder();
    }

    @Override
    Map<String, OptionBuilder> getCommandOptions() {
        return Map.of();
    }

    @Override
    Command commandBuild() {
        return new NotificationOnCommand(this);
    }
}
