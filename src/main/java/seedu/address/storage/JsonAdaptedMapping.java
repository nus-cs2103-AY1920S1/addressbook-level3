package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.GroupId;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.person.PersonId;

/**
 * Constructs a {@code JsonAdaptedMapping} with the given Mapping details.
 */
public class JsonAdaptedMapping {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Mapping's %s field is missing!";

    private final String groupId;
    private final String personId;

    /**
     * Constructs a {@code JsonAdaptedMapping} with the given Mapping details.
     */
    @JsonCreator
    public JsonAdaptedMapping(@JsonProperty("groupId") String groupId,
                              @JsonProperty("personId") String personId) {

        this.groupId = groupId;
        this.personId = personId;
    }

    /**
     * Converts a given {@code Mapping} into this class for Jackson use.
     */
    public JsonAdaptedMapping(PersonToGroupMapping source) {
        this.groupId = source.getGroupId().toString();
        this.personId = source.getPersonId().toString();
    }

    /**
     * Converts this Jackson-friendly adapted mapping object into the model's {@code PersonToGroupMapping} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted mapping.
     */
    public PersonToGroupMapping toModelType() throws IllegalValueException {
        if (groupId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    GroupId.class.getSimpleName()));
        }
        final GroupId modelGroupId = new GroupId(groupId);

        if (personId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PersonId.class.getSimpleName()));
        }
        final PersonId modelPersonId = new PersonId(personId);

        return new PersonToGroupMapping(modelPersonId, modelGroupId);
    }


}
