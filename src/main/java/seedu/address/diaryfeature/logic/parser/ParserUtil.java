package seedu.address.diaryfeature.logic.parser;


import java.util.Date;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.diaryfeature.logic.parser.exceptions.DateParseException;
import seedu.address.diaryfeature.logic.parser.exceptions.EmptyArgumentException;
import seedu.address.diaryfeature.logic.parser.exceptions.MemoryParseException;
import seedu.address.diaryfeature.logic.parser.exceptions.PlaceParseException;
import seedu.address.diaryfeature.logic.parser.exceptions.TitleParseException;
import seedu.address.diaryfeature.model.diaryEntry.DateFormatter;
import seedu.address.diaryfeature.model.diaryEntry.Memory;
import seedu.address.diaryfeature.model.diaryEntry.Place;
import seedu.address.diaryfeature.model.diaryEntry.Title;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer. " +
            "Has to be 1 or more.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
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
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     */
    public static Title parseTitle(String title) throws TitleParseException {
        if(Validators.isNotNull(title)) {
            String trimmed = title.trim();
            if (Validators.isValidTitle(title)) {
                return new Title(trimmed);
            }
        }
        //So if the input is null or if it's not valid, then throw the title error
        throw new TitleParseException();
        }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
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
         *
         */
    public static Place parsePlace(String place) throws PlaceParseException  {
        if(Validators.isNotNull(place)) {
            String trimmed = place.trim();
            if (Validators.isNotEmpty(trimmed)) {
                return new Place(trimmed);
            }
        }
        //So if the input is null or if it's not valid, then throw the title error
        throw new PlaceParseException();
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     */
    public static Memory parseMemory(String memory) throws MemoryParseException {
        if(Validators.isNotNull(memory)) {
            String trimmed = memory.trim();
            if (Validators.isNotEmpty(trimmed)) {
                return new Memory(trimmed);
            }
        }
        //So if the input is null or if it's not valid, then throw the memory error
        throw new MemoryParseException();
    }

    public static String parseStringArgs(String input,String parserName) throws EmptyArgumentException {
        if(Validators.isNotNull(input)) {
            String trimmed = input.trim();
            if (Validators.isNotEmpty(trimmed)) {
                return trimmed;
            }
        }
        //So if the input is null or if it's not valid, then throw the memory error
        throw new EmptyArgumentException(parserName);
    }


}
