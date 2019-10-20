package seedu.address.transaction.logic;

import static seedu.address.transaction.model.Transaction.DATE_TIME_FORMATTER;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_INVALID_EDIT_COMMAND_FORMAT;
//import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_NOT_EDITED;
import static seedu.address.util.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.util.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.util.CliSyntax.PREFIX_DATETIME;
import static seedu.address.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.util.CliSyntax.PREFIX_PERSON;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.Model;
import seedu.address.person.model.person.exceptions.PersonNotFoundException;
import seedu.address.transaction.commands.EditCommand;
import seedu.address.transaction.logic.exception.ParseException;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.transaction.ui.TransactionMessages;
import seedu.address.util.ArgumentMultimap;
import seedu.address.util.ArgumentTokenizer;
import seedu.address.util.Prefix;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements CommandParserWithPersonModel{

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns a EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args, Model personModel) throws ParseException, NoSuchPersonException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_DESCRIPTION,
                        PREFIX_CATEGORY, PREFIX_AMOUNT, PREFIX_PERSON);

        int index;
        try {
            index = Integer.parseInt(argMultimap.getPreamble());
        } catch (Exception e) {
            throw new ParseException(MESSAGE_INVALID_EDIT_COMMAND_FORMAT);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_DATETIME, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_AMOUNT,
                PREFIX_PERSON)) {
            throw new ParseException(MESSAGE_INVALID_EDIT_COMMAND_FORMAT);
        }

        EditCommand.EditTransactionDescriptor editPersonDescriptor = new EditCommand.EditTransactionDescriptor();
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editPersonDescriptor.setDate(argMultimap.getValue(PREFIX_DATETIME).get());
            try {
                LocalDate.parse(argMultimap.getValue(PREFIX_DATETIME).get(), DATE_TIME_FORMATTER);
            } catch (Exception e) {
                throw new ParseException(TransactionMessages.MESSAGE_WRONG_DATE_FORMAT);
            }
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editPersonDescriptor.setDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editPersonDescriptor.setCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            try {
                editPersonDescriptor.setAmount(Double.parseDouble(argMultimap.getValue(PREFIX_AMOUNT).get()));
            } catch (NumberFormatException e) {
                throw new ParseException(TransactionMessages.MESSAGE_WRONG_AMOUNT_FORMAT);
            }
        }
        if (argMultimap.getValue(PREFIX_PERSON).isPresent()) {
            try {
                personModel.getPersonByName(argMultimap.getValue(PREFIX_PERSON).get());
            } catch (PersonNotFoundException e) {
                throw new NoSuchPersonException(TransactionMessages.MESSAGE_NO_SUCH_PERSON);
            }
            editPersonDescriptor.setName(argMultimap.getValue(PREFIX_PERSON).get());
        }

        /*if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(MESSAGE_NOT_EDITED);
        }*/

        return new EditCommand(index, editPersonDescriptor);
    }

    /**
     * Returns false if all of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }
}
