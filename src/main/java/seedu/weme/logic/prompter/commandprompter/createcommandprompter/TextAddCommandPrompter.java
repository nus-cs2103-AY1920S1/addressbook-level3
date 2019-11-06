package seedu.weme.logic.prompter.commandprompter.createcommandprompter;

import static seedu.weme.logic.commands.createcommand.TextAddCommand.MESSAGE_USAGE;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.ArgumentTokenizer.getLastArgument;
import static seedu.weme.logic.parser.util.ArgumentTokenizer.removeLastArgument;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_COLOR;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_SIZE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_STYLE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_X_COORDINATE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_Y_COORDINATE;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptSimilarCreateArguments;

import java.util.regex.Matcher;

import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.logic.prompter.util.LastArgument;
import seedu.weme.model.Model;

/**
 * Prompt possible arguments for TextAddCommand.
 */
public class TextAddCommandPrompter implements Prompter {

    @Override
    public CommandPrompt prompt(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        matcher.matches();
        final String arguments = matcher.group(ARGUMENTS);

        if (arguments.isBlank()) {
            return new CommandPrompt(MESSAGE_USAGE, userInput);
        }

        LastArgument lastArgument = getLastArgument(arguments, PREFIX_X_COORDINATE, PREFIX_Y_COORDINATE, PREFIX_COLOR,
                PREFIX_STYLE, PREFIX_SIZE);
        if (lastArgument == null) {
            return new CommandPrompt(MESSAGE_USAGE, userInput);
        }

        String inputWithoutLastArgument = removeLastArgument(userInput, PREFIX_X_COORDINATE, PREFIX_Y_COORDINATE,
                PREFIX_COLOR, PREFIX_STYLE, PREFIX_SIZE);
        return promptSimilarCreateArguments(model, inputWithoutLastArgument, lastArgument);
    }
}
