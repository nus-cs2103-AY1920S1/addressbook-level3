package seedu.deliverymans.storage.customer;

import java.util.AbstractMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.customer.Customer;

/**
 * Jackson-friendly version of totalTags in {@link Customer}.
 */
public class JsonAdaptedTotalTags {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Customer tag's %s field is missing!";

    private final String tag;
    private final String quantity;

    /**
     * Constructs a {@code JsonAdaptedFood} with the given {@code foodName}.
     */
    @JsonCreator
    public JsonAdaptedTotalTags(@JsonProperty("name") String tag,
                                @JsonProperty("quantity") String quantity) {
        this.tag = tag;
        this.quantity = quantity;
    }


    /**
     * Converts a given {@code totalTag} into this class for Jackson use.
     */
    public JsonAdaptedTotalTags(Map.Entry<Tag, Integer> entry) {
        tag = entry.getKey().tagName;
        quantity = entry.getValue().toString();
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public Map.Entry<Tag, Integer> toModelType() throws IllegalValueException {
        if (tag == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Tag.class.getSimpleName()));
        }
        if (!Tag.isValidTagName(tag)) {
            throw new IllegalValueException(Tag.MESSAGE_CONSTRAINTS);
        }
        final Tag modelTag = new Tag(tag);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Integer.class.getSimpleName()));
        }

        return new AbstractMap.SimpleEntry<Tag, Integer>(modelTag, Integer.parseInt(quantity));
    }

}
