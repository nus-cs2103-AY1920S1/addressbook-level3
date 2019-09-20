package seedu.algobase.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.algobase.commons.exceptions.IllegalValueException;
import seedu.algobase.model.Problem.*;
import seedu.algobase.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Problem}.
 */
class JsonAdaptedProblem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Problem's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedProblem} with the given Problem details.
     */
    @JsonCreator
    public JsonAdaptedProblem(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Problem} into this class for Jackson use.
     */
    public JsonAdaptedProblem(Problem source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getWebLink().value;
        address = source.getDescription().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted Problem object into the model's {@code Problem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Problem.
     */
    public Problem toModelType() throws IllegalValueException {
        final List<Tag> problemTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            problemTags.add(tag.toModelType());
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

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, WebLink.class.getSimpleName()));
        }
        if (!WebLink.isValidEmail(email)) {
            throw new IllegalValueException(WebLink.MESSAGE_CONSTRAINTS);
        }
        final WebLink modelWebLink = new WebLink(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValidAddress(address)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(address);

        final Set<Tag> modelTags = new HashSet<>(problemTags);
        return new Problem(modelName, modelPhone, modelWebLink, modelDescription, modelTags);
    }

}
