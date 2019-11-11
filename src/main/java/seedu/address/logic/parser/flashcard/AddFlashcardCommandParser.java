package seedu.address.logic.parser.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TAG_LIMIT_EXCEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.flashcard.AddFlashcardCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Title;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new {@code AddFlashcardCommand} object
 */
public class AddFlashcardCommandParser implements Parser<AddFlashcardCommand> {

    private static final Logger logger = LogsCenter.getLogger(AddFlashcardCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddFlashcardCommand}
     * and returns a {@code AddFlashcardCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddFlashcardCommand parse(String args) throws ParseException {
        requireNonNull(args);

        logger.info("Parsing AddFlashcardCommand for arg: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_QUESTION, PREFIX_ANSWER,
                PREFIX_TITLE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_TITLE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFlashcardCommand.MESSAGE_USAGE));
        }

        Question question = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
        Answer answer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get());
        Title title = ParserUtil.parseFlashcardTitle(argMultimap.getValue(PREFIX_TITLE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        if (tagList.size() > 10) {
            throw new ParseException(MESSAGE_TAG_LIMIT_EXCEEDED);
        }

        Flashcard flashcard = new Flashcard(question, answer, title, tagList);

        return new AddFlashcardCommand(flashcard);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
