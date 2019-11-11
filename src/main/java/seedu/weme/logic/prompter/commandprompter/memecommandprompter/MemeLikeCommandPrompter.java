package seedu.weme.logic.prompter.commandprompter.memecommandprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.commands.memecommand.MemeLikeCommand.MESSAGE_USAGE;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;

import java.util.regex.Matcher;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.model.Model;

/**
 * Prompt arguments for MemeLikeCommand.
 */
public class MemeLikeCommandPrompter implements Prompter {
    private static final String ARROW_KEY_LIKE_USAGE = "You can now use UP arrow key to spam likes!";

    @Override
    public CommandPrompt prompt(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        matcher.matches();
        final String arguments = matcher.group(ARGUMENTS);

        if (arguments.isBlank()) {
            return new CommandPrompt(MESSAGE_USAGE, userInput);
        }

        try {
            if (!arguments.trim().matches("\\d+") || Integer.parseInt(arguments.trim()) == 0) {
                throw new NumberFormatException();
            }
            return new CommandPrompt(ARROW_KEY_LIKE_USAGE, userInput);
        } catch (NumberFormatException e) {
            throw new PromptException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
    }
}
