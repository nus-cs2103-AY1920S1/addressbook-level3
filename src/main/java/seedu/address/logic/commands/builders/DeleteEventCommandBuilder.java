package seedu.address.logic.commands.builders;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.builders.arguments.IndexVariableArguments;
import seedu.address.logic.commands.builders.arguments.StringVariableArguments;
import seedu.address.logic.commands.builders.options.Option;

public class DeleteEventCommandBuilder extends CommandBuilder {

    private static final String ARGUMENT_INDEXES = "INDEXES";
    private static final String ARGUMENT_TAGS = "TAGS";

    private static final String OPTION_TAG = "--tag";

    private List<Integer> indexes;
    private List<String> tags;

    @Override
    Option getCommandArguments() {
        return new Option()
            .setVariableArguments(new IndexVariableArguments(ARGUMENT_INDEXES, i -> this.indexes = i));
    }

    @Override
    Map<String, Option> getCommandOptions() {
        return Map.of(
            OPTION_TAG, new Option()
                .setVariableArguments(new StringVariableArguments(ARGUMENT_TAGS, s -> this.tags = s))
        );
    }

    @Override
    Command buildCommand() {
        return new DeleteEventCommand(this.indexes, this.tags);
    }
}
