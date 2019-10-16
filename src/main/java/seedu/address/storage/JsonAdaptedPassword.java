package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.password.Description;
import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Password}.
 */
class JsonAdaptedPassword {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Password's %s field is missing!";

    private final String description;
    private final String username;
    private final String passwordValue;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPassword} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPassword(@JsonProperty("description") String description,
                               @JsonProperty("username") String username,
                               @JsonProperty("passwordValue") String passwordValue,
                               @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.description = description;
        this.username = username;
        this.passwordValue = passwordValue;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPassword(Password password) {
        description = password.getDescription().value;
        username = password.getUsername().value;
        passwordValue = password.getPasswordValue().value;
        tagged.addAll(password.getTags().stream()
                .map(JsonAdaptedTag::new)
               .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Password} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Password toModelType() throws IllegalValueException {
        final List<Tag> passwordTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            passwordTags.add(tag.toModelType());
        }

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        final Description modelDescription = new Description(description);

        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        //if (!Phone.isValidPhone(phone)) {
        //    throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        //}
        final Username modelUserName = new Username(username);

        if (passwordValue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PasswordValue.class.getSimpleName()));
        }
        final PasswordValue modelPasswordValue = new PasswordValue(passwordValue);

        final Set<Tag> modelTags = new HashSet<>(passwordTags);
        return new Password(modelDescription, modelUserName, modelPasswordValue, modelTags);
    }

}
