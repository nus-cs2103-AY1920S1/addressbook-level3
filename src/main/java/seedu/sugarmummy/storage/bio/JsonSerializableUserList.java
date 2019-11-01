package seedu.sugarmummy.storage.bio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.sugarmummy.commons.exceptions.IllegalValueException;
import seedu.sugarmummy.model.ReadOnlyUserList;
import seedu.sugarmummy.model.bio.User;
import seedu.sugarmummy.model.bio.UserList;

/**
 * An Immutable UserList that is serializable to JSON format.
 */
@JsonRootName(value = "userList")
class JsonSerializableUserList {

    public static final String MESSAGE_DUPLICATE_PERSON = "Users list contains duplicate user(s).";
    private static List<Map<String, String>> listOfFieldsContainingInvalidReferences = new ArrayList<>();

    private final List<JsonAdaptedUser> users = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableUserList} with the given users.
     */
    @JsonCreator
    public JsonSerializableUserList(@JsonProperty("users") List<JsonAdaptedUser> users) {
        this.users.addAll(users);
    }

    /**
     * Converts a given {@code ReadOnlyUserList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableUserList}.
     */
    public JsonSerializableUserList(ReadOnlyUserList source) {
        users.addAll(source.getUserList().stream().map(JsonAdaptedUser::new).collect(Collectors.toList()));
    }

    /**
     * Return a list of maps of fields in the json file that contain invalid references.
     *
     * @return List of maps of fields in the json file containing invalid references.
     */
    public static List<Map<String, String>> getListOfFieldsContainingInvalidReferences() {
        return listOfFieldsContainingInvalidReferences;
    }

    /**
     * Converts this address book into the sugarmummy.recmfood.model's {@code UserList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public UserList toModelType() throws IllegalValueException {
        UserList userList = new UserList();
        for (JsonAdaptedUser jsonAdaptedUser : users) {
            User user = jsonAdaptedUser.toModelType();

            if (userList.hasUser(user)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }

            Map<String, String> fieldsContainingInvalidReferences = JsonAdaptedUser
                    .getFieldsContainingInvalidReferences();

            if (!fieldsContainingInvalidReferences.isEmpty()) {
                listOfFieldsContainingInvalidReferences.add(fieldsContainingInvalidReferences);
            }

            userList.addUser(user);
        }
        return userList;
    }


}
