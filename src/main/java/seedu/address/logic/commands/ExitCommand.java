package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXIT_SUCCESS;

import java.util.Map;

import javafx.application.Platform;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command which exits Horo.
 */
public class ExitCommand extends Command {

    private static final long EXIT_DURATION = 1000;

    ExitCommand() {}

    public static CommandBuilder newBuilder() {
        return new ExitCommandBuilder().init();
    }

    @Override
    public UserOutput execute() {
        new Thread(() -> {
            try {
                Thread.sleep(EXIT_DURATION);
                Platform.exit();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return new UserOutput(MESSAGE_EXIT_SUCCESS);
    }
}

/**
 * Represents a CommandBuilder responsible for creating {@link ExitCommand}.
 */
class ExitCommandBuilder extends CommandBuilder {

    @Override
    RequiredArgumentList defineCommandArguments() {
        return null;
    }

    @Override
    Map<String, OptionalArgumentList> defineCommandOptions() {
        return null;
    }

    @Override
    Command commandBuild() {
        return new ExitCommand();
    }
}
