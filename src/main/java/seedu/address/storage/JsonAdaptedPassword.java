package seedu.address.storage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.password.Password;
import seedu.address.model.password.PasswordDescription;
import seedu.address.model.password.PasswordModifiedAt;
import seedu.address.model.password.PasswordValue;
import seedu.address.model.password.Username;
import seedu.address.model.password.Website;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.DateUtil;

/**
 * Jackson-friendly version of {@link Password}.
 */
class JsonAdaptedPassword {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Password's %s field is missing!";

    private final String description;
    private final String username;
    private final String passwordValue;
    private final String modifiedAt;
    private final String website;

    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPassword} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPassword(@JsonProperty("description") String description,
                               @JsonProperty("username") String username,
                               @JsonProperty("passwordValue") String passwordValue,
                               @JsonProperty("modifiedAt") String modifiedAt,
                               @JsonProperty("website") String website,
                               @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.description = description;
        this.username = username;
        this.passwordValue = passwordValue;
        this.modifiedAt = modifiedAt;
        this.website = website;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPassword(Password password) {
        description = password.getPasswordDescription().value;
        username = password.getUsername().value;
        passwordValue = password.getPasswordValue().value;
        modifiedAt = DateUtil.formatDate(password.getPasswordModifiedAt().value);
        website = password.getWebsite().value;
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
                    PasswordDescription.class.getSimpleName()));
        }
        final PasswordDescription modelPasswordDescription = new PasswordDescription(description);

        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Username.class.getSimpleName()));
        }
        final Username modelUserName = new Username(username);

        if (passwordValue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PasswordValue.class.getSimpleName()));
        }
        final PasswordValue modelPasswordValue = new PasswordValue(passwordValue);

        if (modifiedAt == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PasswordModifiedAt.class.getSimpleName()));
        }

        if (website == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Website.class.getSimpleName()));
        }
        final Website modelWebsite = new Website(website);

        final PasswordModifiedAt modelPasswordModifiedAt;
        try {
            modelPasswordModifiedAt = new PasswordModifiedAt(DateUtil.parseDate(modifiedAt));
        } catch (ParseException e) {
            throw new IllegalValueException(PasswordModifiedAt.MESSAGE_CONSTRAINTS);
        }
        final Set<Tag> modelTags = new HashSet<>(passwordTags);
        return new Password(modelPasswordDescription, modelUserName, modelPasswordValue, modelPasswordModifiedAt,
                modelWebsite, modelTags);
    }

}
