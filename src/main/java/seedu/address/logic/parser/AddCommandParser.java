package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BLOODSUGAR_CONCENTRATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BMI_HEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BMI_WEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECORDTYPE;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendar.DateTime;
import seedu.address.model.record.BloodSugar;
import seedu.address.model.record.Bmi;
import seedu.address.model.record.Concentration;
import seedu.address.model.record.Height;
import seedu.address.model.record.RecordType;
import seedu.address.model.record.Weight;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an AddCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RECORDTYPE);

        if (!arePrefixesPresent(argMultimap, PREFIX_RECORDTYPE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        RecordType rt = ParserUtil.parseRecordType(argMultimap.getValue(PREFIX_RECORDTYPE).get());
        LocalDate ld = LocalDate.of(1970, Month.JANUARY, 1);
        LocalTime lt = LocalTime.of(8, 0, 0);
        DateTime dateTime = new DateTime(ld, lt);

        switch(rt) {
        case BLOODSUGAR:
            argMultimap = checkAllOtherPrefixes(argMultimap, PREFIX_BLOODSUGAR_CONCENTRATION, PREFIX_DATETIME);
            Concentration concentration = ParserUtil.parseConcentration(
                argMultimap.getValue(PREFIX_BLOODSUGAR_CONCENTRATION).get()
            );
            //dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
            BloodSugar bloodSugar = new BloodSugar(concentration, dateTime);
            return new AddCommand(bloodSugar);

        case BMI:
            argMultimap = checkAllOtherPrefixes(argMultimap, PREFIX_BMI_HEIGHT, PREFIX_BMI_WEIGHT, PREFIX_DATETIME);
            Height height = ParserUtil.parseHeight(argMultimap.getValue(PREFIX_BMI_HEIGHT).get());
            Weight weight = ParserUtil.parseWeight(argMultimap.getValue(PREFIX_BMI_WEIGHT).get());
            //dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
            Bmi bmi = new Bmi(height, weight, dateTime);
            return new AddCommand(bmi);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Returns a multimap of new new prefixes.
     */
    private ArgumentMultimap checkAllOtherPrefixes(ArgumentMultimap argMultimap, Prefix... prefixes)
        throws ParseException {
        String s = argMultimap.getValue(PREFIX_RECORDTYPE).toString();
        ArgumentMultimap a = ArgumentTokenizer.tokenize(s, prefixes);

        if (!arePrefixesPresent(a, prefixes) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        return a;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
