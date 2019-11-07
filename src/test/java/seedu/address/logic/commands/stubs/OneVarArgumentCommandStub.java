package seedu.address.logic.commands.stubs;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandBuilder;
import seedu.address.logic.commands.arguments.StringVariableArguments;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.ui.UserOutput;

/**
 * Command with one variable argument.
 */
public class OneVarArgumentCommandStub extends Command {

    private final List<String> arguments;

    private OneVarArgumentCommandStub(Builder builder) {
        this.arguments = builder.arguments;
    }

    public static CommandBuilder newBuilder() {
        return new Builder().init();
    }

    @Override
    public UserOutput execute() {
        return new UserOutput(String.join(",", arguments));
    }

    /** CommandStub */
    private static class Builder extends CommandBuilder {

        private List<String> arguments;

        @Override
        protected CommandBuilder init() {
            return super.init();
        }

        @Override
        protected RequiredArgumentList defineCommandArguments() {
            return ArgumentList.required()
                .setVariableArguments(StringVariableArguments.newBuilder("", l -> this.arguments = l));
        }

        @Override
        protected Map<String, OptionalArgumentList> defineCommandOptions() {
            return null;
        }

        @Override
        protected Command commandBuild() {
            return new OneVarArgumentCommandStub(this);
        }
    }
}
