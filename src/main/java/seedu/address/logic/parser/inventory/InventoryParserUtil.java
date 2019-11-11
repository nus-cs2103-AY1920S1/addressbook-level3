package seedu.address.logic.parser.inventory;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.inventory.Name;

/**
 * Abstract Class has helper functions that can be used for parsing Inventory-related commands.
 */
public abstract class InventoryParserUtil {

    /**
     * Parses the name if it is valid
     * @param name Name to parse
     * @return Parsed Name
     * @throws ParseException
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }
}
