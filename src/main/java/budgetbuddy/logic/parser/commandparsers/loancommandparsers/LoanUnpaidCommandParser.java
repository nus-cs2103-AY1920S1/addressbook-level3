package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.commands.loancommands.LoanUnpaidCommand;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LoanUnpaidCommand object.
 */
public class LoanUnpaidCommandParser extends MultiLoanCommandParser {
    @Override
    public String name() {
        return LoanUnpaidCommand.COMMAND_WORD;
    }

    @Override
    public LoanUnpaidCommand parse(String args) throws ParseException {
        try {
            parseMultiLoanArgs(args);
            return new LoanUnpaidCommand(personLoanIndexPairs, personIndices);
        } catch (ParseException | CommandException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanUnpaidCommand.COMMAND_WORD), e);
        }
    }
}
