package seedu.address.logic.commands.arguments;

import java.util.List;
import java.util.function.Consumer;

import seedu.address.logic.parser.IndexParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Represents a variable argument of Indexes.
 */
public class IndexVariableArguments extends VariableArguments<Integer> {

    IndexVariableArguments(VariableArgumentsBuilder<Integer> builder) {
        super(builder);
    }

    public static IndexVariableArgumentsBuilder newBuilder(String description, Consumer<List<Integer>> promise) {
        return new IndexVariableArgumentsBuilder(description, promise);
    }

    @Override
    Integer parse(String userInput) throws ParseException {
        return new IndexParser().parse(userInput);
    }
}
