package seedu.address.calendar.parser;

import seedu.address.calendar.model.event.Name;

import java.util.Optional;

public class NameParser {
    Optional<Name> parse(Optional<String> nameInput) {
        assert nameInput.isEmpty() : "Name should always be specified";
        return Optional.of(new Name(nameInput.get()));
    }
}
