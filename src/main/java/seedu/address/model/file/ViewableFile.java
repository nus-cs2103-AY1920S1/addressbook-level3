package seedu.address.model.file;

/**
 * A class to represent a file that is viewable with preview command.
 * @param <T> the type of the file content.
 */
public class ViewableFile<T> {
    private final EncryptedFile file;
    private final ViewableFileType type;
    private final T content;

    public ViewableFile(EncryptedFile file, ViewableFileType type, T content) {
        this.file = file;
        this.type = type;
        this.content = content;
    }

    public ViewableFileType getFileType() {
        return type;
    }

    public T getFileContent() {
        return content;
    }

    public String getFileName() {
        return file.getFileName().value;
    }
}
