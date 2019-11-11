package seedu.address.diaryfeature.logic.parser;


import java.util.Date;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions.DateParseException;
import seedu.address.diaryfeature.logic.parser.exceptions.DetailParseException;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions.MemoryParseException;
import seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions.PlaceParseException;
import seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions.TitleParseException;
import seedu.address.diaryfeature.model.diaryEntry.DateFormatter;
import seedu.address.diaryfeature.model.diaryEntry.Memory;
import seedu.address.diaryfeature.model.diaryEntry.Place;
import seedu.address.diaryfeature.model.diaryEntry.Title;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    private static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer. " +
            "Has to be 1 or more.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Gives a validated Title
     *
     * @param title input from user
     * @return A validated Title
     * @throws TitleParseException if the title is invalid
     */
    public static Title parseTitle(String title) throws TitleParseException {
        if (Validators.isNotNull(title)) {
            String trimmed = title.trim();
            if (Validators.isValidTitle(title)) {
                return new Title(trimmed);
            }
        }
        //So if the input is null or if it's not valid, then throw the title error
        throw new TitleParseException();
    }

    /**
     * Gives a validated Date
     *
     * @param date input from user
     * @return A validated Date
     * @throws DateParseException if the date is invalid
     */
    public static Date parseDate(String date) throws DateParseException {
        if (Validators.isNotNull(date)) {
            String trimmedDate = date.trim();
            if (Validators.isNotEmpty(date)) {
                if (Validators.isCorrectDateFormat(date)) {
                    return DateFormatter.convertToDate(trimmedDate);
                }
            }
        }
        throw new DateParseException();
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Place parsePlace(String place) throws PlaceParseException {
        if (Validators.isNotNull(place)) {
            String trimmed = place.trim();
            if (Validators.isValidPlace(trimmed)) {
                return new Place(trimmed);
            }
        }
        //So if the input is null or if it's not valid, then throw the title error
        throw new PlaceParseException();
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Memory parseMemory(String memory) throws MemoryParseException {
        if (Validators.isNotNull(memory)) {
            String trimmed = memory.trim();
            if (Validators.isValidMemory(trimmed)) {
                return new Memory(trimmed);
            }
        }
        //So if the input is null or if it's not valid, then throw the memory error
        throw new MemoryParseException();
    }

    /**
     * Generic method to make sure that the input is not empty or null
     *
     * @param input      input from user
     * @param parserName Specific to the parserName
     * @return the validated String
     * @throws EmptyArgumentException if the input is invalid
     */

    public static String parseStringArgs(String input, String parserName) throws EmptyArgumentException {
        if (Validators.isNotNull(input)) {
            String trimmed = input.trim();
            if (Validators.isNotEmpty(trimmed)) {
                return trimmed;
            }
        }
        //So if the input is null or if it's not valid, then throw the memory error
        throw new EmptyArgumentException(parserName);
    }

    /**
     * Checks if the input details are valid
     *
     * @param input      input from user
     * @param parserName specific to the parser
     * @return the validated String
     * @throws EmptyArgumentException if the input is empty
     * @throws DetailParseException   if the input does not match the Detail constraints
     */

    public static String parseDetail(String input, String parserName) throws EmptyArgumentException, DetailParseException {
        String user = parseStringArgs(input, parserName);
        if (Validators.isValidDetail(user)) {
            return Encryptor.encrypt(user);
        } else {
            throw new DetailParseException();
        }
    }

    public static boolean isValidEncryptedDetail(String input) {
        if (Validators.isNotNull(input)) {
            return Validators.isValidEncryptedDetail(input);
        } else {
            return false;
        }

    }
}
