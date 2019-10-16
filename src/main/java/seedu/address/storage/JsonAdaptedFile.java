package seedu.address.storage;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.file.EncryptedAt;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.file.FileName;
import seedu.address.model.file.FilePath;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.DateUtil;

/**
 * Jackson-friendly version of {@link seedu.address.model.file.EncryptedFile}.
 */
class JsonAdaptedFile {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "File's %s field is missing!";

    private final String fileName;
    private final String filePath;
    private final String encryptedAt;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFile} with the given file details.
     */
    @JsonCreator
    public JsonAdaptedFile(@JsonProperty("filename") String fileName,
                             @JsonProperty("path") String filePath,
                             @JsonProperty("encrypted_at") String encryptedAt,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.encryptedAt = encryptedAt;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code EncryptedFile} into this class for Jackson use.
     */
    public JsonAdaptedFile(EncryptedFile source) {
        fileName = source.getFileName().value;
        filePath = source.getFilePath().value;
        encryptedAt = DateUtil.formatDate(source.getEncryptedAt().value);
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public EncryptedFile toModelType() throws IllegalValueException {
        final List<Tag> fileTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            fileTags.add(tag.toModelType());
        }

        if (fileName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    FileName.class.getSimpleName()));
        }
        final FileName modelName = new FileName(fileName);

        if (filePath == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    FilePath.class.getSimpleName()));
        }
        final FilePath modelPath = new FilePath(filePath);

        if (encryptedAt == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EncryptedAt.class.getSimpleName()));
        }
        final EncryptedAt modelEncryptedAt;
        try {
            modelEncryptedAt = new EncryptedAt(DateUtil.parseDate(encryptedAt));
        } catch (ParseException e) {
            throw new IllegalValueException(EncryptedAt.MESSAGE_CONSTRAINTS);
        }

        final Set<Tag> modelTags = new HashSet<>(fileTags);
        return new EncryptedFile(modelName, modelPath, modelEncryptedAt, modelTags);
    }

}
