package tagline.logic.parser.contact;

import static tagline.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import tagline.logic.commands.contact.FindContactCommand;
import tagline.logic.parser.Parser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindContactCommand object
 */
public class FindContactParser implements Parser<FindContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindContactCommand
     * and returns a FindContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindContactCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindContactCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindContactCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
