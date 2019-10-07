package seedu.address.reimbursement.logic.parser;

import seedu.address.reimbursement.commands.Command;

public interface SortParser<T extends Command> {
    T parse(String userInput) throws Exception;
}
