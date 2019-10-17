package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.FileBook;
import seedu.address.model.ReadOnlyFileBook;
import seedu.address.model.file.EncryptedFile;

/**
 * An Immutable FileBook that is serializable to JSON format.
 */
@JsonRootName(value = "filebook")
class JsonSerializableFileBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Files list contains duplicate file(s).";

    private final List<JsonAdaptedFile> files = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFileBook} with the given files.
     */
    @JsonCreator
    public JsonSerializableFileBook(@JsonProperty("files") List<JsonAdaptedFile> files) {
        this.files.addAll(files);
    }

    /**
     * Converts a given {@code ReadOnlyFileBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFileBook}.
     */
    public JsonSerializableFileBook(ReadOnlyFileBook source) {
        files.addAll(source.getFileList().stream().map(JsonAdaptedFile::new).collect(Collectors.toList()));
    }

    /**
     * Converts this file book into the model's {@code FileBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FileBook toModelType() throws IllegalValueException {
        FileBook fileBook = new FileBook();
        for (JsonAdaptedFile jsonAdaptedFile : files) {
            EncryptedFile file = jsonAdaptedFile.toModelType();
            if (fileBook.hasFile(file)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            fileBook.addFile(file);
        }
        return fileBook;
    }

}
