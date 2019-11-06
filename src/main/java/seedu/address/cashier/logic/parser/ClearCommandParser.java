package seedu.address.cashier.logic.parser;

import seedu.address.cashier.logic.commands.ClearCommand;
import seedu.address.cashier.model.Model;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser {

    /**
     * Parses the given {@code String} of arguments in the context of the ClearCommand
     * and returns an ClearCommand object for execution.
     */
    public ClearCommand parse(String args, Model model,
                              seedu.address.person.model.CheckAndGetPersonByNameModel personModel) {
        return new ClearCommand();
    }
}
