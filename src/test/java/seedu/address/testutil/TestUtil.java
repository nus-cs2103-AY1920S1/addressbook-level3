package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBookModel;
import seedu.address.model.person.Person;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    /**
     * Folder used for temp files created during testing. Ignored by Git.
     */
    private static final Path SANDBOX_FOLDER = Paths.get("src", "test", "data", "sandbox");

    /**
     * Appends {@code fileName} to the sandbox folder path and returns the resulting path.
     * Creates the sandbox folder if it doesn't exist.
     */
    public static Path getFilePathInSandboxFolder(String fileName) {
        try {
            Files.createDirectories(SANDBOX_FOLDER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return SANDBOX_FOLDER.resolve(fileName);
    }

    /**
     * Returns the middle index of the person in the {@code addressBookModel}'s person list.
     */
    public static Index getMidIndex(AddressBookModel addressBookModel) {
        return Index.fromOneBased(addressBookModel.getFilteredPersonList().size() / 2);
    }

    /**
     * Returns the last index of the person in the {@code addressBookModel}'s person list.
     */
    public static Index getLastIndex(AddressBookModel addressBookModel) {
        return Index.fromOneBased(addressBookModel.getFilteredPersonList().size());
    }

    /**
     * Returns the person in the {@code addressBookModel}'s person list at {@code index}.
     */
    public static Person getPerson(AddressBookModel addressBookModel, Index index) {
        return addressBookModel.getFilteredPersonList().get(index.getZeroBased());
    }
}
