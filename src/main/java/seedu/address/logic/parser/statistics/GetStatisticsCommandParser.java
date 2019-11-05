package seedu.address.logic.parser.statistics;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.statistics.GetStatisticsCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.QuizResultFilter;

/**
 * Parses input arguments and creates a new GetStatisticsCommand object.
 */
public class GetStatisticsCommandParser implements Parser<GetStatisticsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetStatisticsCommand
     * and returns a GetStatisticsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public GetStatisticsCommand parse(String args) throws ParseException {
        Difficulty difficulty;
        QuizResultFilter quizResultFilter;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_DIFFICULTY);

        List<Subject> subjects = new ArrayList<>();
        for (String subject : argMultimap.getAllValues(PREFIX_SUBJECT)) {
            subjects.add(ParserUtil.parseSubject(subject));
        }

        Optional<String> d = argMultimap.getValue(PREFIX_DIFFICULTY);

        if (argMultimap.getAllValues(PREFIX_DIFFICULTY).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GetStatisticsCommand.MESSAGE_USAGE));
        }

        if (d.isPresent()) {
            difficulty = ParserUtil.parseDifficulty(d.get());
            quizResultFilter = new QuizResultFilter(subjects, difficulty);
            return new GetStatisticsCommand(quizResultFilter, returnMessage(d.get(), subjects));
        } else {
            quizResultFilter = new QuizResultFilter(subjects);
            return new GetStatisticsCommand(quizResultFilter, returnMessage(subjects));
        }
    }

    /**
     * Returns a string listing some subjects.
     *
     * @param s the list of subjects to show
     * @return the shown string
     */
    private String returnMessage(List<Subject> s) {
        return !s.isEmpty() ? "\n" + s.toString() : "";
    }

    /**
     * Returns a string listing some subjects and a difficulty.
     *
     * @param d difficulty to show
     * @see #returnMessage(List)
     */
    private String returnMessage(String d, List<Subject> s) {
        return "\n[" + d + "]" + returnMessage(s);
    }
}
