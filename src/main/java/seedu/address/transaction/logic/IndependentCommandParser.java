package seedu.address.transaction.logic;

import seedu.address.transaction.commands.Command;

public interface IndependentCommandParser {

    Command parse(String args) throws Exception;
}
