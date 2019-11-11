package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.account.Account;
import seedu.address.model.account.Username;

/**
 * Jackson-friendly version of {@link Account}.
 */
public class JsonAdaptedAccount {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Account's %s field is missing!";

    private final String username;
    private final String password;

    /**
     * Constructs a {@code JsonAdaptedAccount} with the given account details.
     */
    @JsonCreator
    public JsonAdaptedAccount(@JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Converts a given {@code Account} into this class for Jackson use.
     */
    public JsonAdaptedAccount(Account source) {
        username = source.getUsername().userId;
        password = source.getPassword();
    }

    /**
     * Converts this Jackson-friendly adapted account object into the model's {@code Account} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted account.
     */
    public Account toModelType() throws IllegalValueException {

        if (username == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Messages.MESSAGE_USERNAME_ERROR));
        }
        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_CONSTRAINTS);
        }
        final Username modelUsername = new Username(username);

        if (password == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Messages.MESSAGE_PASSWORD_ERROR));
        }
        final String modelPassword = this.password;

        return new Account(modelUsername, modelPassword);
    }
}
