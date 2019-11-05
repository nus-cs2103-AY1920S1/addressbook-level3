package seedu.address.model.file;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;
import seedu.address.model.util.FileNameUtil;

/**
 * Represents an Encrypted File in SecureIT.
 */
public class EncryptedFile {
    public static final String PREFIX = "[LOCKED]";

    private final FileName fileName;
    private final FilePath filePath;
    private final FileStatus status;
    private EncryptedAt encryptedAt;
    private ModifiedAt modifiedAt;

    private final Set<Tag> tags = new HashSet<>();

    public EncryptedFile(FileName fileName,
                         FilePath filePath,
                         Set<Tag> tags) {
        this(fileName, filePath, FileStatus.ACTIVE, tags);
    }

    public EncryptedFile(FileName fileName,
                         FilePath filePath,
                         FileStatus status,
                         Set<Tag> tags) {
        requireAllNonNull(fileName, filePath, tags);
        this.fileName = fileName;
        this.filePath = filePath;
        this.status = status;
        this.tags.addAll(tags);
    }

    public EncryptedFile(FileName fileName,
                         FilePath filePath,
                         Set<Tag> tags,
                         boolean withPrefix) {
        this(new FileName(FileNameUtil.getFileNameWithoutPrefix(fileName.value)), filePath, tags);
    }

    public EncryptedFile(FileName fileName,
                         FilePath filePath,
                         FileStatus status,
                         Set<Tag> tags,
                         EncryptedAt encryptedAt,
                         ModifiedAt modifiedAt) {
        this(fileName, filePath, status, tags);
        requireAllNonNull(encryptedAt, modifiedAt);
        this.encryptedAt = encryptedAt;
        this.modifiedAt = modifiedAt;
    }

    public FileName getFileName() {
        return fileName;
    }

    public FilePath getFilePath() {
        return filePath;
    }

    public FileStatus getFileStatus() {
        return status;
    }

    public void setEncryptedAt(EncryptedAt value) {
        encryptedAt = value;
    }

    public EncryptedAt getEncryptedAt() {
        return encryptedAt;
    }

    public void setModifiedAt(ModifiedAt value) {
        modifiedAt = value;
    }

    public ModifiedAt getModifiedAt() {
        return modifiedAt;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns the full path of the file in string.
     */
    public String getFullPath() {
        return Path.of(getFilePath().value).resolve(Path.of(getFileName().value)).toString();
    }

    /**
     * Returns the full path of the encrypted file in string.
     */
    public String getEncryptedPath() {
        return Path.of(getFilePath().value)
                .resolve(FileNameUtil.getFileNameWithPrefix(getFileName().value)).toString();
    }

    /**
     * Returns the file extension of the encrypted file.
     */
    public String getFileExtension() {
        return fileName.getExtension();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EncryptedFile)) {
            return false;
        }

        EncryptedFile otherFile = (EncryptedFile) other;
        return otherFile.getFileName().equals(getFileName())
                && otherFile.getFilePath().equals(getFilePath())
                && otherFile.getEncryptedAt().equals(getEncryptedAt())
                && otherFile.getModifiedAt().equals(getModifiedAt())
                && otherFile.getFileStatus().equals(getFileStatus())
                && otherFile.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, filePath, encryptedAt, modifiedAt, status, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getFileName());
        getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Returns true if both files have the same file name and path.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameFile(EncryptedFile otherFile) {
        if (otherFile == this) {
            return true;
        }

        return otherFile != null
                && otherFile.getFileName().equals(getFileName())
                && (otherFile.getFilePath().equals(getFilePath()));
    }
}
