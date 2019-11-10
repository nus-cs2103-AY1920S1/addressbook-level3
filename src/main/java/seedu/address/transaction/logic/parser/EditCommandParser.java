package seedu.address.transaction.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.transaction.model.transaction.Transaction.DATE_TIME_FORMATTER;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_INVALID_EDIT_COMMAND_FORMAT;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_NO_ZERO_ALLOWED;
import static seedu.address.util.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.util.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.util.CliSyntax.PREFIX_DATETIME;
import static seedu.address.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.util.CliSyntax.PREFIX_PERSON;

import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.person.exceptions.PersonNotFoundException;
import seedu.address.transaction.logic.commands.EditCommand;
import seedu.address.transaction.logic.parser.exception.ParseException;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.transaction.ui.TransactionMessages;
import seedu.address.util.ArgumentMultimap;
import seedu.address.util.ArgumentTokenizer;
import seedu.address.util.Prefix;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements CommandParserWithPersonModel {
    private static final double MAX_AMOUNT_ACCEPTED = 999999.99;
    private static final double MIN_AMOUNT_ACCEPTED = -999999.99;
    private static final double ZERO = 0.0;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns a EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args, CheckAndGetPersonByNameModel personModel)
            throws ParseException, NoSuchPersonException {
        requireNonNull(personModel);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_DESCRIPTION,
                        PREFIX_CATEGORY, PREFIX_AMOUNT, PREFIX_PERSON);

        int index;
        try {
            index = Integer.parseInt(argMultimap.getPreamble());
        } catch (Exception e) {
            logger.info("Wrong edit format for index.");
            throw new ParseException(MESSAGE_INVALID_EDIT_COMMAND_FORMAT);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_DATETIME, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_AMOUNT,
                PREFIX_PERSON)) {
            throw new ParseException(MESSAGE_INVALID_EDIT_COMMAND_FORMAT);
        }

        EditCommand.EditTransactionDescriptor editTransactionDescriptor = constructDescriptor(argMultimap, personModel);
        return new EditCommand(index, editTransactionDescriptor);
    }

    /**
     * Returns false if all of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

    /**
     * Constructs a {@code EditCommand.EditTransactionDescriptor} based on the {@ArgumentMultimap}
     * @param argMultimap ArgumentMultimap
     * @param personModel CheckAndGetPersonByNameModel
     * @return EditTransactionDescriptor
     * @throws ParseException If wrong user input format
     * @throws NoSuchPersonException If the inputted person is not in the personModel
     */
    private EditCommand.EditTransactionDescriptor constructDescriptor(ArgumentMultimap argMultimap,
                                                                      CheckAndGetPersonByNameModel personModel)
            throws ParseException, NoSuchPersonException {
        EditCommand.EditTransactionDescriptor editTransactionDescriptor = new EditCommand.EditTransactionDescriptor();
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editTransactionDescriptor.setDate(argMultimap.getValue(PREFIX_DATETIME).get());
            try {
                LocalDate.parse(argMultimap.getValue(PREFIX_DATETIME).get(), DATE_TIME_FORMATTER);
            } catch (Exception e) {
                logger.info("Wrong date format: " + argMultimap.getValue(PREFIX_DATETIME).get());
                throw new ParseException(TransactionMessages.MESSAGE_WRONG_DATE_FORMAT);
            }
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editTransactionDescriptor.setDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editTransactionDescriptor.setCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            try {
                double amount = Double.parseDouble(argMultimap.getValue(PREFIX_AMOUNT).get());
                if (amount > MAX_AMOUNT_ACCEPTED) {
                    throw new ParseException(TransactionMessages.MESSAGE_AMOUNT_TOO_LARGE);
                } else if (amount < MIN_AMOUNT_ACCEPTED) {
                    throw new ParseException(TransactionMessages.MESSAGE_AMOUNT_TOO_SMALL);
                } else if (amount == ZERO) {
                    throw new ParseException(MESSAGE_NO_ZERO_ALLOWED);
                }
                editTransactionDescriptor.setAmount(amount);
            } catch (NumberFormatException e) {
                logger.info("Wrong amount format: " + argMultimap.getValue(PREFIX_AMOUNT).get());
                throw new ParseException(TransactionMessages.MESSAGE_WRONG_AMOUNT_FORMAT);
            }
        }
        if (argMultimap.getValue(PREFIX_PERSON).isPresent()) {
            try {
                personModel.getPersonByName(argMultimap.getValue(PREFIX_PERSON).get());
            } catch (PersonNotFoundException e) {
                logger.info("Person not found in AddressBook: " + argMultimap.getValue(PREFIX_PERSON).get());
                throw new NoSuchPersonException(TransactionMessages.MESSAGE_NO_SUCH_PERSON);
            }
            editTransactionDescriptor.setName(argMultimap.getValue(PREFIX_PERSON).get());
        }
        return editTransactionDescriptor;
    }
}
