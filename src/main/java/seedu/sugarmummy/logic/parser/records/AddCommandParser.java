package seedu.sugarmummy.logic.parser.records;

import static seedu.sugarmummy.commons.core.Messages.MESSAGE_ENSURE_ONLY_ONE_PREFIX_PLURAL;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_ENSURE_ONLY_ONE_PREFIX_SINGULAR;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sugarmummy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_BLOODSUGAR_CONCENTRATION;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_BMI_HEIGHT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_BMI_WEIGHT;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_RECORDTYPE;

import java.util.List;
import java.util.stream.Stream;

import seedu.sugarmummy.logic.commands.records.AddCommand;
import seedu.sugarmummy.logic.parser.ArgumentMultimap;
import seedu.sugarmummy.logic.parser.ArgumentTokenizer;
import seedu.sugarmummy.logic.parser.Parser;
import seedu.sugarmummy.logic.parser.ParserUtil;
import seedu.sugarmummy.logic.parser.Prefix;
import seedu.sugarmummy.logic.parser.exceptions.ParseException;
import seedu.sugarmummy.model.records.BloodSugar;
import seedu.sugarmummy.model.records.Bmi;
import seedu.sugarmummy.model.records.Concentration;
import seedu.sugarmummy.model.records.Height;
import seedu.sugarmummy.model.records.RecordType;
import seedu.sugarmummy.model.records.Weight;
import seedu.sugarmummy.model.time.DateTime;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given {@code
     * ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an AddCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RECORDTYPE, PREFIX_DATETIME, PREFIX_BLOODSUGAR_CONCENTRATION,
                        PREFIX_BMI_HEIGHT, PREFIX_BMI_WEIGHT);

        checkUniqueAndValidPrefix(argMultimap, PREFIX_RECORDTYPE, PREFIX_DATETIME);

        RecordType rt = ParserUtil.parseRecordType(argMultimap.getValue(PREFIX_RECORDTYPE).get());
        DateTime dateTime = ParserUtil.parsePastDateTime(argMultimap.getValue(PREFIX_DATETIME).get());

        switch (rt) {
        case BLOODSUGAR:
            checkUniqueAndValidPrefix(argMultimap, PREFIX_BLOODSUGAR_CONCENTRATION);
            Concentration concentration = ParserUtil.parseConcentration(
                    argMultimap.getValue(PREFIX_BLOODSUGAR_CONCENTRATION).get()
            );
            BloodSugar bloodSugar = new BloodSugar(concentration, dateTime);
            return new AddCommand(bloodSugar);

        case BMI:
            checkUniqueAndValidPrefix(argMultimap, PREFIX_BMI_HEIGHT, PREFIX_BMI_WEIGHT);
            Height height = ParserUtil.parseHeight(argMultimap.getValue(PREFIX_BMI_HEIGHT).get());
            Weight weight = ParserUtil.parseWeight(argMultimap.getValue(PREFIX_BMI_WEIGHT).get());
            Bmi bmi = new Bmi(height, weight, dateTime);
            return new AddCommand(bmi);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Throws {@code ParseException} if given list of {@code Prefixes} are not unique in given {@code ArgumentMultimap}.
     */
    private void checkUniqueAndValidPrefix(ArgumentMultimap argMultimap, Prefix... prefixes) throws ParseException {
        if (!arePrefixesPresent(argMultimap, prefixes)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        List<Prefix> nonUniquePrefixes = argMultimap.getNonUniquePrefixes(prefixes);

        if (!nonUniquePrefixes.isEmpty()) {
            throw new ParseException((nonUniquePrefixes.size() == 1
                ? MESSAGE_ENSURE_ONLY_ONE_PREFIX_SINGULAR
                : MESSAGE_ENSURE_ONLY_ONE_PREFIX_PLURAL) + nonUniquePrefixes);
        }
    }

}
