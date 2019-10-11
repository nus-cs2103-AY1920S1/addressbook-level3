package seedu.address.logic.parser.quiz;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.stream.Stream;

import seedu.address.logic.commands.quiz.QuizModeCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Subject;

/**
 * Parses input arguments and creates a new QuizModeCommand object.
 */
public class QuizModeCommandParser implements Parser<QuizModeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the QuizModeCommand
     * and returns an QuizModeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public QuizModeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NUMBER, PREFIX_DIFFICULTY, PREFIX_SUBJECT);

        int numOfQuestions;

        if (!arePrefixesPresent(argMultimap, PREFIX_DIFFICULTY, PREFIX_SUBJECT)
                || (!argMultimap.getPreamble().isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, QuizModeCommand.MESSAGE_USAGE));
        }

        numOfQuestions = ParserUtil.parseNumber(argMultimap.getValue(PREFIX_NUMBER).get());
        Subject subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get());
        Difficulty difficulty = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get());
        return new QuizModeCommand(numOfQuestions, subject, difficulty);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
