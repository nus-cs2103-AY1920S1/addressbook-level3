package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class EventManpowerNeeded {
    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at least 3 digits long";
    public static final String VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public EventManpowerNeeded(String phone) {
        requireNonNull(phone);
        //checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        value = phone;
    }
}
