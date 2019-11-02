package seedu.address.calendar.model.event;

import static java.util.Objects.requireNonNull;

public class Name {
    private String name;

    public Name(String name) {
        requireNonNull(name);
        this.name = name;
    }

    String asString() {
        return toString();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Name)) {
            return false;
        }

        Name otherName = (Name) obj;
        String otherNameStr = otherName.name;
        return this.name.equals(otherNameStr);
    }
}
