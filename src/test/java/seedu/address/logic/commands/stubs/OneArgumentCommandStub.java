package seedu.address.logic.commands.stubs;

import java.util.Map;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandBuilder;
import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.ui.UserOutput;

/**
 * Command with one argument.
 */
public class OneArgumentCommandStub extends Command {

    private final String argument;

    private OneArgumentCommandStub(Builder builder) {
        this.argument = builder.argument;
    }

    public static CommandBuilder newBuilder() {
        return new Builder().init();
    }

    @Override
    public UserOutput execute() {
        return new UserOutput(argument);
    }

    /** CommandStub */
    public static class Builder extends CommandBuilder {

        private String argument;

        @Override
        protected CommandBuilder init() {
            return super.init();
        }

        @Override
        protected RequiredArgumentList defineCommandArguments() {
            return ArgumentList.required()
                .addArgument(StringArgument.newBuilder("", s -> this.argument = s));
        }

        @Override
        protected Map<String, OptionalArgumentList> defineCommandOptions() {
            return null;
        }

        @Override
        protected Command commandBuild() {
            return new OneArgumentCommandStub(this);
        }
    }
}
