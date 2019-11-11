package seedu.address.inventory.logic.parser;

import static seedu.address.util.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.util.CliSyntax.PREFIX_COST;
import static seedu.address.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.util.CliSyntax.PREFIX_PRICE;
import static seedu.address.util.CliSyntax.PREFIX_QUANTITY;

import seedu.address.inventory.logic.commands.EditCommand;
import seedu.address.inventory.logic.parser.exception.InvalidNumberException;
import seedu.address.inventory.logic.parser.exception.ParseException;
import seedu.address.inventory.ui.InventoryMessages;
import seedu.address.util.ArgumentMultimap;
import seedu.address.util.ArgumentTokenizer;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns a EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException, InvalidNumberException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_CATEGORY, PREFIX_QUANTITY, PREFIX_COST,
                        PREFIX_PRICE);
        int index;
        try {
            index = Integer.parseInt(argMultimap.getPreamble());
        } catch (Exception e) {
            throw new ParseException(InventoryMessages.MESSAGE_INVALID_EDIT_COMMAND_FORMAT);
        }

        EditCommand.EditItemDescriptor editItemDescriptor = new EditCommand.EditItemDescriptor();

        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editItemDescriptor.setDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editItemDescriptor.setCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        }
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            try {
                Integer quantity = Integer.parseInt(argMultimap.getValue(PREFIX_QUANTITY).get());
                if (quantity < 0) {
                    throw new ParseException(InventoryMessages.MESSAGE_NEGATIVE_NUMBER);
                } else if (quantity > 9999) {
                    throw new InvalidNumberException(InventoryMessages.MESSAGE_NUMBER_TOO_LARGE);
                } else {
                    editItemDescriptor.setQuantity(quantity);
                }
            } catch (NumberFormatException e) {
                throw new ParseException(InventoryMessages.MESSAGE_NOT_A_NUMBER);
            }
        }
        if (argMultimap.getValue(PREFIX_COST).isPresent()) {
            try {
                Double cost = Double.parseDouble(argMultimap.getValue(PREFIX_COST).get());
                if (cost < 0) {
                    throw new ParseException(InventoryMessages.MESSAGE_NEGATIVE_NUMBER);
                } else if (cost > 9999) {
                    throw new InvalidNumberException(InventoryMessages.MESSAGE_NUMBER_TOO_LARGE);
                } else {
                    editItemDescriptor.setCost(cost);
                }
            } catch (NumberFormatException e) {
                throw new ParseException(InventoryMessages.MESSAGE_NOT_A_NUMBER);
            }
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            try {
                Double price = Double.parseDouble(argMultimap.getValue(PREFIX_PRICE).get());
                if (price < 0) {
                    throw new ParseException(InventoryMessages.MESSAGE_NEGATIVE_NUMBER);
                } else if (price > 9999) {
                    throw new InvalidNumberException(InventoryMessages.MESSAGE_NUMBER_TOO_LARGE);
                } else {
                    editItemDescriptor.setPrice(price);
                }
            } catch (NumberFormatException e) {
                throw new ParseException(InventoryMessages.MESSAGE_NOT_A_NUMBER);
            }
        }

        if (!editItemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(InventoryMessages.MESSAGE_NOT_EDITED);
        }
        return new EditCommand(index, editItemDescriptor);
    }
}
