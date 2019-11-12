package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.file.EncryptedFile;

/**
 * A Model stub that contains a single encrypted file.
 */
public class ModelStubWithEncryptedFile extends ModelStub {
    private final EncryptedFile file;

    ModelStubWithEncryptedFile(EncryptedFile file) {
        requireNonNull(file);
        this.file = file;
    }

    @Override
    public boolean hasFile(EncryptedFile file) {
        requireNonNull(file);
        return this.file.isSameFile(file);
    }
}
