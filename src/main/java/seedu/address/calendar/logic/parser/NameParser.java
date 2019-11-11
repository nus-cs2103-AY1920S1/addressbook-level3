package seedu.address.calendar.logic.parser;

import seedu.address.calendar.model.event.Name;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;

/**
 * Parses a name.
 */
public class NameParser {
    /**
     * Parses name. Name must be present and cannot be longer than 100 characters.
     *
     * @param nameInput The specified name
     * @return An {@code Optional} name
     * @throws ParseException if the specified name is not present or exceeds the character limit
     */
    Optional<Name> parse(Optional<String> nameInput) throws ParseException {
        if (nameInput.isEmpty() || !Name.isValidNameString(nameInput.get())) {
            throw new ParseException(Name.MESSAGE_CONSTRAINT);
        }

        assert nameInput.isEmpty() : "Name should always be specified";
        return Optional.of(new Name(nameInput.get()));
    }
}
