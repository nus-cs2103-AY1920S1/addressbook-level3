package seedu.address.logic.parser.statistics;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.ArrayList;
import java.util.List;

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
     * and returns an GetStatisticsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public GetStatisticsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_DIFFICULTY);

        List<Subject> subjects = new ArrayList<>();
        for (String subject : argMultimap.getAllValues(PREFIX_SUBJECT)) {
            subjects.add(ParserUtil.parseSubject(subject));
        }

        Difficulty difficulty = ParserUtil.parseDifficulty(argMultimap.getValue(PREFIX_DIFFICULTY).get());

        QuizResultFilter quizResultFilter = new QuizResultFilter(subjects, difficulty);

        return new GetStatisticsCommand(quizResultFilter);
    }
}
