package seedu.address.logic.commands.stubs;

import java.util.Map;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandBuilder;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.ui.UserOutput;

/**
 * Command with no arguments or parameters.
 */
public class CommandStub extends Command {

    public static CommandBuilder newBuilder() {
        return new Builder().init();
    }

    @Override
    public UserOutput execute() {
        return null;
    }

    /** CommandStub */
    public static class Builder extends CommandBuilder {

        @Override
        protected CommandBuilder init() {
            return super.init();
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
            return new CommandStub();
        }
    }
}
