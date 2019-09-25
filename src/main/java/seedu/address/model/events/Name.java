package seedu.address.model.events;

import static java.util.Objects.requireNonNull;

public class Name {

    public final String name;

    public Name(String name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
