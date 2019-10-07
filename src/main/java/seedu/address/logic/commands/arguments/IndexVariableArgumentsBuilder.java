package seedu.address.logic.commands.arguments;

public class IndexVariableArgumentsBuilder extends VariableArgumentsBuilder<Integer> {

    IndexVariableArgumentsBuilder(String description) {
        super(description);
    }

    @Override
    VariableArguments<Integer> argumentBuild() {
        return new IndexVariableArguments(this);
    }
}
