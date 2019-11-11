package seedu.weme.logic.prompter.commandprompter.importcommandprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.commands.importcommand.LoadCommand.MESSAGE_USAGE;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.ArgumentTokenizer.getLastArgument;
import static seedu.weme.logic.parser.util.ArgumentTokenizer.removeLastArgument;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_FILEPATH;
import static seedu.weme.logic.prompter.util.PrompterUtil.PREFIX_LENGTH;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptSimilarImportExportArguments;

import java.util.regex.Matcher;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.logic.prompter.util.LastArgument;
import seedu.weme.model.Model;

/**
 * Prompt possible arguments for LoadCommand.
 */
public class LoadCommandPrompter implements Prompter {

    @Override
    public CommandPrompt prompt(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        matcher.matches();
        final String arguments = matcher.group(ARGUMENTS);

        if (arguments.isBlank()) {
            return new CommandPrompt(MESSAGE_USAGE, userInput);
        }

        LastArgument lastArgument = getLastArgument(arguments, PREFIX_FILEPATH);
        if (arguments.trim().length() >= PREFIX_LENGTH && lastArgument == null) {
            throw new PromptException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        } else if (lastArgument == null) {
            return new CommandPrompt(MESSAGE_USAGE, userInput);
        }

        String inputWithoutLastArgument = removeLastArgument(userInput, PREFIX_FILEPATH);
        return promptSimilarImportExportArguments(model, inputWithoutLastArgument, lastArgument);
    }
}
