package io.xpire.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.xpire.commons.exceptions.IllegalValueException;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.item.ReminderThreshold;
import io.xpire.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Item}.
 */
class JsonAdaptedItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Item's %s field is missing!";

    private final String name;
    private final String expiryDate;
    private final String reminderThreshold;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedItem} with the given item details.
     */
    @JsonCreator
    public JsonAdaptedItem(@JsonProperty("name") String name,
                           @JsonProperty("expiryDate") String expiryDate,
                           @JsonProperty("reminderThreshold") String reminderThreshold,
                           @JsonProperty("tags") List<JsonAdaptedTag> tags) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.reminderThreshold = reminderThreshold;
        this.tags.addAll(tags);
    }

    /**
     * Converts a given {@code Item} into this class for Jackson use.
     */
    public JsonAdaptedItem(Item source) {
        this.name = source.getName().toString();
        this.expiryDate = source.getExpiryDate().toString();
        this.reminderThreshold = source.getReminderThreshold().toString();
        this.tags.addAll(source
                .getTags()
                .stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted item object into the model's {@code Item} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item.
     */
    public Item toModelType() throws IllegalValueException {

        if (this.name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(this.name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(this.name);

        if (this.expiryDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ExpiryDate.class.getSimpleName()));
        }
        if (!ExpiryDate.isValidExpiryDate(this.expiryDate)) {
            throw new IllegalValueException(ExpiryDate.MESSAGE_CONSTRAINTS);
        }
        final ExpiryDate modelExpiryDate = new ExpiryDate(this.expiryDate);

        if (this.reminderThreshold == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ReminderThreshold.class.getSimpleName()));
        }
        if (!ReminderThreshold.isValidReminderThreshold(this.reminderThreshold)) {
            throw new IllegalValueException(ReminderThreshold.MESSAGE_CONSTRAINTS);
        }
        final ReminderThreshold modelReminderThreshold = new ReminderThreshold(this.reminderThreshold);

        final List<Tag> itemTags = new ArrayList<>();
        for (JsonAdaptedTag tag : this.tags) {
            itemTags.add(tag.toModelType());
        }
        final Set<Tag> modelTags = new HashSet<>(itemTags);

        Item item = new Item(modelName, modelExpiryDate, modelTags);
        item.setReminderThreshold(modelReminderThreshold);
        return item;
    }

}
