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
 * Command with two arguments.
 */
public class TwoArgumentsCommandStub extends Command {

    private final String argument1;
    private final String argument2;

    private TwoArgumentsCommandStub(Builder builder) {
        this.argument1 = builder.argument1;
        this.argument2 = builder.argument2;
    }

    public static CommandBuilder newBuilder() {
        return new Builder().init();
    }

    @Override
    public UserOutput execute() {
        return new UserOutput(String.format("%s,%s",
            this.argument1, this.argument2));
    }

    /** CommandStub */
    private static class Builder extends CommandBuilder {

        private String argument1;
        private String argument2;

        @Override
        protected CommandBuilder init() {
            return super.init();
        }

        @Override
        protected RequiredArgumentList defineCommandArguments() {
            return ArgumentList.required()
                .addArgument(StringArgument.newBuilder("", s -> this.argument1 = s))
                .addArgument(StringArgument.newBuilder("", s -> this.argument2 = s));
        }

        @Override
        protected Map<String, OptionalArgumentList> defineCommandOptions() {
            return null;
        }

        @Override
        protected Command commandBuild() {
            return new TwoArgumentsCommandStub(this);
        }
    }
}
