package seedu.address.transaction.logic;

import java.util.stream.Stream;
import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.person.model.person.exceptions.PersonNotFoundException;
import seedu.address.transaction.commands.AddCommand;
import seedu.address.transaction.logic.exception.ParseException;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.transaction.ui.TransactionMessages;

import static seedu.address.transaction.logic.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.transaction.logic.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.transaction.logic.CliSyntax.PREFIX_DATETIME;
import static seedu.address.transaction.logic.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.transaction.logic.CliSyntax.PREFIX_PERSON;

public class AddCommandParser {

    public static AddCommand parse(String args, int transactionListSize, Model personModel)
            throws Exception {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_AMOUNT, PREFIX_PERSON);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATETIME, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_AMOUNT, PREFIX_PERSON)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(TransactionMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT);
        }

        String datetime = argMultimap.getValue(PREFIX_DATETIME).get();
        String description = argMultimap.getValue(PREFIX_DESCRIPTION).get();
        String category = argMultimap.getValue(PREFIX_CATEGORY).get();
        String amountString = argMultimap.getValue(PREFIX_AMOUNT).get();
        double amount = Double.parseDouble(amountString);
        try {
            Person person = personModel.getPersonByName(argMultimap.getValue(PREFIX_PERSON).get());
            Transaction transaction = new Transaction(datetime, description, category, amount, person,
                    transactionListSize + 1, false);
            AddCommand addCommand = new AddCommand(transaction);
            return addCommand;
        } catch (PersonNotFoundException e) {
            System.out.println("invalid add command");
            throw new NoSuchPersonException(TransactionMessages.NO_SUCH_PERSON);
        } catch (Exception e) {
            throw new ParseException(TransactionMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT);
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

}
