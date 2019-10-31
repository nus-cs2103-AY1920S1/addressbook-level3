package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BODY_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAUSE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_JOINED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_ADMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_DEATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESIGNATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FRIDGE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORGANS_FOR_DONATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELIGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.StatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author dalisc

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class StatsCommandParser implements Parser<StatsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new StatsCommand();
        } else {

            ArgumentMultimap argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_WEEK, PREFIX_MONTH, PREFIX_YEAR, PREFIX_PHONE_NUMBER,
                            PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                            PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                            PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                            PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                            PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                            PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);

            boolean areAnyPrefixesPresent = isExactlyOnePrefixPresent(argMultimap,
                    PREFIX_WEEK, PREFIX_MONTH, PREFIX_YEAR);

            if (!areAnyPrefixesPresent || !argMultimap.getPreamble().isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatsCommand.MESSAGE_USAGE));
            }

            Prefix presentPrefix = (Prefix) Stream.of(PREFIX_WEEK, PREFIX_MONTH, PREFIX_YEAR)
                    .filter(prefix -> argMultimap.getValue(prefix).isPresent())
                    .toArray()[0];

            String timeFrame = presentPrefix.toString().substring(1);

            try {
                if (presentPrefix.equals(PREFIX_MONTH)) {
                    Date date = new SimpleDateFormat("MM/yyyy")
                        .parse(argMultimap.getValue(presentPrefix).get());
                    return new StatsCommand(date, timeFrame);
                } else if (presentPrefix.equals(PREFIX_YEAR)) {
                    Date date = new SimpleDateFormat("yyyy").parse(argMultimap.getValue(presentPrefix).get());
                    return new StatsCommand(date, timeFrame);
                } else {
                    Date date = new SimpleDateFormat("dd/MM/yyyy")
                          .parse(argMultimap.getValue(presentPrefix).get());
                    return new StatsCommand(date, timeFrame);
                }
            } catch (Exception e) {
                throw new ParseException(Messages.MESSAGE_INVALID_DATE_FORMAT);
            }
        }


    }

    /**
     * Returns true if any of the prefixes contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isExactlyOnePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        if (Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent())) {
            int match = 0;
            for (Prefix prefix: prefixes) {
                if (argumentMultimap.getValue(prefix).isPresent()) {
                    match++;
                }
            }

            return match == 1;

        } else {
            return false;
        }
    }
}
