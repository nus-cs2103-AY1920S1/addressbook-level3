package seedu.address.logic.parser.itinerary;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.FileUtil.isFileExists;
import static seedu.address.commons.util.FileUtil.isValidPath;

import java.io.File;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.inventory.Inventory;
import seedu.address.model.itinerary.Budget;
import seedu.address.model.itinerary.Description;
import seedu.address.model.itinerary.Location;
import seedu.address.model.itinerary.Name;
import seedu.address.model.itinerary.Photo;

/**
 * Utility class for itinerary.
 * WARNING INCOMPLETE: IMPLEMENT PARSING FOR BOOKING/INVENTORY HERE
 */
public abstract class ItineraryParserUtil {
    private static final Pattern ALLOWED_IMAGE_EXTENSIONS =
            Pattern.compile("(?:.*)(?<extension>\\.(png|jpg|jpeg))$");

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String location} into a {@code Location}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Location} is invalid.
     */
    public static Location parseLocation(String location) throws ParseException {
        requireNonNull(location);
        String trimmedLocation = location.trim();
        if (!Location.isValidLocation(trimmedLocation)) {
            throw new ParseException(Location.MESSAGE_CONSTRAINTS);
        }
        return new Location(trimmedLocation);
    }

    /**
     * Parses a {@code String budget} into a {@code Budget}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Budget} is invalid.
     */
    public static Budget parseBudget(String budget) throws ParseException {
        requireNonNull(budget);
        String trimmedBudget = budget.trim();
        if (!Budget.isValidBudget(trimmedBudget)) {
            throw new ParseException(Budget.MESSAGE_CONSTRAINTS);
        }
        return new Budget(trimmedBudget);
    }

    public static Inventory parseAddInventory(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!seedu.address.model.inventory.Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Inventory(new seedu.address.model.inventory.Name(trimmedName), false, -1);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the {@code Description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }


    /**
     * Parses the file path to be used for the photo from the {@code argMultimap}.
     * If not specified, it uses the path of the {@code imageFile}.
     * Assumes the alternative {@code imageFile} exists and is of a valid extension if non-null.
     *
     * @param path The input {@link java.nio.file.Path} given by user.
     * @param imageFile The {@link File} image file.
     * @return The file path to use for the photo.
     * @throws ParseException If the data file path prefix is not specified and the imageFile is {@code null},
     * or the file specified at the given data file path does not exist or is invalid.
     */
    public static Photo parseFilePath(String path, File imageFile) throws ParseException {
        //short-circuit if alternative is present
        if (imageFile != null) {
            return new Photo(imageFile.toPath().toAbsolutePath());
        }

        // Checks if path is valid and exists
        boolean doesFileExist = isValidPath(path) && isFileExists(Paths.get(path));
        if (!doesFileExist) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Photo.MESSAGE_PATH_CONSTRAINTS));
        }


        boolean isFileExtensionValid = ALLOWED_IMAGE_EXTENSIONS
                .matcher(Paths.get(path).getFileName().toString()).matches();
        if (!isFileExtensionValid) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Photo.MESSAGE_PATH_CONSTRAINTS));
        }

        return new Photo(path);
    }


}
