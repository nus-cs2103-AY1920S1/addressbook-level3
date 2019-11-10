package seedu.address.transaction.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.util.CliSyntax.PREFIX_PERSON;

import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.person.Person;
import seedu.address.person.model.person.exceptions.PersonNotFoundException;
import seedu.address.transaction.logic.commands.DeleteCommand;
import seedu.address.transaction.logic.commands.DeleteIndexCommand;
import seedu.address.transaction.logic.commands.DeleteNameCommand;
import seedu.address.transaction.logic.commands.exception.NotANumberException;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.transaction.ui.TransactionMessages;
import seedu.address.util.ArgumentMultimap;
import seedu.address.util.ArgumentTokenizer;
import seedu.address.util.Prefix;

/**
 * Parses input arguments and creates a new DeleteIndexCommand object
 */
public class DeleteCommandParser implements CommandParserWithPersonModel {
    private final Logger logger = LogsCenter.getLogger(getClass());


    /**
     * Parses the given {@code String} of arguments in the context of the DeleteIndexCommand
     * and returns a DeleteIndexCommand object for execution.
     * @throws NotANumberException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String userInput, CheckAndGetPersonByNameModel personModel) throws NotANumberException,
            NoSuchPersonException {
        requireNonNull(personModel);
        if (userInput.length() > 1 && userInput.substring(1).matches("-?(0|[1-9]\\d*)")) {
            int index = Integer.parseInt(userInput.substring(1));
            DeleteIndexCommand deleteIndexCommand = new DeleteIndexCommand(index);
            return deleteIndexCommand;
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_PERSON);
        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON) || !argMultimap.getPreamble().isEmpty()) {
            logger.info("Delete is not followed by an index or prefix p/.");
            throw new NotANumberException(TransactionMessages.MESSAGE_INVALID_DELETE_COMMAND_FORMAT);
        }

        try {
            Person person = personModel.getPersonByName(argMultimap.getValue(PREFIX_PERSON).get());
            DeleteNameCommand deleteNameCommand = new DeleteNameCommand(person);
            return deleteNameCommand;
        } catch (PersonNotFoundException e) {
            logger.info("The person inputted is not in the AddressBook: "
                    + argMultimap.getValue(PREFIX_PERSON).get());
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
