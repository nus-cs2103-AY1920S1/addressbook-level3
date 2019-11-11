//@@author stevenwjy
package tagline.logic.parser.contact;

import java.util.Arrays;
import java.util.Collections;

import tagline.logic.commands.contact.FindContactCommand;
import tagline.logic.parser.Parser;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.exceptions.ParseException;
import tagline.logic.parser.exceptions.PromptRequestException;
import tagline.model.contact.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindContactCommand object.
 */
public class FindContactParser implements Parser<FindContactCommand> {
    public static final String FIND_CONTACT_EMPTY_ARGS_PROMPT_STRING = "Please enter at least one keyword to find by.";

    /**
     * Parses the given {@code String} of arguments in the context of the FindContactCommand
     * and returns a FindContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindContactCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new PromptRequestException(Collections.singletonList(
                    new Prompt("", FIND_CONTACT_EMPTY_ARGS_PROMPT_STRING)));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindContactCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
