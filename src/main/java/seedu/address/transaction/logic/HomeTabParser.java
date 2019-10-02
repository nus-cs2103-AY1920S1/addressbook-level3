package seedu.address.transaction.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import seedu.address.person.logic.commands.AddCommand;
import seedu.address.transaction.commands.Command;
import seedu.address.transaction.commands.DeleteCommand;
import seedu.address.transaction.commands.EditCommand;
import seedu.address.transaction.commands.PersonCommand;
import seedu.address.transaction.logic.exception.ParseException;
import seedu.address.transaction.ui.TransactionUi;

public class HomeTabParser {

    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    public Command parseCommand(String userInput, int transactionListSize) throws Exception {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(TransactionUi.MESSAGE_INVALID_ADDCOMMAND_FORMAT);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments, transactionListSize);


        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case PersonCommand.COMMAND_WORD:
            return new PersonCommandParser().parse(arguments);

        default:
            throw new ParseException(TransactionUi.NO_SUCH_COMMAND);

        }
    }
}
