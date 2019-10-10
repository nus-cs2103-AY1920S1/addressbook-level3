package seedu.address.model.file;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an Encrypted File in SecureIT.
 */
public class EncryptedFile {
    private final FileName fileName;
    private final FilePath filePath;
    private final EncryptedAt encryptedAt;

    public EncryptedFile(FileName fileName, FilePath filePath, EncryptedAt encryptedAt) {
        requireAllNonNull(fileName, filePath, encryptedAt);
        this.fileName = fileName;
        this.filePath = filePath;
        this.encryptedAt = encryptedAt;
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
                && otherFile.getFilePath().equals(getFilePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, filePath);
    }
}
