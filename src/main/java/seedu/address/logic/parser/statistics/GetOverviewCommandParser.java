package seedu.address.logic.parser.statistics;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.statistics.GetOverviewCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.quiz.QuizResultFilter;

/**
 * Parses input arguments and creates a new GetOverviewCommand object.
 */
public class GetOverviewCommandParser implements Parser<GetOverviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetOverviewCommand
     * and returns a GetOverviewCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public GetOverviewCommand parse(String args) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        QuizResultFilter quizResultFilter = new QuizResultFilter();

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        List<String> dates = new ArrayList<>();
        LocalDate startDate = null;
        LocalDate endDate = null;
        String timePeriod = "";

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            dates = argMultimap.getAllValues(PREFIX_DATE);
            if (dates.size() != 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_DATE_FORMAT,
                        GetOverviewCommand.MESSAGE_USAGE));
            }
            try {
                startDate = LocalDate.parse(dates.get(0), formatter);
                endDate = LocalDate.parse(dates.get(1), formatter);
            } catch (DateTimeParseException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_DATE_FORMAT,
                        GetOverviewCommand.MESSAGE_USAGE));
            }
            if (startDate.isAfter(endDate)) {
                LocalDate temp = startDate;
                startDate = endDate;
                endDate = temp;
            }
            quizResultFilter = new QuizResultFilter(startDate, endDate);
        }

        if (!dates.isEmpty()) {
            timePeriod = "\n(" + startDate.format(formatter) + " to " + endDate.format(formatter) + ")";
        }

        return new GetOverviewCommand(quizResultFilter, timePeriod);
    }
}
