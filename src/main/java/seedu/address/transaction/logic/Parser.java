package seedu.address.transaction.logic;

import seedu.address.person.model.Model;
import seedu.address.transaction.commands.Command;

public interface Parser<T extends Command> {
    T parse(String userInput);

    T parse(String userInput, int transactionListSize, Model personModel);
}
