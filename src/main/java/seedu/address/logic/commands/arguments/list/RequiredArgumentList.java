package seedu.address.logic.commands.arguments.list;

import seedu.address.logic.commands.arguments.ArgumentBuilder;
import seedu.address.logic.commands.arguments.VariableArgumentsBuilder;

/**
 * A RequiredArgumentList is a builder that creates an {@link ArgumentList}.
 * It's arguments are always required.
 */
public class RequiredArgumentList extends ArgumentListBuilder {
    RequiredArgumentList() {
        super(true);
    }

    @Override
    public RequiredArgumentList addArgument(ArgumentBuilder argument) {
        super.addArgument(argument);
        return this;
    }

    @Override
    public RequiredArgumentList setVariableArguments(VariableArgumentsBuilder variableArguments) {
        super.setVariableArguments(variableArguments);
        return this;
    }
}
