package seedu.address.calendar.model;

import static java.util.Objects.requireNonNull;

public class Name {
    private String name;

    public Name(String name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
