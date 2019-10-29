package seedu.address.calendar.parser;

import seedu.address.calendar.model.event.Info;

import java.util.Optional;

public class InfoParser {
    Optional<Info> parse(Optional<String> infoInput) {
        return infoInput.isEmpty() ? Optional.empty() : Optional.of(new Info(infoInput.get()));
    }
}
