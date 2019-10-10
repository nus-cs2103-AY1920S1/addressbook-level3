package seedu.address.logic.parser.quiz;

import seedu.address.logic.commands.quiz.QuizModeCommand;

import seedu.address.logic.parser.*;

import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Subject;

import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new QuizModeCommand object.
 */
public class QuizModeCommandParser implements Parser<QuizModeCommand> {
    public QuizModeCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NUMBER, PREFIX_DIFFICULTY, PREFIX_SUBJECT);

        int numOfQuestions;

        if(!arePrefixesPresent(argMultimap, PREFIX_DIFFICULTY, PREFIX_SUBJECT)
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
