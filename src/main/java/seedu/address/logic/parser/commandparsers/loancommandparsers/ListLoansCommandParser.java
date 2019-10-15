package seedu.address.logic.parser.commandparsers.loancommandparsers;

import seedu.address.logic.commands.loancommands.ListLoansCommand;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListLoansCommandParser implements CommandParser<ListLoansCommand> {
    @Override
    public String name() {
        return ListLoansCommand.COMMAND_WORD;
    }

    @Override
    public ListLoansCommand parse(String args) {
        return new ListLoansCommand();
    }
}
