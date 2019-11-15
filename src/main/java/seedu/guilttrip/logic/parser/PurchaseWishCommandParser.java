package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.guilttrip.commons.core.index.Index;
import seedu.guilttrip.logic.commands.PurchaseWishCommand;
import seedu.guilttrip.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments into a PurchaseWishCommand object.
 */
public class PurchaseWishCommandParser implements Parser<PurchaseWishCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PurchaseWishCommand
     * and returns a PurchaseWishCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PurchaseWishCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new PurchaseWishCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PurchaseWishCommand.MESSAGE_USAGE), pe);
        }
    }

}
