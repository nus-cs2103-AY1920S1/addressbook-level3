package seedu.address.transaction.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.person.logic.commands.AddCommand;
import seedu.address.person.model.Model;
import seedu.address.transaction.commands.Command;
import seedu.address.transaction.commands.DeleteCommand;
import seedu.address.transaction.commands.EditCommand;
import seedu.address.transaction.commands.FindCommand;
import seedu.address.transaction.commands.SortCommand;
import seedu.address.transaction.logic.exception.NoSuchSortException;
import seedu.address.transaction.logic.exception.NotANumberException;
import seedu.address.transaction.logic.exception.ParseException;
import seedu.address.transaction.model.exception.NoSuchPersonException;
import seedu.address.transaction.ui.TransactionMessages;

/**
 * Parses user input.
 */
public class TransactionTabParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format.
     * @throws NoSuchPersonException if the user inputs a person not found in data base.
     * @throws NotANumberException if the user input does not conform the expected format for delete.
     * @throws NoSuchSortException if the user input does not conform the expected format for sort.
     * */
    public Command parseCommand(String userInput, int transactionListSize, Model personModel)
            throws ParseException, NoSuchPersonException, NotANumberException, NoSuchSortException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(TransactionMessages.MESSAGE_INVALID_ADD_COMMAND_FORMAT);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments, transactionListSize, personModel);


        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        default:
            throw new ParseException(TransactionMessages.MESSAGE_NO_SUCH_COMMAND);

        }
    }
}
