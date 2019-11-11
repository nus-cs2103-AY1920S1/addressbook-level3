package seedu.address.transaction.logic.parser;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.logic.commands.AddCommand;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.transaction.logic.commands.BackCommand;
import seedu.address.transaction.logic.commands.Command;
import seedu.address.transaction.logic.commands.DeleteCommand;
import seedu.address.transaction.logic.commands.EditCommand;
import seedu.address.transaction.logic.commands.FindCommand;
import seedu.address.transaction.logic.commands.SortCommand;
import seedu.address.transaction.logic.commands.exception.NoSuchSortException;
import seedu.address.transaction.logic.commands.exception.NotANumberException;
import seedu.address.transaction.logic.parser.exception.ParseException;
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
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @param personModel Model of person package
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format.
     * @throws NoSuchPersonException if the user inputs a person not found in data base.
     * @throws NotANumberException if the user input does not conform the expected format for delete.
     * @throws NoSuchSortException if the user input does not conform the expected format for sort.
     * */
    public Command parseCommand(String userInput, CheckAndGetPersonByNameModel personModel)
            throws ParseException, NoSuchPersonException, NotANumberException, NoSuchSortException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            logger.info("The user input does not have the correct command format that can be parsed by"
                    + " Transaction Tab Parser.");
            throw new ParseException(TransactionMessages.MESSAGE_NO_COMMAND);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments, personModel);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments, personModel);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments, personModel);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case BackCommand.COMMAND_WORD:
            return new BackCommand();

        default:
            logger.info("There is no command word found in input (Transaction Tab Parser)");
            throw new ParseException(TransactionMessages.MESSAGE_NO_COMMAND);

        }
    }
}
