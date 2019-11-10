package seedu.address.logic.parser.flashcard;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.flashcard.StartTimeTrialCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.flashcard.FlashcardContainsTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new StartTimeTrialCommand object
 */
public class StartTimeTrialCommandParser implements Parser<StartTimeTrialCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StartTimeTrialCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, StartTimeTrialCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");
        for (String keyword: tagKeywords) {
            assert (!keyword.isEmpty());
        }
        Set<Tag> tags = new HashSet<>();
        // copy to array of tags
        for (String s : tagKeywords) {
            try {
                tags.add(new Tag(s));
            } catch (IllegalArgumentException e) {
                throw new ParseException(e.getMessage());
            }
        }
        return new StartTimeTrialCommand(new FlashcardContainsTagPredicate(tags), tagKeywords);
    }

}
