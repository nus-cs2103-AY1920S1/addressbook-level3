package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.group.GroupId;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.mapping.Role;
import seedu.address.model.person.PersonId;

/**
 * Constructs a {@code JsonAdaptedMapping} with the given Mapping details.
 */
public class JsonAdaptedMapping {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Mapping's %s field is missing!";

    private final String groupId;
    private final String personId;
    private final String role;

    /**
     * Constructs a {@code JsonAdaptedMapping} with the given Mapping details.
     */
    @JsonCreator
    public JsonAdaptedMapping(@JsonProperty("groupId") String groupId,
                              @JsonProperty("personId") String personId,
                              @JsonProperty("role") String role) {

        this.groupId = groupId;
        this.personId = personId;
        this.role = role;
    }

    /**
     * Converts a given {@code Mapping} into this class for Jackson use.
     */
    public JsonAdaptedMapping(PersonToGroupMapping source) {
        this.groupId = source.getGroupId().toString();
        this.personId = source.getPersonId().toString();
        this.role = source.getRole().toString();
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

        if(role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Role.class.getSimpleName()));
        }
        final Role modelRole = new Role(role);

        return new PersonToGroupMapping(modelPersonId, modelGroupId, modelRole);
    }


}
