package seedu.address.model.entity.worker;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.AppUtil;
import seedu.address.commons.util.FileUtil;

//@@author shaoyi1997
/**
 * Represents the display picture of a worker in Mortago.
 */
public class Photo {

    public static final String MESSAGE_CONSTRAINTS =
            "Error in importing display photo: Photo should be a jpeg, jpg or png file, or it does not exist.";
    public static final String MESSAGE_DATA_COPY_ERROR =
            "Error in copying photo to the data directory";
    public static final String PATH_TO_DATA_DIRECTORY = "data" + File.separator + "photo" + File.separator;
    public static final String PATH_TO_EXAMPLE_PHOTO = PATH_TO_DATA_DIRECTORY + "ExamplePhoto.jpg";

    private static final Logger logger = LogsCenter.getLogger(Photo.class);
    private String dataDirectory; // the path to the copied photo in the data directory
    private String originalDirectory; // the original file path

    public Photo(String pathToPhoto) throws IllegalArgumentException {
        requireNonNull(pathToPhoto);
        if (pathToPhoto.equals(PATH_TO_EXAMPLE_PHOTO)) {
            originalDirectory = PATH_TO_EXAMPLE_PHOTO;
            dataDirectory = PATH_TO_EXAMPLE_PHOTO;
            if (!FileUtil.isFileExists(Paths.get(pathToPhoto))) {
                initExamplePhoto();
            }
        } else {
            this.originalDirectory = pathToPhoto;
            File photo = new File(originalDirectory);
            this.dataDirectory = PATH_TO_DATA_DIRECTORY + photo.getName();
            copyToDataDirectory(Paths.get(originalDirectory), Paths.get(this.dataDirectory));
        }
    }

    /**
     * Copies the photo from the {@code originalPath} to the {@code dataDirPath}.
     *
     * @throws IllegalArgumentException if unable to copy the file.
     */
    private void copyToDataDirectory(Path originalPath, Path dataDirPath) throws IllegalArgumentException {
        try {
            Files.createDirectories(Paths.get(PATH_TO_DATA_DIRECTORY));
            Files.copy(originalPath, dataDirPath, REPLACE_EXISTING);
        } catch (IOException e) {
            logger.warning(e.getMessage());
            throw new IllegalArgumentException(MESSAGE_DATA_COPY_ERROR);
        }
    }

    /**
     * Returns the file path of the copied photo in the data directory.
     * Intended for {@code ImageView} to reference to the photo.
     */
    public String getPathToDataDirectory() {
        return "file://" + Paths.get(dataDirectory).toAbsolutePath().toUri().getPath();
    }

    //@@author ambervoong
    /**
     * Returns the file path of the copied photo in the data directory.
     * Intended for {@code JsonAdaptedWorker} to retrieve the photo.
     */
    public String getStoragePathToDataDirectory() {
        String path = Paths.get(dataDirectory).toAbsolutePath().toUri().getPath();
        if (path.charAt(0) == '/' && AppUtil.isWindows()) {
            return path.substring(1); // removes the additional leading forward slash
        } else {
            return path; // keeps the leading forward slash required
        }
    }

    //@@author

    /**
     * Loads the example photo from resources into the data directory when the user launches app for the first time.
     */
    private static boolean initExamplePhoto() {
        try {
            Files.createDirectories(Paths.get(PATH_TO_DATA_DIRECTORY));
            InputStream photo = MainApp.class.getResourceAsStream("/images/ExamplePhoto.jpg");
            Files.copy(photo, Paths.get(PATH_TO_EXAMPLE_PHOTO), REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            logger.severe("Unable to load example photo.");
            return false;
        }
    }

    /**
     * Returns true if the photo from {@code pathToPhoto} is a valid photo.
     * Valid photo extensions are jpeg, jpg and png.
     */
    public static boolean isValidPhoto(String pathToPhoto) {
        if (pathToPhoto.isEmpty()) {
            return false;
        } else if (pathToPhoto.equals(PATH_TO_EXAMPLE_PHOTO)) {
            if (!FileUtil.isFileExists(Paths.get(pathToPhoto))) {
                initExamplePhoto(); // create example photo in data directory if doesn't exist
            }
            return true;
        } else if (FileUtil.isFileExists(Paths.get(pathToPhoto))) {
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
//@@author
