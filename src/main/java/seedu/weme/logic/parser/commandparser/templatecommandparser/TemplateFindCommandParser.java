package seedu.weme.logic.parser.commandparser.templatecommandparser;

import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.weme.logic.commands.templatecommand.TemplateFindCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.model.template.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new {@code TemplateFindCommand} object
 */
public class TemplateFindCommandParser implements Parser<TemplateFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TemplateFindCommand
     * and returns a TemplateFindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TemplateFindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TemplateFindCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new TemplateFindCommand(new NameContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
