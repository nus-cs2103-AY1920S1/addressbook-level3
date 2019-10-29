package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEXES;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TrainingCommand;
import seedu.address.logic.commands.TrainingCommandAbsent;
import seedu.address.logic.commands.TrainingCommandPresent;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;

/**
 * Parses input arguments and creates a new TrainingCommand object.
 */
public class TrainingCommandParser implements Parser<TrainingCommand> {

    /**
     * Parses the input arguments and returns a TrainingCommand object.
     * @param args Parsed user arguments.
     * @return TrainingCommandPresent or TrainingCommandAbsent.
     * @throws ParseException Thrown when indexes are not present or unrecognised arguments found.
     */
    @Override
    public TrainingCommand parse(String args) throws ParseException {
        List<Index> indexList;
        AthletickDate date;
        boolean byAbsent = false;

        if (args.contains("-a")) {
            byAbsent = true;
            args = args.replaceAll("-a", "");
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_INDEXES);
        if (!arePrefixesPresent(argMultimap, PREFIX_INDEXES) || !argMultimap.getPreamble().isEmpty()) {
            if (byAbsent) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        TrainingCommandAbsent.MESSAGE_USAGE));
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        TrainingCommandPresent.MESSAGE_USAGE));
            }
        }

        indexList = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_INDEXES).get());
        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        } else {
            DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            date = ParserUtil.parseDate(dateFormat.format(new Date()));
        }
        if (byAbsent) {
            return new TrainingCommandAbsent(date, indexList);
        } else {
            return new TrainingCommandPresent(date, indexList);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
