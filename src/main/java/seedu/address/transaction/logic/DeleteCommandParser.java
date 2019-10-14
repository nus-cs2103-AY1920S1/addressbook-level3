package seedu.address.transaction.logic;

import static seedu.address.transaction.logic.CliSyntax.PREFIX_PERSON;

import java.util.stream.Stream;

import seedu.address.person.model.Model;
import seedu.address.person.model.person.Person;
import seedu.address.person.model.person.exceptions.PersonNotFoundException;
import seedu.address.transaction.commands.DeleteCommand;
import seedu.address.transaction.commands.DeleteIndexCommand;
import seedu.address.transaction.commands.DeleteNameCommand;
import seedu.address.transaction.logic.exception.NotANumberException;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Parses input arguments and creates a new DeleteIndexCommand object
 */
public class DeleteCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteIndexCommand
     * and returns a DeleteIndexCommand object for execution.
     * @throws NotANumberException if the user input does not conform the expected format
     */
    public static DeleteCommand parse(String userInput, Model personModel) throws NotANumberException,
            NoSuchPersonException {

        if (userInput.length() > 1 && userInput.substring(1).matches("-?(0|[1-9]\\d*)")) {
            int index = Integer.parseInt(userInput.substring(1));
            DeleteIndexCommand deleteIndexCommand = new DeleteIndexCommand(index);
            return deleteIndexCommand;
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_PERSON);
        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON) || !argMultimap.getPreamble().isEmpty()) {
            throw new NotANumberException(TransactionMessages.MESSAGE_INVALID_DELETE_COMMAND_FORMAT);
        }

        try {
            Person person = personModel.getPersonByName(argMultimap.getValue(PREFIX_PERSON).get());
            DeleteNameCommand deleteNameCommand = new DeleteNameCommand(person);
            return deleteNameCommand;
        } catch (PersonNotFoundException e) {
            throw new NoSuchPersonException(TransactionMessages.MESSAGE_NO_SUCH_PERSON);
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
