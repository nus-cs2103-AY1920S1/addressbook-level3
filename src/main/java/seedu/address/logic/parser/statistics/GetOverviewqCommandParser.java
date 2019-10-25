package seedu.address.logic.parser.statistics;

import static seedu.address.logic.commands.statistics.GetOverviewqCommand.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import seedu.address.logic.commands.statistics.GetOverviewqCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.quiz.QuizResultFilter;

/**
 * Parses input arguments and creates a new GetStatisticsCommand object.
 */
public class GetOverviewqCommandParser implements Parser<GetOverviewqCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GetStatisticsCommand
     * and returns an GetStatisticsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    public GetOverviewqCommand parse(String args) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        QuizResultFilter quizResultFilter = new QuizResultFilter();

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        List<String> dates = new ArrayList<>();
        List<Date> formattedDates = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            dates = argMultimap.getAllValues(PREFIX_DATE);
            for (String date : dates) {
                try {
                    Date d = formatter.parse(date);
                    formattedDates.add(d);
                } catch (java.text.ParseException e) {
                    throw new ParseException(String.format(MESSAGE_INVALID_DATE_FORMAT,
                            GetOverviewqCommand.MESSAGE_USAGE));
                }
            }
            quizResultFilter = new QuizResultFilter(formattedDates.get(0), formattedDates.get(1));
        }
        return new GetOverviewqCommand(quizResultFilter);
    }
}
