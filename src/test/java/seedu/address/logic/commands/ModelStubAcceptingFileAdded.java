package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.model.FileBook;
import seedu.address.model.ReadOnlyFileBook;
import seedu.address.model.file.EncryptedFile;

/**
 * A Model stub that always accept the encrypted file being added.
 */
public class ModelStubAcceptingFileAdded extends ModelStub {
    final ArrayList<EncryptedFile> filesAdded = new ArrayList<>();

    @Override
    public boolean hasFile(EncryptedFile file) {
        requireNonNull(file);
        return filesAdded.stream().anyMatch(file::isSameFile);
    }

    @Override
    public void addFile(EncryptedFile file) {
        requireNonNull(file);
        filesAdded.add(file);
    }

    @Override
    public ReadOnlyFileBook getFileBook() {
        return new FileBook();
    }
}
