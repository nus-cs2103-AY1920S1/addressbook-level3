package seedu.ichifund.logic.parser.loan;

import static seedu.ichifund.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.loan.AddLoanCommand;
import seedu.ichifund.logic.commands.loan.PayLoanCommand;
import seedu.ichifund.logic.parser.FeatureParser;
import seedu.ichifund.logic.parser.exceptions.ParseException;

/**
 * Passes user input to the appropriate Parser for commands related to the loan feature.
 */
public class LoanFeatureParser implements FeatureParser {

    private final int tabIndex = 3;

    @Override
    public String getTabSwitchCommandWord() {
        return "loan";
    }

    @Override
    public int getTabIndex() {
        return tabIndex;
    }

    @Override
    public Command parseCommand(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case AddLoanCommand.COMMAND_WORD:
            return new AddLoanCommandParser().parse(arguments);
        case PayLoanCommand.COMMAND_WORD:
            return new PayLoanCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
