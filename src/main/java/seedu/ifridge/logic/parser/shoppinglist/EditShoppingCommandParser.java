package seedu.ifridge.logic.parser.shoppinglist;

import static java.util.Objects.requireNonNull;
import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ifridge.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.ifridge.commons.core.index.Index;
import seedu.ifridge.logic.commands.shoppinglist.EditShoppingCommand;
import seedu.ifridge.logic.commands.shoppinglist.EditShoppingCommand.EditShoppingItemDescriptor;
import seedu.ifridge.logic.parser.ArgumentMultimap;
import seedu.ifridge.logic.parser.ArgumentTokenizer;
import seedu.ifridge.logic.parser.Parser;
import seedu.ifridge.logic.parser.ParserUtil;
import seedu.ifridge.logic.parser.exceptions.ParseException;

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditShoppingCommand.MESSAGE_USAGE), pe);
        }

        EditShoppingItemDescriptor editShoppingItemDescriptor = new EditShoppingItemDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editShoppingItemDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editShoppingItemDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }

        if (!editShoppingItemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditShoppingCommand.MESSAGE_NOT_EDITED);
        }

        return new EditShoppingCommand(index, editShoppingItemDescriptor);
    }
}
