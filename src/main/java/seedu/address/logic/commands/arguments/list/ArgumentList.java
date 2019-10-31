package seedu.address.logic.commands.arguments.list;

import java.util.List;

import seedu.address.logic.commands.arguments.Argument;
import seedu.address.logic.commands.arguments.VariableArguments;
import seedu.address.logic.commands.exceptions.ArgumentException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a list of arguments in a Command.
 * It can contain 0..* arguments, and can contain 0..1 variable arguments.
 */
public class ArgumentList {

    // Determines whether or not the arguments in this list is required.
    private boolean required;
    private int argumentIndex;

    private final List<Argument> arguments;
    private final VariableArguments variableArguments;

    ArgumentList(ArgumentListBuilder builder) {
        this.required = builder.isRequired();
        this.arguments = builder.getArguments();
        this.variableArguments = builder.getVariableArguments();
    }

    public static OptionalArgumentList optional() {
        return new OptionalArgumentList();
    }

    public static RequiredArgumentList required() {
        return new RequiredArgumentList();
    }

    /**
     * Accepts user input and passes it to the next argument for parsing.
     * @param argument the user input
     */
    public void acceptArgument(String argument) {
        if (this.argumentIndex < this.arguments.size()) {
            this.arguments.get(this.argumentIndex).accept(argument);
            this.argumentIndex++;
        } else if (this.variableArguments != null) {
            this.variableArguments.accept(argument);
        }
    }

    /**
     * Builds all arguments.
     * @throws ArgumentException if any argument is required but null
     * @throws ParseException if any user input is invalid
     */
    public void build() throws ArgumentException, ParseException {
        for (Argument argument : this.arguments) {
            argument.build(this.required);
        }

        if (this.variableArguments != null) {
            this.variableArguments.build();
        }
    }

    public void setRequired() {
        this.required = true;
    }
}
