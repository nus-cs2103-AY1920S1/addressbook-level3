package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.commands.loancommands.DeleteLoanCommand;
import budgetbuddy.logic.parser.exceptions.ParseException;

public class DeleteLoanCommandParser extends MultiLoanCommandParser {
    @Override
    public String name() {
        return DeleteLoanCommand.COMMAND_WORD;
    }

    @Override
    public DeleteLoanCommand parse(String args) throws ParseException {
        try {
            parseMultiLoanArgs(args);
            return new DeleteLoanCommand(personLoanIndexPairs, personIndices);
        } catch (ParseException | CommandException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLoanCommand.MESSAGE_USAGE), e);
        }
    }
}
