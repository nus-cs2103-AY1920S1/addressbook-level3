package seedu.weme.storage;

import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.weme.commons.exceptions.IllegalValueException;
import seedu.weme.model.path.ImagePath;
import seedu.weme.model.template.Name;
import seedu.weme.model.template.Template;

/**
 * Jackson-friendly version of {@link Template}.
 */
class JsonAdaptedTemplate {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Template's %s field is missing!";

    private final String name;
    private final String filePath;
    private final boolean isArchived;

    /**
     * Constructs a {@code JsonAdaptedTemplate} with the given template details.
     */
    @JsonCreator
    public JsonAdaptedTemplate(@JsonProperty("name") String name, @JsonProperty("filePath") String filePath,
                               @JsonProperty("isArchived") boolean isArchived) {
        this.name = name;
        this.filePath = filePath;
        this.isArchived = isArchived;
    }

    /**
     * Converts a given {@code Template} into this class for Jackson use.
     * @param folderPath the path to the folder the template images are saved in.
     */
    public JsonAdaptedTemplate(Template source, Path folderPath) {
        name = source.getName().toString();
        filePath = folderPath.relativize(source.getImagePath().getFilePath()).toString(); // store only filename
        isArchived = source.isArchived();
    }

    /**
     * Converts this Jackson-friendly adapted template object into the model's {@code Template} object.
     *
     * @param folderPath the path to the folder the template images are saved in.
     * @throws IllegalValueException if there were any data constraints violated in the adapted template.
     */
    public Template toModelType(Path folderPath) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (filePath == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                ImagePath.class.getSimpleName()));
        }
        String imagePathString = folderPath.resolve(filePath).toString(); // convert to be usable by Weme
        if (!ImagePath.isValidFilePath(imagePathString)) {
            throw new IllegalValueException(ImagePath.MESSAGE_CONSTRAINTS);
        }
        final ImagePath modelFilePath = new ImagePath(imagePathString);

        return new Template(modelName, modelFilePath, isArchived);
    }

}
