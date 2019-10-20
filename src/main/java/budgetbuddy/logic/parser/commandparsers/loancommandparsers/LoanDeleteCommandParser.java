package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.commands.loancommands.LoanDeleteCommand;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LoanDeleteCommand object.
 */
public class LoanDeleteCommandParser extends MultiLoanCommandParser {
    @Override
    public String name() {
        return LoanDeleteCommand.COMMAND_WORD;
    }

    @Override
    public LoanDeleteCommand parse(String args) throws ParseException {
        try {
            parseMultiLoanArgs(args);
            return new LoanDeleteCommand(personLoanIndexPairs, personIndices);
        } catch (ParseException | CommandException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanDeleteCommand.MESSAGE_USAGE), e);
        }
    }
}
