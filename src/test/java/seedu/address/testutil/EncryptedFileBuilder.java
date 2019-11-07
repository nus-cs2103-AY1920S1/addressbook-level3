package seedu.address.testutil;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.file.EncryptedAt;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.FileName;
import seedu.address.model.file.FilePath;
import seedu.address.model.file.FileStatus;
import seedu.address.model.file.ModifiedAt;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Person objects.
 */
public class EncryptedFileBuilder {
    public static final String DEFAULT_FILENAME = "Test File.txt";
    public static final String DEFAULT_FILEPATH = TestUtil.SANDBOX_FOLDER.toString();
    public static final Date DEFAULT_DATE = new Date();
    public static final FileStatus DEFAULT_STATUS = FileStatus.ACTIVE;

    private FileName fileName;
    private FilePath filePath;
    private EncryptedAt encryptedAt;
    private ModifiedAt modifiedAt;
    private FileStatus fileStatus;
    private Set<Tag> tags;

    /**
     * Initializes the EncryptedFileBuilder.
     */
    public EncryptedFileBuilder() {
        fileName = new FileName(DEFAULT_FILENAME);
        filePath = new FilePath(DEFAULT_FILEPATH);
        encryptedAt = new EncryptedAt(DEFAULT_DATE);
        modifiedAt = new ModifiedAt(DEFAULT_DATE);
        fileStatus = DEFAULT_STATUS;
        tags = new HashSet<>();
    }

    /**
     * Sets the {@code FileName} of the {@code EncryptedFile} that we are building.
     */
    public EncryptedFileBuilder withFileName(String fileName) {
        this.fileName = new FileName(fileName);
        return this;
    }

    /**
     * Sets the {@code FilePath} of the {@code EncryptedFile} that we are building.
     */
    public EncryptedFileBuilder withFilePath(String filePath) {
        this.filePath = new FilePath(filePath);
        return this;
    }

    public EncryptedFile build() {
        return new EncryptedFile(fileName, filePath, fileStatus, tags, encryptedAt, modifiedAt);
    }
}
