package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddIncomeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.claim.Amount;
import seedu.address.model.claim.Description;
import seedu.address.model.commonvariables.Date;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.commonvariables.Phone;
import seedu.address.model.income.Income;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddIncomeCommandParser implements Parser<AddIncomeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddIncomeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_CASH, PREFIX_DATE, PREFIX_NAME,
                        PREFIX_PHONE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_CASH, PREFIX_DATE, PREFIX_NAME, PREFIX_PHONE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddIncomeCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_CASH).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Income income = new Income(description, amount, date, name, phone, tagList);

        return new AddIncomeCommand(income);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
