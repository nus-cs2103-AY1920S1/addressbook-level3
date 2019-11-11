package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.flashcard.logic.commands.QuizTagCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.tag.Tag;

/**
 * Parses input arguments to generate {@code ListCardByTagCommandParser}
 */
public class QuizTagCommandParser implements Parser<QuizTagCommand> {

    /**
     * Parses the string of arguments to be listed by tags.
     * @param args string containing the parameters for the target tags
     * @return new {@code ListCardByTagCommand}
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public QuizTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_DURATION);
        if (!arePrefixesPresent(argMultimap, PREFIX_TAG) || !argMultimap.getPreamble().isEmpty()) {
            FlashcardListParser.setQuizMode(false);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT
                    + QuizTagCommand.MESSAGE_USAGE));
        }


        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Integer duration;
        if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());
        } else {
            duration = null;
        }

        return new QuizTagCommand(tagList, duration);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
