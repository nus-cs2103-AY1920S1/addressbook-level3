package seedu.address.logic.parser.inventory;

import seedu.address.logic.commands.inventory.AddInventoryCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.inventory.Name;

/**
 * Used to parse whether the user wants to add an item to the inventory list
 */
public class AddInventoryParser implements Parser<AddInventoryCommand> {


    @Override
    public AddInventoryCommand parse(String userInput) throws ParseException {
        return new AddInventoryCommand(InventoryParserUtil.parseName(userInput));
    }
}
