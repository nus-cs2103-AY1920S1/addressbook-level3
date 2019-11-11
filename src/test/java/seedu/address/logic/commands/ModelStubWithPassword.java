package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.password.Password;

/**
 * A Model stub that contains a single password.
 */
public class ModelStubWithPassword extends ModelStub {
    private final Password password;

    ModelStubWithPassword(Password password) {
        requireNonNull(password);
        this.password = password;
    }

    @Override
    public boolean hasPassword(Password person) {
        requireNonNull(person);
        return this.password.isSamePassword(person);
    }
}
