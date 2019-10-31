package seedu.weme.logic.prompter.commandprompter.importcommandprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.commands.importcommand.ImportEditCommand.MESSAGE_USAGE;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.ArgumentTokenizer.getLastArgument;
import static seedu.weme.logic.parser.util.ArgumentTokenizer.removeLastArgument;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptSimilarArguments;

import java.util.regex.Matcher;

import seedu.weme.logic.parser.util.ArgumentTokenizer;
import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.logic.prompter.util.LastArgument;
import seedu.weme.model.Model;

/**
 * Prompt arguments for ImportEditCommand.
 */
public class ImportEditCommandPrompter implements Prompter {

    @Override
    public CommandPrompt prompt(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        matcher.matches();
        final String arguments = matcher.group(ARGUMENTS);

        if (arguments.isBlank()) {
            return new CommandPrompt(MESSAGE_USAGE, userInput);
        }

        ArgumentTokenizer.tokenize(arguments, PREFIX_DESCRIPTION, PREFIX_TAG);
        LastArgument lastArgument = getLastArgument(arguments, PREFIX_DESCRIPTION, PREFIX_TAG);
        if (lastArgument == null) {
            try {
                // Checks whether the arguments is in the correct format:
                // either contains index only or contains index and partial prefix.
                char partialPrefix = arguments.charAt(arguments.length() - 1);
                if (partialPrefix == PREFIX_DESCRIPTION.toString().charAt(0)
                        || partialPrefix == PREFIX_TAG.toString().charAt(0)) {
                    Integer.parseInt(arguments.substring(0, arguments.length() - 1).trim());
                } else {
                    Integer.parseInt(arguments.trim());
                }
                return new CommandPrompt(MESSAGE_USAGE, userInput);
            } catch (NumberFormatException e) {
                throw new PromptException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        }

        String inputWithoutLastArgument = removeLastArgument(userInput, PREFIX_DESCRIPTION, PREFIX_TAG);
        return promptSimilarArguments(model, inputWithoutLastArgument, lastArgument);
    }
}
