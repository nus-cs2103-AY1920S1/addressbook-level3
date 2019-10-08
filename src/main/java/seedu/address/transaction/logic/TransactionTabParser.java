package seedu.address.transaction.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.address.person.logic.commands.AddCommand;
import seedu.address.person.model.Model;
import seedu.address.transaction.commands.Command;
import seedu.address.transaction.commands.DeleteCommand;
import seedu.address.transaction.commands.EditCommand;
import seedu.address.transaction.commands.SortCommand;
import seedu.address.transaction.logic.exception.ParseException;
import seedu.address.transaction.ui.TransactionMessages;

public class TransactionTabParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");


    public Command parseCommand(String userInput, int transactionListSize,
                                Model personModel) throws Exception {
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

        /*case PersonCommand.COMMAND_WORD:
            return new PersonCommandParser().parse(arguments);*/

        default:
            throw new ParseException(TransactionMessages.NO_SUCH_COMMAND);

        }
    }
}
