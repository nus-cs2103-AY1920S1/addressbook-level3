package seedu.weme.logic.prompter.commandprompter.createcommandprompter;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.commands.createcommand.TextEditCommand.MESSAGE_USAGE;
import static seedu.weme.logic.parser.contextparser.WemeParser.ARGUMENTS;
import static seedu.weme.logic.parser.contextparser.WemeParser.BASIC_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.ArgumentTokenizer.getLastArgument;
import static seedu.weme.logic.parser.util.ArgumentTokenizer.removeLastArgument;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_COLOR;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_SIZE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_STYLE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TEXT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_X_COORDINATE;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_Y_COORDINATE;
import static seedu.weme.logic.prompter.util.PrompterUtil.promptSimilarCreateArguments;

import java.util.regex.Matcher;

import seedu.weme.logic.commands.createcommand.TextEditCommand;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ArgumentMultimap;
import seedu.weme.logic.parser.util.ArgumentTokenizer;
import seedu.weme.logic.parser.util.ParserUtil;
import seedu.weme.logic.prompter.Prompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.logic.prompter.util.LastArgument;
import seedu.weme.model.Model;

/**
 * Prompt possible arguments for TextEditCommand.
 */
public class TextEditCommandPrompter implements Prompter {

    @Override
    public CommandPrompt prompt(Model model, String userInput) throws PromptException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        matcher.matches();
        final String arguments = matcher.group(ARGUMENTS);

        if (arguments.isBlank()) {
            return new CommandPrompt(MESSAGE_USAGE, userInput);
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(arguments, PREFIX_TEXT, PREFIX_X_COORDINATE,
                PREFIX_Y_COORDINATE, PREFIX_COLOR, PREFIX_STYLE, PREFIX_SIZE);
        LastArgument lastArgument = getLastArgument(arguments, PREFIX_TEXT, PREFIX_X_COORDINATE, PREFIX_Y_COORDINATE,
                PREFIX_COLOR, PREFIX_STYLE, PREFIX_SIZE);
        if (lastArgument == null) {
            try {
                // Checks whether the arguments is in the correct format:
                // either contains index only or contains index and partial prefix.
                char partialPrefix = arguments.charAt(arguments.length() - 1);
                if (partialPrefix == PREFIX_X_COORDINATE.toString().charAt(0)
                        || partialPrefix == PREFIX_Y_COORDINATE.toString().charAt(0)
                        || partialPrefix == PREFIX_TEXT.toString().charAt(0)
                        || partialPrefix == PREFIX_SIZE.toString().charAt(0)
                        || partialPrefix == PREFIX_COLOR.toString().charAt(0)
                        || partialPrefix == PREFIX_STYLE.toString().charAt(0)) {
                    Integer.parseInt(arguments.substring(0, arguments.length() - 1).trim());
                } else {
                    Integer.parseInt(arguments.trim());
                }
                return new CommandPrompt(TextEditCommand.MESSAGE_USAGE, userInput);
            } catch (NumberFormatException e) {
                throw new PromptException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        } else {
            try {
                ParserUtil.parseIndex(argMultimap.getPreamble());
            } catch (ParseException e) {
                throw new PromptException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        }

        String inputWithoutLastArgument = removeLastArgument(userInput, PREFIX_TEXT, PREFIX_X_COORDINATE,
                PREFIX_Y_COORDINATE, PREFIX_COLOR, PREFIX_STYLE, PREFIX_SIZE);
        return promptSimilarCreateArguments(model, inputWithoutLastArgument, lastArgument);
    }
}
