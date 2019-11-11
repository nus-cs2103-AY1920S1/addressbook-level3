package mams.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import mams.commons.core.index.Index;
import mams.commons.util.StringUtil;
import mams.logic.parser.exceptions.ParseException;
import mams.model.student.Credits;
import mams.model.student.MatricId;
import mams.model.student.Name;
import mams.model.student.PrevMods;
import mams.model.tag.Tag;

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
     * Parses a {@code String credits} into a {@code Credits}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code credits} is invalid.
     */
    public static Credits parseCredits(String credits) throws ParseException {
        requireNonNull(credits);
        String trimmedCredits = credits.trim();
        if (!Credits.isValidCredits(trimmedCredits)) {
            throw new ParseException(Credits.MESSAGE_CONSTRAINTS);
        }
        return new Credits(trimmedCredits);
    }

    /**
     * Parses a {@code String matricid} into an {@code matricid}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code matricId} is invalid.
     */
    public static MatricId parseMatricId(String matricId) throws ParseException {
        requireNonNull(matricId);
        String trimmedMatricId = matricId.trim();
        if (!MatricId.isValidMatricId(trimmedMatricId)) {
            throw new ParseException(MatricId.MESSAGE_CONSTRAINTS);
        }
        return new MatricId(trimmedMatricId);
    }

    /**
     * Parses a {@code String prevMods} into an {@code PrevMods}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code prevMods} is invalid.
     */
    public static PrevMods parsePrevMods(String prevMods) throws ParseException {
        requireNonNull(prevMods);
        String trimmedPrevMods = prevMods.trim();
        if (!PrevMods.isValidPrevMods(trimmedPrevMods)) {
            throw new ParseException(PrevMods.MESSAGE_CONSTRAINTS);
        }
        return new PrevMods(trimmedPrevMods);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
