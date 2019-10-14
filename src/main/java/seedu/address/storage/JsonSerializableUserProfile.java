package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.profile.DukeCooks;
import seedu.address.profile.ReadOnlyDukeCooks;
import seedu.address.profile.person.Person;

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
    * Converts a given {@code ReadOnlyDukeCooks} into this class for Jackson use.
    *
    * @param source future changes to this will not affect the created {@code JsonSerializableUserProfile}.
    */
    public JsonSerializableUserProfile(ReadOnlyDukeCooks source) {
        userprofile.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
    *  Converts this User Profile into the model's {@code DukeCooks} object.
    *
    * @throws IllegalValueException if there were any data constraints violated.
    */
    public DukeCooks toModelType() throws IllegalValueException {
        DukeCooks dukeCooks = new DukeCooks();
        for (JsonAdaptedPerson jsonAdaptedPerson : userprofile) {
            Person person = jsonAdaptedPerson.toModelType();
            dukeCooks.addPerson(person);
        }
        return dukeCooks;
    }
}
