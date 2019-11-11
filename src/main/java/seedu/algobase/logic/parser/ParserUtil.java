package seedu.algobase.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_DIFFICULTY_RANGE;
import static seedu.algobase.commons.core.Messages.MESSAGE_INVALID_KEYWORD_FORMAT;
import static seedu.algobase.commons.util.CollectionUtil.isArrayOfLength;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.algobase.commons.core.index.Index;
import seedu.algobase.commons.util.FileUtil.Format;
import seedu.algobase.commons.util.StringUtil;
import seedu.algobase.logic.commands.problem.SortCommand;
import seedu.algobase.logic.parser.exceptions.ParseException;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.TabType;
import seedu.algobase.model.plan.PlanDescription;
import seedu.algobase.model.plan.PlanName;
import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Description;
import seedu.algobase.model.problem.Difficulty;
import seedu.algobase.model.problem.Name;
import seedu.algobase.model.problem.Remark;
import seedu.algobase.model.problem.Source;
import seedu.algobase.model.problem.WebLink;
import seedu.algobase.model.searchrule.problemsearchrule.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DescriptionContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.DifficultyIsInRangePredicate;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;
import seedu.algobase.model.searchrule.problemsearchrule.NameContainsKeywordsPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.SourceMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.TagIncludesKeywordsPredicate;
import seedu.algobase.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String DATE_CONSTRAINTS = "DateTime format should be 'yyyy-MM-dd'.";
    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);
    public static final String DEFAULT_COLOR = "#3e7b91";
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
     * Parses a {@code String name} into a {@code PlanName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static PlanName parsePlanName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new PlanName(trimmedName);
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
     * Parses a {@code String description} into an {@code Description}.
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
     * Parses a {@code String description} into an {@code PlanDescription}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static PlanDescription parsePlanDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new PlanDescription(trimmedDescription);
    }

    /**
     * Parses a {@code String weblink} into an {@code WebLink}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weblink} is invalid.
     */
    public static WebLink parseWebLink(String weblink) throws ParseException {
        requireNonNull(weblink);
        String trimmedWebLink = weblink.trim();
        if (!WebLink.isValidWebLink(trimmedWebLink)) {
            throw new ParseException(WebLink.MESSAGE_CONSTRAINTS);
        }
        return new WebLink(trimmedWebLink);
    }

    /**
     * Parses a {@code String difficulty} into an {@code Difficulty}.
     *
     * @throws ParseException if the given {@code difficulty} is invalid.
     */
    public static Difficulty parseDifficulty(String difficulty) throws ParseException {
        requireNonNull(difficulty);
        String trimmedDifficulty = difficulty.trim();
        if (!Difficulty.isValidDifficulty(trimmedDifficulty)) {
            throw new ParseException(Difficulty.MESSAGE_CONSTRAINTS);
        }
        return new Difficulty(trimmedDifficulty);
    }

    /**
     * Parses a {@code String remark} into an {@code Remark}.
     *
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemark(trimmedRemark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }
        return new Remark(remark);
    }

    /**
     * Parses a {@code String source} into an {@code Source}.
     *
     * @throws ParseException if the given {@code source} is invalid.
     */
    public static Source parseSource(String source) throws ParseException {
        String trimmedSource = source.trim();
        if (!Source.isValidSource(trimmedSource)) {
            throw new ParseException(Source.MESSAGE_CONSTRAINTS);
        }
        return new Source(source);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag, String color) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        String trimmedColor = color.trim();
        if (!Tag.isValidTagName(trimmedTag) || tag.isBlank()) {
            throw new ParseException(Tag.MESSAGE_NAME_CONSTRAINTS);
        }
        if (color.isEmpty()) {
            trimmedColor = "#3e7b91";
        }
        return new Tag(trimmedTag, trimmedColor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName, DEFAULT_COLOR));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String method} into a {@code SortingMethod}.
     *
     * @throws ParseException if the given {@code String method} is invalid.
     */
    public static SortCommand.SortingMethod parseSortingMethod(String method) throws ParseException {
        switch (method) {
        case SortCommand.SortingMethod.KEYWORD_NAME:
            return SortCommand.SortingMethod.byName;
        case SortCommand.SortingMethod.KEYWORD_AUTHOR:
            return SortCommand.SortingMethod.byAuthor;
        case SortCommand.SortingMethod.KEYWORD_DIFFICULTY:
            return SortCommand.SortingMethod.byDifficulty;
        case SortCommand.SortingMethod.KEYWORD_SOURCE:
            return SortCommand.SortingMethod.bySource;
        case SortCommand.SortingMethod.KEYWORD_WEBLINK:
            return SortCommand.SortingMethod.byWebLink;
        default:
            throw new ParseException(SortCommand.SortingMethod.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String order} into a {@code SortingOrder}.
     *
     * @throws ParseException if the given {@code String order} is invalid.
     */
    public static SortCommand.SortingOrder parseSortingOrder(String order) throws ParseException {
        switch (order) {
        case SortCommand.SortingOrder.KEYWORD_ASCEND:
            return SortCommand.SortingOrder.ascend;
        case SortCommand.SortingOrder.KEYWORD_DESCEND:
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

    /**
     * Returns true if some of the prefixes contain present {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean hasPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /** Parses a {@code String date} into an {@code LocalDate}.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (DateTimeException ex) {
            throw new ParseException(DATE_CONSTRAINTS);
        }
    }

    /**
     * Checks if the input string parses into a valid tab type.
     */
    private static boolean isValidTabType(String tabType) {
        try {
            TabType.valueOf(tabType.toUpperCase());
            return true;
        } catch (IllegalArgumentException ire) {
            return false;
        }
    }

    /**
     * Checks if the input string parses into a valid tab type index.
     */
    private static boolean isValidTabTypeIndex(String tabType) {
        try {
            Index tabTypeIndex = ParserUtil.parseIndex(tabType);
            TabType tabTypePlaceholder = TabType.values()[tabTypeIndex.getZeroBased()];
            return true;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | ParseException e) {
            return false;
        }
    }

    /** Parses a {@code String tabType} into an {@code TabType}.
     *
     * @throws ParseException if the given {@code string tabType} is invalid.
     */
    public static TabType parseTabType(String tabType, String errorMessage) throws ParseException {
        if (isValidTabType(tabType)) {
            return TabType.valueOf(tabType.toUpperCase());
        }

        if (isValidTabTypeIndex(tabType)) {
            Index tabTypeIndex = Index.fromOneBased(Integer.parseInt(tabType));
            return TabType.values()[tabTypeIndex.getZeroBased()];
        }

        throw new ParseException(errorMessage);
    }

    /** Parses a {@code String tabIndex} into an {@code Index}.
     *
     * @throws ParseException if the given {@code string tabIndex} is invalid.
     */
    public static Index parseTabIndex(String tabIndex, String errorMessage) throws ParseException {
        try {
            return ParserUtil.parseIndex(tabIndex);
        } catch (ParseException pe) {
            throw new ParseException(errorMessage);
        }
    }

    /**
     * Checks if the input string parses into a valid model type.
     */
    private static boolean isValidModelType(String modelType) {
        try {
            ModelType.valueOf(modelType.toUpperCase());
            return true;
        } catch (IllegalArgumentException ire) {
            return false;
        }
    }

    /**
     * Checks if the input string parses into a valid model type index.
     */
    private static boolean isValidModelTypeIndex(String modelType) {
        try {
            Index modelTypeIndex = ParserUtil.parseIndex(modelType);
            ModelType modelTypePlaceholder = ModelType.values()[modelTypeIndex.getZeroBased()];
            return true;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | ParseException e) {
            return false;
        }
    }

    /** Parses a {@code String modelTypeString} into an {@code ModelType}.
     *
     * @throws ParseException if the given {@code string modelTypString} is invalid.
     */
    public static ModelType parseModelType(String modelType, String errorMessage) throws ParseException {
        if (isValidModelType(modelType)) {
            return ModelType.valueOf(modelType.toUpperCase());
        }

        if (isValidTabTypeIndex(modelType)) {
            Index modelTypeIndex = Index.fromOneBased(Integer.parseInt(modelType));
            return ModelType.values()[modelTypeIndex.getZeroBased()];
        }

        throw new ParseException(errorMessage);
    }

    /** Parses a {@code String modelIndex} into an {@code ModelIndex}.
     *
     * @throws ParseException if the given {@code string modelIndex} is invalid.
     */
    public static Index parseModelIndex(String modelIndex, String errorMessage) throws ParseException {
        try {
            return ParserUtil.parseIndex(modelIndex);
        } catch (ParseException pe) {
            throw new ParseException(errorMessage);
        }
    }

    /**
     * Throws {@code ParseException} if a given string is not a valid {@code Keyword} string.
     * @param keyword to be checked
     * @throws ParseException if keyword is invalid.
     */
    private static void checkKeywordString(String keyword) throws ParseException {
        requireNonNull(keyword);
        if (!Keyword.isValidKeyword(keyword)) {
            throw new ParseException(String.format(MESSAGE_INVALID_KEYWORD_FORMAT, Keyword.MESSAGE_CONSTRAINTS));
        }
    }

    /**
     * Throws {@code ParseException} if any string in the given list is not a valid {@code Keyword} string.
     * @param keywords to be checked
     * @throws ParseException if any keyword inside the list is invalid.
     */
    private static void checkKeywordStringList(List<String> keywords) throws ParseException {
        requireNonNull(keywords);
        if (keywords.stream().anyMatch(keyword -> !Keyword.isValidKeyword(keyword))) {
            throw new ParseException(String.format(MESSAGE_INVALID_KEYWORD_FORMAT, Keyword.MESSAGE_CONSTRAINTS));
        }
    }

    /**
     * Returns a list of string by splitting the argValue with empty spaces.
     * @param argValue a string consisting of a list of keywords.
     */
    private static List<String> getArgumentValueAsList(String argValue) {
        String trimmedArg = argValue.trim();
        String[] keywords = trimmedArg.split("\\s+");
        return Arrays.asList(keywords);
    }

    /**
     * Parses a string into a {@code DifficultyIsInRangePredicate}.
     * @param arg argument string to be parsed
     * @param messageUsage target command's usage message
     * @throws ParseException if the argument string is of invalid format.
     */
    public static DifficultyIsInRangePredicate parseDifficultyPredicate(String arg, String messageUsage)
        throws ParseException {
        // Because of how `.split()` works, all trailing consecutive "-" will be ignored:
        // e.g. -1.0-2.0 is invalid because the array is of length 3
        // however 1.0-2.0------ is valid because the trailing consecutive "-" is ignored
        String[] difficultyBounds = arg.split("-");
        if (!isArrayOfLength(difficultyBounds, 2)) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage));
        }
        try {
            double lowerBound = Double.parseDouble(difficultyBounds[0]);
            double upperBound = Double.parseDouble(difficultyBounds[1]);
            return new DifficultyIsInRangePredicate(lowerBound, upperBound);
        } catch (NumberFormatException | NullPointerException nfe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, messageUsage), nfe);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_DIFFICULTY_RANGE, DifficultyIsInRangePredicate.MESSAGE_CONSTRAINTS),
                iae);
        }
    }

    /**
     * Parses a string into a {@code NameContainsKeywordsPredicate}.
     * @param arg argument string to be parsed
     * @throws ParseException if the argument string is of invalid format.
     */
    public static NameContainsKeywordsPredicate parseNamePredicate(String arg)
        throws ParseException {
        List<String> nameKeywords = getArgumentValueAsList(arg);
        checkKeywordStringList(nameKeywords);
        List<Keyword> keywords = nameKeywords.stream().map(Keyword::new).collect(Collectors.toList());
        return new NameContainsKeywordsPredicate(keywords);
    }

    /**
     * Parses a string into a {@code AuthorMatchesKeywordPredicate}.
     * @param authorKeyword argument string to be parsed
     * @throws ParseException if the argument string is of invalid format.
     */
    public static AuthorMatchesKeywordPredicate parseAuthorPredicate(String authorKeyword)
        throws ParseException {
        checkKeywordString(authorKeyword);
        return new AuthorMatchesKeywordPredicate(new Keyword(authorKeyword));
    }

    /**
     * Parses a string into a {@code DescriptionContainsKeywordsPredicate}.
     * @param arg argument string to be parsed
     * @throws ParseException if the argument string is of invalid format.
     */
    public static DescriptionContainsKeywordsPredicate parseDescriptionPredicate(String arg)
        throws ParseException {
        List<String> descriptionKeywords = getArgumentValueAsList(arg);
        checkKeywordStringList(descriptionKeywords);
        List<Keyword> keywords = descriptionKeywords.stream().map(Keyword::new).collect(Collectors.toList());
        return new DescriptionContainsKeywordsPredicate(keywords);
    }

    /**
     * Parses a string into a {@code SourceMatchesKeywordPredicate}.
     * @param sourceKeyword argument string to be parsed
     * @throws ParseException if the argument string is of invalid format.
     */
    public static SourceMatchesKeywordPredicate parseSourcePredicate(String sourceKeyword)
        throws ParseException {
        checkKeywordString(sourceKeyword);
        return new SourceMatchesKeywordPredicate(new Keyword(sourceKeyword));
    }

    /**
     * Parses a string into a {@code TagIncludesKeywordsPredicate}.
     * @param arg argument string to be parsed
     * @throws ParseException if the argument string is of invalid format.
     */
    public static TagIncludesKeywordsPredicate parseTagPredicate(String arg)
        throws ParseException {
        List<String> tagKeywords = getArgumentValueAsList(arg);
        checkKeywordStringList(tagKeywords);
        List<Keyword> keywords = tagKeywords.stream().map(Keyword::new).collect(Collectors.toList());
        return new TagIncludesKeywordsPredicate(keywords);
    }

    /** Parses a {@code String format} into an {@code Format}.
     *
     * @throws ParseException if the given {@code string format} is invalid.
     */
    public static Format parseFileFormat(String format) throws ParseException {
        try {
            return Format.valueOf(format.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.toString());
        }
    }
}
