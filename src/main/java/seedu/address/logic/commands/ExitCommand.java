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

    private ExitCommand() {}

    public static CommandBuilder newBuilder() {
        return new Builder().init();
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

    /**
     * Represents a CommandBuilder responsible for creating {@link ExitCommand}.
     */
    static class Builder extends CommandBuilder {

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
            return new ExitCommand();
        }
    }
}
