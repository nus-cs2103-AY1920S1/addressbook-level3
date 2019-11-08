package seedu.address.logic.commands.stubs;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandBuilder;
import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.arguments.StringVariableArguments;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.ui.UserOutput;

/**
 * Command with two arguments and one variable argument.
 */
public class TwoArgumentsOneVarArgumentCommandStub extends Command {

    private String argument1;
    private String argument2;
    private final List<String> arguments;

    private TwoArgumentsOneVarArgumentCommandStub(Builder builder) {
        this.argument1 = builder.argument1;
        this.argument2 = builder.argument2;
        this.arguments = builder.arguments;
    }

    public static CommandBuilder newBuilder() {
        return new Builder().init();
    }

    @Override
    public UserOutput execute() {
        return new UserOutput(String.format("[%s,%s][%s]",
            this.argument1,
            this.argument2,
            String.join(",", arguments)));
    }

    /** CommandStub */
    private static class Builder extends CommandBuilder {

        private String argument1;
        private String argument2;
        private List<String> arguments;

        @Override
        protected CommandBuilder init() {
            return super.init();
        }

        @Override
        protected RequiredArgumentList defineCommandArguments() {
            return ArgumentList.required()
                .addArgument(StringArgument.newBuilder("", s -> this.argument1 = s))
                .addArgument(StringArgument.newBuilder("", s -> this.argument2 = s))
                .setVariableArguments(StringVariableArguments.newBuilder("", l -> this.arguments = l));
        }

        @Override
        protected Map<String, OptionalArgumentList> defineCommandOptions() {
            return null;
        }

        @Override
        protected Command commandBuild() {
            return new TwoArgumentsOneVarArgumentCommandStub(this);
        }
    }
}
