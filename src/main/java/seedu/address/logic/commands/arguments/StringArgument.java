package seedu.address.logic.commands.arguments;

/**
 * Represents a command argument of type String.
 */
public class StringArgument extends CommandArgument<String> {

    public StringArgument(String description, boolean required) {
        super(description, required);
    }

    @Override
    public String parse(String userInput) {
        return userInput;
    }
}
