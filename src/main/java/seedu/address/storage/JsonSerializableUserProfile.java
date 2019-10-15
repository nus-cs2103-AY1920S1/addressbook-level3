package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyUserProfile;
import seedu.address.model.UserProfile;
import seedu.address.model.person.Person;

/**
 * An Immutable User Profile that is serializable to JSON format.
 */
@JsonRootName(value = "userprofile")
class JsonSerializableUserProfile {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> userprofile = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableUserProfile} with the given userprofile.
     */
    @JsonCreator
    public JsonSerializableUserProfile(@JsonProperty("userprofile") List<JsonAdaptedPerson> person) {
        this.userprofile.addAll(person);
    }

    /**
    * Converts a given {@code ReadOnlyUserProfile} into this class for Jackson use.
    *
    * @param source future changes to this will not affect the created {@code JsonSerializableUserProfile}.
    */
    public JsonSerializableUserProfile(ReadOnlyUserProfile source) {
        userprofile.addAll(source.getUserProfileList().stream().map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
    }

    /**
    *  Converts this User Profile into the model's {@code UserProfile} object.
    *
    * @throws IllegalValueException if there were any data constraints violated.
    */
    public UserProfile toModelType() throws IllegalValueException {
        UserProfile userProfile = new UserProfile();
        for (JsonAdaptedPerson jsonAdaptedPerson : userprofile) {
            Person person = jsonAdaptedPerson.toModelType();
            userProfile.addPerson(person);
        }
        return userProfile;
    }
}
