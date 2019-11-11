package seedu.weme.logic.prompter.commandprompter.createcommandprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.commands.createcommand.TextMoveCommand.MESSAGE_USAGE;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.ArgumentTokenizer.getLastArgument;
import static seedu.weme.logic.parser.util.ArgumentTokenizer.removeLastArgument;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TEXT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_X_COORDINATE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_Y_COORDINATE;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptSimilarCreateArguments;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.createcommand.TextMoveCommand;
import seedu.weme.logic.parser.util.ArgumentTokenizer;
import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.logic.prompter.util.LastArgument;
import seedu.weme.model.Model;

/**
 * Prompt possible arguments for TextMoveCommand.
 */
public class TextMoveCommandPrompter implements Prompter {

    private static final String MOVE_TEXT_ARROW_KEYS_PROMPT = "You can type in the full command, but you can also use "
            + "arrow keys to move the text now.\n"
            + "To make the text move faster, hold Shift.\n"
            + "To make the text move slower, hold Alt";

    @Override
    public CommandPrompt prompt(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        matcher.matches();
        final String arguments = matcher.group(ARGUMENTS);

        if (arguments.isBlank()) {
            return new CommandPrompt(MESSAGE_USAGE, userInput);
        }

        ArgumentTokenizer.tokenize(arguments, PREFIX_TEXT, PREFIX_X_COORDINATE, PREFIX_Y_COORDINATE);
        LastArgument lastArgument = getLastArgument(arguments, PREFIX_TEXT, PREFIX_X_COORDINATE, PREFIX_Y_COORDINATE);
        if (lastArgument == null) {
            try {
                // Checks whether the arguments is in the correct format:
                // either contains index only or contains index and partial prefix.
                char partialPrefix = arguments.charAt(arguments.length() - 1);
                if (partialPrefix == PREFIX_X_COORDINATE.toString().charAt(0)
                        || partialPrefix == PREFIX_Y_COORDINATE.toString().charAt(0)) {
                    Integer.parseInt(arguments.substring(0, arguments.length() - 1).trim());
                } else {
                    Integer.parseInt(arguments.trim());
                    return new CommandPrompt(MOVE_TEXT_ARROW_KEYS_PROMPT, userInput);
                }
                return new CommandPrompt(TextMoveCommand.MESSAGE_USAGE, userInput);
            } catch (NumberFormatException e) {
                throw new PromptException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        }

        String inputWithoutLastArgument = removeLastArgument(userInput, PREFIX_TEXT, PREFIX_X_COORDINATE,
                PREFIX_Y_COORDINATE);
        return promptSimilarCreateArguments(model, inputWithoutLastArgument, lastArgument);
    }
}
