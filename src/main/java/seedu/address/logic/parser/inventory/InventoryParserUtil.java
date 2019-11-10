package seedu.address.logic.parser.inventory;

import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.inventory.Inventory;

import seedu.address.model.inventory.Name;

import static java.util.Objects.requireNonNull;

public abstract class InventoryParserUtil {

    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }
}
