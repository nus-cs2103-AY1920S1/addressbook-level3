package seedu.address.model.common;

import static seedu.address.commons.util.AppUtil.getAbsoluteImage;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.image.Image;

import seedu.address.commons.util.AppUtil;

/**
 * Abstraction of a photo in trip containing the path to an image, and a cached JavaFX instance of
 * {@link Image}.
 */
public class Photo {

    public static final String MESSAGE_PATH_CONSTRAINTS =
            "The image path specified should be valid, and must point to an existing file.\n"
                    + "NOTE: A placeholder image will be used if the file path becomes invalid in the future!";

    private static final String INVALID_FILE_PATH = "/images/invalidPhotoPlaceholder.png";

    protected String imageFilePath;
    private Image image;

    /**
     * Constructs a {@code Photo} from the given arguments.
     *
     * @param imagePath The {@link Path} to point to the image file.
     * @throws IllegalArgumentException If the {@code imagePath} violates the constraints.
     */
    public Photo(Path imagePath) {
        requireAllNonNull(imagePath);
        validateImagePath(imagePath);
    }

    public Photo(String imagePath) {
        this(Paths.get(imagePath));
    }

    public Image getImage() {
        return image;
    }


    public String getImageFilePath() {
        return imageFilePath;
    }


    /**
     * Validates the given path, setting the {@code image} property of this instance if valid.
     * Otherwise, the {@code image} is set to a placeholder image indicated by {@code INVALID_FILE_PATH}.
     */
    private void validateImagePath(Path imagePath) {
        try {
            this.imageFilePath = imagePath.toAbsolutePath().toString();
            this.image = getAbsoluteImage(imageFilePath);
        } catch (IllegalArgumentException | FileNotFoundException ex) {
            this.image = AppUtil.getImage(INVALID_FILE_PATH);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(" File path: ")
                .append(imageFilePath);

        return builder.toString();
    }

    /**
     * Checks for equality to the specified {@code obj}.
     * Does not check the JavaFx {@code image} equality.
     *
     * @param obj The other object to check.
     * @return True if the object has reference equality, or it is an instance of {@code Photo} and each
     * of its fields have soft equality, except {@code image}.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Photo)) {
            return false;
        }

        Photo otherPhoto = (Photo) obj;

        return imageFilePath.equals(otherPhoto.imageFilePath);
    }
}
