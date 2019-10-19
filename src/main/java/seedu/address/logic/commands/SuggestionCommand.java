package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandWordException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class SuggestionCommand extends Command {

    public static final String MESSAGE = "%1$s is not recognised.\nDid you mean: ";

    private String originalCommand;
    private String suggestedCommand;
    private String arguments;

    public SuggestionCommand(String originalCommand, String suggestedCommand, String arguments) {
        this.originalCommand = originalCommand;
        this.suggestedCommand = suggestedCommand;
        this.arguments = arguments;
    }

    @Override
    public CommandResult execute(Model model) throws CommandWordException {
        requireNonNull(model);
        String message = generateMessage();
        String suggestedCommand = generateSuggestedCommand();
        throw new CommandWordException(message, suggestedCommand);
    }

    private String generateMessage() {
        StringBuilder message = new StringBuilder();
        message.append(String.format(MESSAGE, originalCommand) + generateSuggestedCommand());
        return message.toString();
    }

    private String generateSuggestedCommand() {
        StringBuilder message = new StringBuilder();
        message.append(suggestedCommand + " " + arguments);
        return message.toString();
    }
}
