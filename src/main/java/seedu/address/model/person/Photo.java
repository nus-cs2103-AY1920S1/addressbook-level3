package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.File;

/**
 *  Represents a Person's photo in the address book.
 *  Guarantees: immutable; is valid as declared in {@link #isValidFilePath(String)}
 */
public class Photo {


    public static final String MESSAGE_CONSTRAINTS = "Image filepath should be of the format local-part.png "
            + "and adhere to the following constraints:\n"
            + "1. The local-part should only contain alphanumeric characters and underscores.\n"
            + "2. This is followed by a '.' and only the image extension 'png' is allowed. ";
    public static final String NO_FILE_EXIST_MESSAGE = "Image file does not exist. Make sure image file is in the "
            + "`images` folder.";
    public static final String VALIDATION_REGEX = "^[\\w]+(\\.(?i)(png))$";
    private static final String IMAGE_DIRECTORY = "images/";
    public final String value;
    public final String filePath;
    private final File imageFile;


    public Photo() {
        value = "default.png";
        imageFile = new File(IMAGE_DIRECTORY + value);
        filePath = "file://" + imageFile.toURI().getPath();
    }

    public Photo(String image) {
        requireNonNull(image);
        checkArgument(isValidFilePath(image), MESSAGE_CONSTRAINTS);
        value = image;
        imageFile = new File(IMAGE_DIRECTORY + value);
        filePath = "file://" + imageFile.toURI().getPath();
    }

    public static boolean isValidFilePath(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks whether image file exists in image folder
     * @param test file name of image file
     */
    public static boolean isFileExist(String test) {
        File filePath = new File(IMAGE_DIRECTORY + test);
        return filePath.exists();

    }

    public String toString() {
        return value;
    }
}
