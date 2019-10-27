package seedu.address.model.entity.worker;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
public class Photo {

    private static final Logger logger = LogsCenter.getLogger(Photo.class);
    public static final String MESSAGE_CONSTRAINTS =
            "Error in importing display photo: Photo should be less than 2MB or it does not exist.";
    public static final String MESSAGE_DATA_COPY_ERROR =
            "Error in copying photo to the data directory";
    public static final int MAX_SIZE = 2000000;
    public static final String PATH_TO_DATA_DIRECTORY = "data" + File.separator + "photo" + File.separator;

    private String dataDirectory;
    private String originalDirectory;

    public Photo(String pathToPhoto) throws ParseException {
        this.originalDirectory = pathToPhoto;
        File photo = new File(originalDirectory);
        this.dataDirectory = PATH_TO_DATA_DIRECTORY + photo.getName();
        copyToDataDirectory(photo.toPath(), Paths.get(this.dataDirectory));
    }

    private void copyToDataDirectory(Path originalPath, Path dataDirPath) throws ParseException {
        try {
            Files.createDirectories(Paths.get(PATH_TO_DATA_DIRECTORY));
            Files.copy(originalPath, dataDirPath, REPLACE_EXISTING);
        } catch (IOException e) {
            logger.info(e.getMessage());
            throw new ParseException(MESSAGE_DATA_COPY_ERROR);
        }
    }

    public static boolean isValidPhoto(String pathToPhoto) {
        File photo = new File(pathToPhoto);
        if (photo.exists()) {
            return photo.length() <= MAX_SIZE && (pathToPhoto.endsWith(".jpg") || pathToPhoto.endsWith(".png"));
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
