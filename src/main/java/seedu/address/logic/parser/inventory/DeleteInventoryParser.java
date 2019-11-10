package seedu.address.logic.parser.inventory;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.inventory.DeleteInventoryCommand;
import seedu.address.logic.commands.itinerary.days.EnterDayCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This class's execute function is called whenever an item is deleted from the inventory list
 */
public class DeleteInventoryParser implements Parser<DeleteInventoryCommand> {

    @Override
    public DeleteInventoryCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteInventoryCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteInventoryCommand.MESSAGE_USAGE));
        }
    }
}
