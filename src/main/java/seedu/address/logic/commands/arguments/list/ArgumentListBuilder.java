package seedu.address.logic.commands.arguments.list;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.arguments.Argument;
import seedu.address.logic.commands.arguments.ArgumentBuilder;
import seedu.address.logic.commands.arguments.VariableArguments;
import seedu.address.logic.commands.arguments.VariableArgumentsBuilder;

/**
 * Represents an object that builds {@link ArgumentList}.
 */
public abstract class ArgumentListBuilder {

    // Determines whether or not the arguments in this list is required.
    private final boolean required;
    private final List<Argument> arguments;
    private VariableArguments variableArguments;

    ArgumentListBuilder(boolean required) {
        this.required = required;
        this.arguments = new ArrayList<>();
    }

    /**
     * Adds an argument to this option.
     * @param argument the argument
     * @return this instance
     */
    public ArgumentListBuilder addArgument(ArgumentBuilder argument) {
        this.arguments.add(argument.build());
        return this;
    }

    public ArgumentListBuilder setVariableArguments(VariableArgumentsBuilder variableArguments) {
        this.variableArguments = variableArguments.build();
        return this;
    }

    boolean isRequired() {
        return required;
    }

    List<Argument> getArguments() {
        return arguments;
    }

    VariableArguments getVariableArguments() {
        return variableArguments;
    }

    public ArgumentList build() {
        return new ArgumentList(this);
    }
}
