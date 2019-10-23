package seedu.address.calendar.model;
import static java.util.Objects.requireNonNull;

public class Info {
    private String info;

    public Info(String info) {
        requireNonNull(info);
        this.info = info;
    }
}
