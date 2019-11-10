package seedu.address.transaction.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_AMOUNT_TOO_LARGE;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_AMOUNT_TOO_SMALL;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_NO_SUCH_PERSON;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_NO_ZERO_ALLOWED;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_WRONG_AMOUNT_FORMAT;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_WRONG_DATE_FORMAT;
import static seedu.address.util.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.util.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.util.CliSyntax.PREFIX_DATETIME;
import static seedu.address.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.util.CliSyntax.PREFIX_PERSON;

import java.time.format.DateTimeParseException;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.person.Person;
import seedu.address.person.model.person.exceptions.PersonNotFoundException;
import seedu.address.transaction.logic.commands.AddCommand;
import seedu.address.transaction.logic.parser.exception.ParseException;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.transaction.ui.TransactionMessages;
import seedu.address.util.ArgumentMultimap;
import seedu.address.util.ArgumentTokenizer;
import seedu.address.util.Prefix;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements CommandParserWithPersonModel {
    private static final double MAX_AMOUNT_ACCEPTED = 999999.99;
    private static final double MIN_AMOUNT_ACCEPTED = -999999.99;
    private static final double ZERO = 0.0;
    private final Logger logger = LogsCenter.getLogger(getClass());


    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     * @throws NoSuchPersonException if user inputs a transaction done by someone not in database
     */
    public AddCommand parse(String args, CheckAndGetPersonByNameModel personModel)
            throws ParseException, NoSuchPersonException {
        requireNonNull(personModel);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_DESCRIPTION,
                        PREFIX_CATEGORY, PREFIX_AMOUNT, PREFIX_PERSON);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATETIME, PREFIX_DESCRIPTION, PREFIX_CATEGORY,
                PREFIX_AMOUNT, PREFIX_PERSON) || !argMultimap.getPreamble().isEmpty()) {
            logger.info("Not all the required fields and their prefixes are present.");
            throw new ParseException(TransactionMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT);
        }

        String datetime = argMultimap.getValue(PREFIX_DATETIME).get();
        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
        String category = argMultimap.getValue(PREFIX_CATEGORY).get();
        String amountString = argMultimap.getValue(PREFIX_AMOUNT).get();
        try {
            double amount = Double.parseDouble(amountString);
            if (amount > MAX_AMOUNT_ACCEPTED) {
                throw new ParseException(MESSAGE_AMOUNT_TOO_LARGE);
            } else if (amount < MIN_AMOUNT_ACCEPTED) {
                throw new ParseException(MESSAGE_AMOUNT_TOO_SMALL);
            } else if (amount == ZERO) {
                throw new ParseException(MESSAGE_NO_ZERO_ALLOWED);
            }
            Person person = personModel.getPersonByName(argMultimap.getValue(PREFIX_PERSON).get());
            Transaction transaction = new Transaction(datetime, description, category, amount, person,
                    0, false);
            logger.info("Transaction to be added: " + transaction.toString());
            AddCommand addCommand = new AddCommand(transaction);
            return addCommand;
        } catch (PersonNotFoundException e) {
            throw new NoSuchPersonException(MESSAGE_NO_SUCH_PERSON);
        } catch (DateTimeParseException e) {
            throw new ParseException(MESSAGE_WRONG_DATE_FORMAT);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_WRONG_AMOUNT_FORMAT);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

}
