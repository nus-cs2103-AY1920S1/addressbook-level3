package seedu.system.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Date;
import java.util.stream.Stream;

import seedu.system.commons.core.index.Index;
import seedu.system.commons.util.StringUtil;
import seedu.system.logic.commands.RankMethod;
import seedu.system.logic.parser.exceptions.ParseException;
import seedu.system.model.person.CustomDate;
import seedu.system.model.person.Gender;
import seedu.system.model.person.Name;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

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
     * Parses a {@code String date} into a {@code date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @return CustomeDate object with date trimmed.
     * @throws ParseException if the given {@code dateOfBirth} is invalid.
     */
    public static CustomDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!CustomDate.isValidDate(trimmedDate)) {
            throw new ParseException(CustomDate.MESSAGE_CONSTRAINTS);
        }
        return new CustomDate(trimmedDate.toLowerCase());
    }

    /**
     * Parses a {@code String gender} into an {@code Gender}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code gender} is invalid.
     */
    public static Gender parseGender(String gender) throws ParseException {
        requireNonNull(gender);
        String trimmedGender = gender.trim();
        if (!Gender.isValidGender(trimmedGender)) {
            throw new ParseException(Gender.MESSAGE_CONSTRAINTS);
        }
        return Gender.getGenderCorrespondingToName(gender.toLowerCase());
    }

    /**
     * Parses a {@code String rankMethod} into an {@code RankMethod}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code rankMethod} is invalid.
     */
    public static RankMethod parseRankMethod(String rankMethod) throws ParseException {
        requireNonNull(rankMethod);
        String trimmedRankMethod = rankMethod.trim();
        if (!RankMethod.isValidRankMethod(trimmedRankMethod)) {
            throw new ParseException(RankMethod.MESSAGE_CONSTRAINTS);
        }
        return RankMethod.getRankMethodCorrespondingToName(rankMethod.toLowerCase());
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
    //@@author HoWeiChin
    /**
     * Returns true if {@code startDate} is exactly or before {@code endDate}
     */
    public static boolean isBefore(CustomDate startDate, CustomDate endDate) {
        Date start = startDate.getDate();
        Date end = endDate.getDate();
        return start.equals(end) || start.before(end);
    }

}
