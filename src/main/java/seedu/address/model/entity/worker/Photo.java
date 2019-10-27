package seedu.address.model.entity.worker;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;

//@@ author shaoyi1997

/**
 * Represents the display picture of a worker in Mortago.
 */
public class Photo {

    public static final String PATH_TO_EXAMPLE_PHOTO = "src/main/resources/images/ExamplePhoto.jpg";
    public static final String MESSAGE_CONSTRAINTS =
            "Error in importing display photo: Photo should be less than 2MB or it does not exist.";
    public static final String MESSAGE_DATA_COPY_ERROR =
            "Error in copying photo to the data directory";
    public static final String PATH_TO_DATA_DIRECTORY = "data" + File.separator + "photo" + File.separator;

    private static final Logger logger = LogsCenter.getLogger(Photo.class);
    private String dataDirectory;
    private String originalDirectory;

    public Photo(String pathToPhoto) throws IllegalArgumentException {
        requireNonNull(pathToPhoto);
        this.originalDirectory = pathToPhoto;
        File photo = new File(originalDirectory);
        this.dataDirectory = PATH_TO_DATA_DIRECTORY + photo.getName();
        copyToDataDirectory(photo.toPath(), Paths.get(this.dataDirectory));
    }

    /**
     * Copies the photo from the {@code originalPath} to the {@code dataDirPath}.
     * @throws ParseException if unable to copy the file.
     */
    private void copyToDataDirectory(Path originalPath, Path dataDirPath) throws IllegalArgumentException {
        try {
            Files.copy(originalPath, dataDirPath, REPLACE_EXISTING);
        } catch (IOException e) {
            logger.warning(e.getMessage());
            throw new IllegalArgumentException(MESSAGE_DATA_COPY_ERROR);
        }
    }

    /**
     * Returns the file path of
     * the copied photo in the data directory.
     * Intended for {@code ImageView} to reference to the photo.
     */
    public String getPathToDataDirectory() {
        return "file://" + Paths.get(dataDirectory).toAbsolutePath().toUri().getPath();
    }

    /**
     * Returns true if the photo from {@code pathToPhoto} is a valid photo.
     * Valid photo extensions are jpeg, jpg and png.
     */
    public static boolean isValidPhoto(String pathToPhoto) {
        if (pathToPhoto.isEmpty()) {
            return false;
        }
        File photo = new File(pathToPhoto);
        if (photo.exists()) {
            return pathToPhoto.endsWith(".jpeg") || (pathToPhoto.endsWith(".jpg") || pathToPhoto.endsWith(".png"));
        }
        return false;
    }

    @Override
    public String toString() {
        return "Original Directory: " + originalDirectory + "\nData Directory: " + dataDirectory;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Photo // instanceof handles nulls
            && (this.dataDirectory.equals(((Photo) other).dataDirectory)
            && (this.originalDirectory.equals(((Photo) other).originalDirectory)))); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalDirectory, dataDirectory);
    }

}
//@@ author
