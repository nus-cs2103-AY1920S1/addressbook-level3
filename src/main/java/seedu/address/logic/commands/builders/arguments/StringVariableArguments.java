package seedu.address.logic.commands.builders.arguments;

import java.util.List;
import java.util.function.Consumer;
import seedu.address.logic.parser.exceptions.ParseException;

public class StringVariableArguments extends VariableArguments<String> {

    public StringVariableArguments(String description, Consumer<List<String>> builder) {
        super(description, builder);
    }

    @Override
    String parse(String userInput) throws ParseException {
        return userInput;
    }
}
