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
        requireNonNull(originalCommand);
        requireNonNull(suggestedCommand);
        requireNonNull(arguments);
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

    /**
     * Generates the suggested command.
     * @return Suggested command.
     */
    private String generateSuggestedCommand() {
        StringBuilder message = new StringBuilder();
        if (arguments.length() == 0) {
            return message.append(suggestedCommand).toString();
        } else {
            message.append(suggestedCommand + " " + arguments);
            return message.toString();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SuggestionCommand // instanceof handles nulls
                && originalCommand.equals(((SuggestionCommand) other).originalCommand)
                && suggestedCommand.equals(((SuggestionCommand) other).suggestedCommand)
                && arguments.equals(((SuggestionCommand) other).arguments)); // state check
    }
}
