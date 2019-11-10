package seedu.planner.storage.activity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.planner.commons.exceptions.IllegalValueException;

import seedu.planner.commons.util.StringUtil;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.activity.Duration;
import seedu.planner.model.activity.Priority;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.Cost;
import seedu.planner.model.field.Name;
import seedu.planner.model.tag.Tag;
import seedu.planner.storage.JsonAdaptedTag;
import seedu.planner.storage.contact.JsonAdaptedContact;

/**
 * Jackson-friendly version of {@link Contact}.
 */
public class JsonAdaptedActivity {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Activity's %s field is missing!";
    private static final int LOWEST_PRIORITY = 0;

    private final String name;
    private final String address;
    private final JsonAdaptedContact contact;
    private final String cost;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final String duration;
    private final String priority;

    /**
     * Constructs a {@code JsonAdaptedActivity} with the given activity details.
     */
    @JsonCreator
    public JsonAdaptedActivity(@JsonProperty("name") String name, @JsonProperty("address") String address,
            @JsonProperty("contact") JsonAdaptedContact contact, @JsonProperty("cost") String cost,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged, @JsonProperty("duration") String duration,
                               @JsonProperty("priority") String priority) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.cost = cost;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        this.duration = duration;
        this.priority = priority;
    }

    /**
     * Converts a given {@code Activity} into this class for Jackson use.
     */
    public JsonAdaptedActivity(Activity source) {
        name = source.getName().toString();
        address = source.getAddress().toString();
        contact = source.getContact().isPresent() ? new JsonAdaptedContact(source.getContact().get()) : null;
        cost = source.getCost().isPresent() ? source.getCost().get().toString() : null;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        duration = Integer.toString(source.getDuration().value);
        priority = Integer.toString(source.getPriority().priorityValue);
    }

    /**
     * Converts this Jackson-friendly adapted contacts object into the model's {@code Activity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contacts.
     */
    public Activity toModelType() throws IllegalValueException {
        final List<Tag> activityTags = new ArrayList<>();
        final Priority modelPriority;
        for (JsonAdaptedTag tag : tagged) {
            activityTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (address != null && !Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = address != null ? new Address(address) : null;

        final Contact modelContact = (contact == null) ? null : contact.toModelType();

        final Cost modelCost = cost == null ? null : new Cost(cost);

        final Set<Tag> modelTags = new HashSet<>(activityTags);

        if (duration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Duration.class.getSimpleName()));
        }
        if (!StringUtil.isNonZeroSignedInteger(duration) && (Integer.parseInt(duration) >= 0)) {
            throw new IllegalValueException(String.format(Duration.MESSAGE_CONSTRAINTS));
        }
        final Duration modelDuration = new Duration(Integer.parseInt(duration));
        if (priority == null) {
            modelPriority = new Priority(LOWEST_PRIORITY);
        } else {
            modelPriority = new Priority(Integer.parseInt(priority));
        }

        return new Activity(modelName, modelAddress, modelContact, modelCost, modelTags, modelDuration, modelPriority);
    }

}
