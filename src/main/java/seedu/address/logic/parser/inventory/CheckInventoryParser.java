package seedu.address.logic.parser.inventory;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.inventory.CheckInventoryCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This class's execute function is called whenever an item is deleted from the inventory list
 */
public class CheckInventoryParser implements Parser<CheckInventoryCommand> {

    @Override
    public CheckInventoryCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new CheckInventoryCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckInventoryCommand.MESSAGE_USAGE));
        }
    }
}
