package seedu.address.model.item;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Description of the item.
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS = "Description should not be blank.";
    public final String itemDescription;

    public Description(String desc) {
        requireNonNull(desc);
        checkArgument(isValidDescription(desc), MESSAGE_CONSTRAINTS);
        itemDescription = desc;
    }

    public static boolean isValidDescription(String description) {
        return (!description.isEmpty());
    }

    @Override
    public String toString() {
        return itemDescription;
    }
}
