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
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        if (d.isPresent()) {
            difficulty = ParserUtil.parseDifficulty(d.get());
            quizResultFilter = new QuizResultFilter(subjects, difficulty);
        } else {
            quizResultFilter = new QuizResultFilter(subjects);
        }

        return new GetStatisticsCommand(quizResultFilter, returnMessage(d, subjects));
    }

    /**
     * Returns a string to be placed in result display.
     * @param d Denotes difficulty to include in result display.
     * @param s Denotes subjects to include in result display.
     * @return The message to be placed in result display.
     */
    private String returnMessage(Optional d, List<Subject> s) {
        String message = "";
        if (d.isPresent()) {
            message += "\n[" + d.get() + "]";
        }
        if (!s.isEmpty()) {
            message += "\n" + s.toString();
        }
        return message;
    }
}
