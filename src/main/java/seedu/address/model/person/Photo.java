package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import javafx.scene.image.Image;

public class Photo {

	private static final String IMAGE_DIRECTORY = "/images/";
	public static final String MESSAGE_CONSTRAINTS = "Image filepath should be of the format local-part.png "
			+ "and adhere to the following constraints:\n"
			+ "1. The local-part should only contain alphanumeric characters and underscores.\n"
			+ "2. This is followed by a '.' and only the image extension 'png' is allowed. ";
	public static final String VALIDATION_REGEX = "^[\\w]+(\\.(?i)(png))$";

	public final String value;
	public final Image photo;

	public Photo() {
		value = "/images/default.png";
		photo =	new Image(this.getClass().getResourceAsStream(value));
	}

	public Photo(String image) {
		requireNonNull(image);
		checkArgument(isValidFilePath(image), MESSAGE_CONSTRAINTS);
		value = image;
		photo = new Image(this.getClass().getResourceAsStream(IMAGE_DIRECTORY + image));
	}

	public static boolean isValidFilePath(String test) {
		return test.matches(VALIDATION_REGEX);
	}

	public String toString() {
		return value;
	}
}
