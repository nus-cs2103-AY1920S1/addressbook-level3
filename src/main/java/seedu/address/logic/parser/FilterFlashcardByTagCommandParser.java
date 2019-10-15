package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.FilterFlashcardByTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.flashcard.FlashcardContainsTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FilterFlashcardByTagCommandParser implements Parser<FilterFlashcardByTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterFlashcardByTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterFlashcardByTagCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");
        Set<Tag> tags = new HashSet<>();
        // copy to array of tags
        for (String s : tagKeywords) {
            tags.add(new Tag(s));
        }
        return new FilterFlashcardByTagCommand(new FlashcardContainsTagPredicate(tags), tagKeywords);
    }

}
