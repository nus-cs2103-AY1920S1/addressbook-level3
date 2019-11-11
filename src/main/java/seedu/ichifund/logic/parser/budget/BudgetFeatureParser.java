package seedu.ichifund.logic.parser.budget;

import static seedu.ichifund.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.budget.AddBudgetCommand;
import seedu.ichifund.logic.commands.budget.DeleteBudgetCommand;
import seedu.ichifund.logic.commands.budget.FindBudgetCommand;
import seedu.ichifund.logic.parser.FeatureParser;
import seedu.ichifund.logic.parser.exceptions.ParseException;

/**
 * Passes user input to the appropriate Parser for commands related to the budget feature.
 */
public class BudgetFeatureParser implements FeatureParser {

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

        case FindBudgetCommand.COMMAND_WORD:
            return new FindBudgetCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
