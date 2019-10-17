package seedu.address.model.file;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Encrypted File in SecureIT.
 */
public class EncryptedFile {
    private final FileName fileName;
    private final FilePath filePath;
    private final EncryptedAt encryptedAt;

    private final Set<Tag> tags = new HashSet<>();

    public EncryptedFile(FileName fileName, FilePath filePath, EncryptedAt encryptedAt, Set<Tag> tags) {
        requireAllNonNull(fileName, filePath, encryptedAt, tags);
        this.fileName = fileName;
        this.filePath = filePath;
        this.encryptedAt = encryptedAt;
        this.tags.addAll(tags);
    }

    public FileName getFileName() {
        return fileName;
    }

    public FilePath getFilePath() {
        return filePath;
    }

    public EncryptedAt getEncryptedAt() {
        return encryptedAt;
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
        return Paths.get(getFilePath().value + "/" + getFileName()).toString();
    }

    /**
     * Returns the full path of the encrypted file in string.
     */
    public String getEncryptedPath() {
        return Paths.get(getFilePath().value + "/[LOCKED] " + getFileName()).toString();
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
                && otherFile.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, filePath, encryptedAt, tags);
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
