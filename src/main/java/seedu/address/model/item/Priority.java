package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Priority {

    public static final String MESSAGE_CONSTRAINTS = "Description should not be blank.";
    public final String itemPriority;

    public Priority(String p) {
        requireNonNull(p);
        checkArgument(isValidPriority(p), MESSAGE_CONSTRAINTS);
        itemPriority = p;
    }

    public static boolean isValidPriority(String priority) {
        return (!priority.isEmpty());
    }

    @Override
    public String toString() {
        return itemPriority;
    }
}