package seedu.address.logic.parser.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.statistics.GetQnsCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.QuizResultFilter;

/**
 * Parses input arguments and creates a new GetQnsCommand object.
 */
public class GetQnsCommandParser implements Parser<GetQnsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetQnsCommand
     * and returns a GetQnsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public GetQnsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        QuizResultFilter quizResultFilter;
        boolean getCorrectQns;

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);
        List<Subject> subjects = new ArrayList<>();

        for (String subject : argMultimap.getAllValues(PREFIX_SUBJECT)) {
            subjects.add(ParserUtil.parseSubject(subject));
        }

        if (!(args.contains("-c") ^ args.contains("-i"))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GetQnsCommand.MESSAGE_USAGE));
        }

        getCorrectQns = args.contains("-c");
        quizResultFilter = new QuizResultFilter(subjects, getCorrectQns);

        return new GetQnsCommand(quizResultFilter, returnMessage(getCorrectQns, subjects));
    }

    private String returnMessage(boolean getCorrectQns, List<Subject> subjects) {
        String returnMessage;
        if (!subjects.isEmpty()) {
            returnMessage = "Here are the " + (getCorrectQns ? "correct" : "incorrect")
                    + " questions for " + subjects + ":";
        } else {
            returnMessage = "Here are the " + (getCorrectQns ? "correct" : "incorrect")
                    + " questions:";
        }
        return returnMessage;
    }

}
