package seedu.address.storage.bio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyUserList;
import seedu.address.model.bio.User;
import seedu.address.model.bio.UserList;

/**
 * An Immutable UserList that is serializable to JSON format.
 */
@JsonRootName(value = "userList")
class JsonSerializableUserList {

    public static final String MESSAGE_DUPLICATE_PERSON = "Users list contains duplicate user(s).";

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
     * Converts this address book into the model's {@code UserList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public UserList toModelType() throws IllegalValueException {
        UserList addressBook = new UserList();
        for (JsonAdaptedUser jsonAdaptedUser : users) {
            User user = jsonAdaptedUser.toModelType();
            if (addressBook.hasUser(user)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addUser(user);
        }
        return addressBook;
    }

}
