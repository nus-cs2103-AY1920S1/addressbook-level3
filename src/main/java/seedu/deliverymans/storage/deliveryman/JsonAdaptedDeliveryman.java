package seedu.deliverymans.storage.deliveryman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.deliverymans.commons.exceptions.IllegalValueException;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.storage.JsonAdaptedTag;


/**
 * Jackson-friendly version of {@link Deliveryman}.
 */
public class JsonAdaptedDeliveryman {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Deliveryman's %s field is missing!";

    private final String name;
    private final String phone;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedDeliveryman} with the given deliveryman details.
     */
    @JsonCreator
    public JsonAdaptedDeliveryman(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                                  @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Deliveryman} into this class for Jackson use.
     */
    public JsonAdaptedDeliveryman(Deliveryman source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted deliveryman object into the model's {@code Deliveryman} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted deliveryman.
     */
    public Deliveryman toModelType() throws IllegalValueException {
        final List<Tag> deliverymanTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            deliverymanTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        final Set<Tag> modelTags = new HashSet<>(deliverymanTags);
        return new Deliveryman(modelName, modelPhone, modelTags);
    }

}
