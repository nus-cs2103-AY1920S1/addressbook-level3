package seedu.algobase.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.commons.util.StringUtil;
import seedu.algobase.logic.commands.SortCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Difficulty;
import seedu.algobase.model.problem.Name;
import seedu.algobase.model.problem.Remark;
import seedu.algobase.model.problem.Source;
import seedu.algobase.model.problem.WebLink;
import seedu.algobase.model.tag.Tag;

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
     * Parses a {@code String name} into a {@code PlanName}.
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
     * Parses a {@code String author} into a {@code Author}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code author} is invalid.
     */
    public static Author parseAuthor(String author) throws ParseException {
        requireNonNull(author);
        String trimmedAuthor = author.trim();
        if (!Author.isValidAuthor(trimmedAuthor)) {
            throw new ParseException(Author.MESSAGE_CONSTRAINTS);
        }
        return new Author(trimmedAuthor);
    }

    /**
     * Parses a {@code String description} into an {@code PlanDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
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
     * Parses a {@code String weblink} into an {@code WebLink}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weblink} is invalid.
     */
    public static WebLink parseWeblink(String weblink) throws ParseException {
        requireNonNull(weblink);
        String trimmedWeblink = weblink.trim();
        if (!WebLink.isValidWeblink(trimmedWeblink)) {
            throw new ParseException(WebLink.MESSAGE_CONSTRAINTS);
        }
        return new WebLink(trimmedWeblink);
    }

    /**
     * Parses a {@code String difficulty} into an {@code Difficulty}.
     *
     * @throws ParseException if the given {@code difficulty} is invalid.
     */
    public static Difficulty parseDifficulty(String difficulty) throws ParseException {
        // TODO: implement parse difficulty
        return Difficulty.DEFAULT_DIFFICULTY;
    }

    /**
     * Parses a {@code String remark} into an {@code Remark}.
     *
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        // TODO: implementation
        return new Remark(remark);
    }

    /**
     * Parses a {@code String source} into an {@code Source}.
     *
     * @throws ParseException if the given {@code source} is invalid.
     */
    public static Source parseSource(String source) throws ParseException {
        // TODO: implementation
        return new Source(source);
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

    /**
     * Parses a {@code String method} into a {@code SortingMethod}.
     *
     * @throws ParseException
     */
    public static SortCommand.SortingMethod parseSortingMethod(String method) throws ParseException {
        switch (method) {
        case "name":
            return SortCommand.SortingMethod.byName;
        case "author":
            return SortCommand.SortingMethod.byAuthor;
        case "difficulty":
            return SortCommand.SortingMethod.byDifficulty;
        case "source":
            return SortCommand.SortingMethod.bySource;
        case "weblink":
            return SortCommand.SortingMethod.byWebLink;
        default:
            throw new ParseException(SortCommand.SortingMethod.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String order} into a {@code SortingOrder}.
     *
     * @throws ParseException
     */
    public static SortCommand.SortingOrder parseSortingOrder(String order) throws ParseException {
        switch (order) {
        case "ascend":
            return SortCommand.SortingOrder.ascend;
        case "descend":
            return SortCommand.SortingOrder.descend;
        default:
            throw new ParseException(SortCommand.SortingOrder.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
