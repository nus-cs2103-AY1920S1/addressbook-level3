package seedu.address.logic.parser.inventory;

import seedu.address.logic.commands.inventory.EnterInventoryCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Placeholder javadoc.
 */
public class EnterInventoryParser implements Parser<EnterInventoryCommand> {


    @Override
    public EnterInventoryCommand parse(String userInput) throws ParseException {
        return new EnterInventoryCommand();
    }
}
