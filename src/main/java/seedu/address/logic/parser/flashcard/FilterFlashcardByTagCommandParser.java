package seedu.address.logic.parser.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.flashcard.FilterFlashcardByTagCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.flashcard.FlashcardContainsTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FilterFlashcardByTagCommand object
 */
public class FilterFlashcardByTagCommandParser implements Parser<FilterFlashcardByTagCommand> {

    private static final Logger logger = LogsCenter.getLogger(FilterFlashcardByTagCommandParser.class);


    /**
     * Parses the given {@code String} of arguments in the context of the FilterFlashcardByTagCommand
     * and returns a FilterFlashcardByTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterFlashcardByTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterFlashcardByTagCommand.MESSAGE_USAGE));
        }

        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        if (tags.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterFlashcardByTagCommand.MESSAGE_USAGE));
        }
        ArrayList<String> tagKeywords = new ArrayList<>();
        for (Tag t : tags) {
            tagKeywords.add(t.toString());
        }

        logger.info("Parsing FilterFlashcardByTagCommand of args: " + args);

        return new FilterFlashcardByTagCommand(new FlashcardContainsTagPredicate(tags), tagKeywords);
    }

}
