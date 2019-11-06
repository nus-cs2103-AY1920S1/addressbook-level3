package seedu.address.logic.parser;

import java.util.Map;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandBuilder;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.ui.UserOutput;

/** CommandStub */
public class CommandStub extends Command {

    static CommandBuilder newBuilder() {
        return new CommandBuilderStub().init();
    }

    @Override
    public UserOutput execute() {
        return null;
    }

    /** CommandStub */
    private static class CommandBuilderStub extends CommandBuilder {

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
