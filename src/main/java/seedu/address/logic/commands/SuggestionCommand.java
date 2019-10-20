package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandWordException;
import seedu.address.model.Model;

/**
 * Gives suggestions for incorrect user input commands.
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
