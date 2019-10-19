package seedu.ichifund.logic.parser.budget;

import static seedu.ichifund.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.budget.AddBudgetCommand;
import seedu.ichifund.logic.commands.budget.DeleteBudgetCommand;
import seedu.ichifund.logic.parser.ParserManager;
import seedu.ichifund.logic.parser.exceptions.ParseException;

public class BudgetParserManager implements ParserManager {

    private final int tabIndex = 2;

    @Override
    public String getTabSwitchCommandWord() {
        return "budget";
    }

    @Override
    public int getTabIndex() {
        return tabIndex;
    }

    @Override
    public Command parseCommand(String commandWord, String arguments) throws ParseException {
        switch(commandWord) {
        case AddBudgetCommand.COMMAND_WORD:
            return new AddBudgetCommandParser().parse(arguments);

        case DeleteBudgetCommand.COMMAND_WORD:
            return new DeleteBudgetCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
