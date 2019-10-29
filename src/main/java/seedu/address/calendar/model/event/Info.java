package seedu.address.calendar.model.event;

import static java.util.Objects.requireNonNull;

public class Info {
    private String info;

    public Info(String info) {
        requireNonNull(info);
        this.info = info;
    }

    String asString() {
        return toString();
    }

    @Override
    public String toString() {
        return info;
    }
}
