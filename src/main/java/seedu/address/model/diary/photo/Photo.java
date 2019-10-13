package seedu.address.model.diary.photo;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.AppUtil.getAbsoluteImage;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import javafx.scene.image.Image;

/**
 * Abstraction of a photo containing the path to an image.
 * Also contains other information such as the display name of the photo and the date.
 */
public class Photo {
    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Description can take any values, but it should not be blank";
    public static final String MESSAGE_PATH_CONSTRAINTS =
            "The image path specified should be valid, and must point to an existing file.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The description also cannot be empty.
     */
    private static final String DESCRIPTION_VALIDATION_REGEX = "[^\\s].+";

    private Image image;
    private String description;
    private LocalDateTime dateTaken;

    public Photo(Path imagePath, String description, LocalDateTime dateTaken) {
        requireAllNonNull(image, description, dateTaken);
        validateImagePath(imagePath);
        validateDescription(description);
        this.dateTaken = dateTaken;
    }

    public Photo(String imagePath, String description, LocalDateTime dateTaken) {
        this(Paths.get(imagePath), description, dateTaken);
    }

    public Image getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTaken() {
        return dateTaken;
    }

    /**
     * Validates the given string description and sets the description of the instance if true.
     */
    private void validateDescription(String description) {
        boolean doMatch = description.matches(DESCRIPTION_VALIDATION_REGEX);
        checkArgument(doMatch, MESSAGE_DESCRIPTION_CONSTRAINTS);
        this.description = description;
    }

    private void validateImagePath(Path imagePath) {
        try {
            this.image = getAbsoluteImage(imagePath.toAbsolutePath().toString());
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException(MESSAGE_PATH_CONSTRAINTS);
        }
    }
}
