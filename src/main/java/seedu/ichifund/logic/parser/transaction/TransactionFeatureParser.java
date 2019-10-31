package seedu.ichifund.logic.parser.transaction;

import static seedu.ichifund.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.transaction.AddTransactionCommand;
import seedu.ichifund.logic.commands.transaction.DeleteTransactionCommand;
import seedu.ichifund.logic.commands.transaction.EditTransactionCommand;
import seedu.ichifund.logic.commands.transaction.FilterTransactionCommand;
import seedu.ichifund.logic.parser.FeatureParser;
import seedu.ichifund.logic.parser.exceptions.ParseException;

/**
 * Passes user input to the appropriate Parser for commands related to the transaction feature.
 */
public class TransactionFeatureParser implements FeatureParser {

    private final int tabIndex = 0;

    @Override
    public String getTabSwitchCommandWord() {
        return "tx";
    }

    @Override
    public int getTabIndex() {
        return tabIndex;
    }

    @Override
    public Command parseCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case AddTransactionCommand.COMMAND_WORD:
            return new AddTransactionCommandParser().parse(arguments);

        case FilterTransactionCommand.COMMAND_WORD:
            return new FilterTransactionCommandParser().parse(arguments);

        case DeleteTransactionCommand.COMMAND_WORD:
            return new DeleteTransactionCommandParser().parse(arguments);

        case EditTransactionCommand.COMMAND_WORD:
            return new EditTransactionCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
