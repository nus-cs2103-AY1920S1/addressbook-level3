package seedu.weme.logic.prompter.commandprompter.memecommandprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.commands.memecommand.MemeAddCommand.MESSAGE_USAGE;
import static seedu.weme.logic.parser.util.ArgumentTokenizer.getLastArgument;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.weme.logic.prompter.util.PrompterUtil.PREFIX_LENGTH;
import static seedu.weme.logic.prompter.util.PrompterUtil.findSimilarArguments;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.logic.prompter.util.LastArgument;
import seedu.weme.model.Model;

/**
 * Prompt possible arguments for MemeAddCommand.
 */
public class MemeAddCommandPrompter implements Prompter {

    @Override
    public CommandPrompt prompt(Model model, String arguments) throws PromptException {
        if (arguments.isBlank()) {
            return new CommandPrompt(MESSAGE_USAGE);
        }

        LastArgument lastArgument = getLastArgument(arguments, PREFIX_FILEPATH, PREFIX_DESCRIPTION, PREFIX_TAG);
        if (arguments.trim().length() >= PREFIX_LENGTH && lastArgument == null) {
            throw new PromptException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        } else if (lastArgument == null) {
            return new CommandPrompt(MESSAGE_USAGE);
        }

        return findSimilarArguments(model, lastArgument);
    }
}
