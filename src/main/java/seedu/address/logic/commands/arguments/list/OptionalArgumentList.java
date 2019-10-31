package seedu.address.logic.commands.arguments.list;

import seedu.address.logic.commands.arguments.ArgumentBuilder;
import seedu.address.logic.commands.arguments.VariableArgumentsBuilder;

/**
 * An OptionalArgumentList is a builder that creates an {@link ArgumentList}.
 * It's arguments are always optional.
 */
public class OptionalArgumentList extends ArgumentListBuilder {
    OptionalArgumentList() {
        super(false);
    }

    @Override
    public OptionalArgumentList addArgument(ArgumentBuilder argument) {
        super.addArgument(argument);
        return this;
    }


    @Override
    public OptionalArgumentList setVariableArguments(VariableArgumentsBuilder variableArguments) {
        super.setVariableArguments(variableArguments);
        return this;
    }
}
