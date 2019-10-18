package seedu.weme.logic.prompter;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.ArgumentTokenizer.getLastArgument;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.weme.logic.prompter.PrompterUtil.findSimilarArguments;

import seedu.weme.logic.commands.MemeAddCommand;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.model.Model;

/**
 * Prompt arguments for MemeAddCommand.
 */
public class MemeAddCommandPrompter implements Prompter {
    private static final int PREFIX_LENGTH = 2;

    @Override
    public CommandPrompt prompt(Model model, String arguments) throws PromptException {
        if (arguments.isBlank()) {
            return CommandPrompt.empty();
        }

        LastArgument lastArgument = getLastArgument(arguments, PREFIX_FILEPATH, PREFIX_DESCRIPTION, PREFIX_TAG);
        if (arguments.length() > PREFIX_LENGTH && lastArgument == null) {
            throw new PromptException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MemeAddCommand.MESSAGE_USAGE));
        } else if (lastArgument == null) {
            return CommandPrompt.empty();
        }

        return findSimilarArguments(model, lastArgument);
    }
}
