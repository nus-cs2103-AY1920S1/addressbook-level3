package seedu.address.logic.parser;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_FILTER_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        String[] nameKeywords = new String[10];
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FIRST_NAME, PREFIX_MIDDLE_NAME, PREFIX_LAST_NAME, PREFIX_PHONE,
                        PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION, PREFIX_STATUS,
                        PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_CAUSE_OF_DEATH,
                        PREFIX_BODY_DETAILS, PREFIX_NRIC, PREFIX_RELATIONSHIP, PREFIX_RELIGION,
                        PREFIX_NAME_NOK, PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION,
                        PREFIX_FRIDGE_ID, PREFIX_FLAG, PREFIX_EMPLOYMENT_STATUS,
                        PREFIX_NAME, PREFIX_TAG, PREFIX_EMAIL, PREFIX_ADDRESS);
        String flag = argMultimap.getValue(PREFIX_FLAG).orElse("");
        boolean areAnyPrefixesPresent;

        switch (flag) {
            case "w":
                areAnyPrefixesPresent = areAnyPrefixesPresent(argMultimap, PREFIX_FIRST_NAME, PREFIX_LAST_NAME,
                        PREFIX_PHONE, PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_JOINED, PREFIX_DESIGNATION,
                        PREFIX_EMPLOYMENT_STATUS);
                break;
            case "b":
                areAnyPrefixesPresent = areAnyPrefixesPresent(argMultimap, PREFIX_FIRST_NAME, PREFIX_LAST_NAME,
                        PREFIX_SEX, PREFIX_DATE_OF_BIRTH, PREFIX_DATE_OF_DEATH, PREFIX_DATE_OF_ADMISSION, PREFIX_NRIC,
                        PREFIX_CAUSE_OF_DEATH, PREFIX_RELATIONSHIP, PREFIX_RELIGION, PREFIX_NAME_NOK,
                        PREFIX_PHONE_NOK, PREFIX_ORGANS_FOR_DONATION, PREFIX_FRIDGE_ID);
                break;
            default:
                areAnyPrefixesPresent = areAnyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE,
                        PREFIX_EMAIL);
        }

        if (!areAnyPrefixesPresent || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_FILTER_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        return new FilterCommand(argMultimap, flag);
    }

    /**
     * Returns true if any of the prefixes contains non-empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
