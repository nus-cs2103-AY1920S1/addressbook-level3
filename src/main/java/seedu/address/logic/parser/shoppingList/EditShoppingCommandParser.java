package seedu.address.logic.parser.shoppingList;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.shoppingList.EditShoppingCommand;
import seedu.address.logic.commands.shoppingList.EditShoppingCommand.EditShoppingItemDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

//to add expiry date functionality later
/**
 * Parses input arguments and creates a new EditShoppingCommand object
 */
public class EditShoppingCommandParser implements Parser<EditShoppingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditShoppingCommand
     * and returns an EditShoppingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditShoppingCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditShoppingCommand.MESSAGE_USAGE), pe);
        }

        EditShoppingItemDescriptor editShoppingItemDescriptor = new EditShoppingItemDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editShoppingItemDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editShoppingItemDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }

        if (!editShoppingItemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditShoppingCommand(index, editShoppingItemDescriptor);
    }
}
