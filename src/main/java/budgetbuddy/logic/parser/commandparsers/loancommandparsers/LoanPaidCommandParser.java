package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.commands.loancommands.LoanPaidCommand;
import budgetbuddy.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LoanPaidCommand object.
 */
public class LoanPaidCommandParser extends MultiLoanCommandParser {
    @Override
    public String name() {
        return LoanPaidCommand.COMMAND_WORD;
    }

    @Override
    public LoanPaidCommand parse(String args) throws ParseException {
        try {
            parseMultiLoanArgs(args);
            return new LoanPaidCommand(personLoanIndexPairs, personIndices);
        } catch (ParseException | CommandException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanPaidCommand.MESSAGE_USAGE), e);
        }
    }
}
