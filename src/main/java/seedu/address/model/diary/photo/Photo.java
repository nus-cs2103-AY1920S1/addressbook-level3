package seedu.address.model.diary.photo;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.AppUtil.getAbsoluteImage;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.FileUtil.isFileExists;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import javafx.scene.image.Image;

import seedu.address.logic.parser.ParserDateUtil;

/**
 * Abstraction of a photo containing the path to an image.
 * Also contains other information such as the display name of the photo and the date.
 */
public class Photo {
    public static final String MESSAGE_DESCRIPTION_CONSTRAINTS =
            "Description can take any values, but it should not be blank,"
                    + " and must have a maximum length of 20. (Keep your descriptions short and sweet!)";
    public static final String MESSAGE_PATH_CONSTRAINTS =
            "The image path specified should be valid, and must point to an existing file."
                    + "You entered: %1$s";
    public static final String MESSAGE_IMAGE_CONSTRAINTS = "The file should point to a valid png or jpg image file. "
            + "You entered: %1$s";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The description also cannot be empty.
     */
    private static final String DESCRIPTION_VALIDATION_REGEX = "[^\\s].{1,20}$";

    private Image image;
    private String imageFilePath;
    private String description;
    private LocalDateTime dateTaken;

    public Photo(Path imagePath, String description, LocalDateTime dateTaken) {
        requireAllNonNull(imagePath, description, dateTaken);
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

    public String getImageFilePath() {
        return imageFilePath;
    }

    public LocalDateTime getDateTaken() {
        return dateTaken;
    }

    /**
     * Validates the given string description and sets the description of the instance if true.
     *
     * @throws IllegalArgumentException If the description does not match the {@code DESCRIPTION_VALIDATION_REGEX}.
     */
    private void validateDescription(String description) {
        boolean doMatch = description.matches(DESCRIPTION_VALIDATION_REGEX);
        checkArgument(doMatch, MESSAGE_DESCRIPTION_CONSTRAINTS);
        this.description = description;
    }

    /**
     * Validates the given path, setting the {@code image} property of this instance if valid.
     *
     * @throws IllegalArgumentException If the path does not point to a valid image file.
     */
    private void validateImagePath(Path imagePath) {
        if (!isFileExists(imagePath))  {
            throw new IllegalArgumentException(String.format(MESSAGE_PATH_CONSTRAINTS, imagePath.toAbsolutePath()));
        }

        try {
            this.imageFilePath = imagePath.toAbsolutePath().toString();
            this.image = getAbsoluteImage(imageFilePath);
        } catch (IllegalArgumentException | FileNotFoundException ex) {
            throw new IllegalArgumentException(String.format(MESSAGE_IMAGE_CONSTRAINTS, imagePath.toAbsolutePath()));
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Photo description : ")
                .append(description)
                .append(" File path: ")
                .append(imageFilePath)
                .append(" Date Taken: ")
                .append(ParserDateUtil.getDisplayTime(dateTaken));

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

        return description.equals(otherPhoto.description)
                && imageFilePath.equals(otherPhoto.description)
                && dateTaken.isEqual(otherPhoto.dateTaken);
    }
}
