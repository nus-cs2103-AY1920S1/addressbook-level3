package seedu.address.logic.composers;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class InstantComposer {

    private final DateTimeFormatter composer;

    public InstantComposer(DateTimeFormatter composer) {
        this.composer = composer;
    }

    public String compose(Instant instant) {
        return this.composer.format(instant);
    }
}
