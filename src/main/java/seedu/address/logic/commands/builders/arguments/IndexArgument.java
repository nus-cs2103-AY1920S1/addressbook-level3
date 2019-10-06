package seedu.address.logic.commands.builders.arguments;

import java.util.function.Consumer;

import seedu.address.logic.parser.IndexParser;
import seedu.address.logic.parser.exceptions.ParseException;

public class IndexArgument extends Argument<Integer> {

    public IndexArgument(String description, Consumer<Integer> builder) {
        super(description, builder);
    }

    @Override
    Integer parse(String userInput) throws ParseException {
        return new IndexParser().parse(userInput);
    }
}
