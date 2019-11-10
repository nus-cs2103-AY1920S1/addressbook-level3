package seedu.weme.logic.parser.util;

import static java.util.Objects.requireNonNull;
import static seedu.weme.model.ModelContext.CONTEXT_CREATE;
import static seedu.weme.model.ModelContext.CONTEXT_EXPORT;
import static seedu.weme.model.ModelContext.CONTEXT_IMPORT;
import static seedu.weme.model.ModelContext.CONTEXT_MEMES;
import static seedu.weme.model.ModelContext.CONTEXT_PREFERENCES;
import static seedu.weme.model.ModelContext.CONTEXT_STATISTICS;
import static seedu.weme.model.ModelContext.CONTEXT_TEMPLATES;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.weme.commons.core.index.Index;
import seedu.weme.commons.util.FileUtil;
import seedu.weme.commons.util.StringUtil;
import seedu.weme.logic.parser.contextparser.CreateParser;
import seedu.weme.logic.parser.contextparser.ExportParser;
import seedu.weme.logic.parser.contextparser.ImportParser;
import seedu.weme.logic.parser.contextparser.MemeParser;
import seedu.weme.logic.parser.contextparser.TemplateParser;
import seedu.weme.logic.parser.contextparser.ViewParser;
import seedu.weme.logic.parser.contextparser.WemeParser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.model.ModelContext;
import seedu.weme.model.meme.Description;
import seedu.weme.model.path.DirectoryPath;
import seedu.weme.model.path.ImagePath;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.Coordinates;
import seedu.weme.model.template.MemeTextColor;
import seedu.weme.model.template.MemeTextSize;
import seedu.weme.model.template.MemeTextStyle;
import seedu.weme.model.template.Name;
import seedu.weme.model.template.exceptions.InvalidCoordinatesException;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_TAB = "Tab provided is not a valid tab.";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_DIRECTORYPATH = "Invalid directory path given.";
    public static final String MESSAGE_INVALID_COORDINATES_CHANGE = "Change must be numbers between -1 and 1 inclusive";

    public static final MemeParser MEMES_PARSER = new MemeParser();
    public static final TemplateParser TEMPLATE_PARSER = new TemplateParser();
    public static final ImportParser IMPORT_PARSER = new ImportParser();
    public static final ExportParser EXPORT_PARSER = new ExportParser();
    public static final CreateParser CREATE_PARSER = new CreateParser();
    public static final ViewParser VIEW_PARSER = new ViewParser();
    public static final WemeParser BASE_PARSER = new WemeParser();

    /**
     * Returns a Parser depending on the given ModelContext.
     *
     * @param modelContext Current context.
     * @return Parser to parse commands with.
     */
    public static WemeParser forContext(ModelContext modelContext) {
        switch (modelContext) {
        case CONTEXT_MEMES:
            return MEMES_PARSER;
        case CONTEXT_IMPORT:
            return IMPORT_PARSER;
        case CONTEXT_EXPORT:
            return EXPORT_PARSER;
        case CONTEXT_TEMPLATES:
            return TEMPLATE_PARSER;
        case CONTEXT_CREATE:
            return CREATE_PARSER;
        case CONTEXT_VIEW:
            return VIEW_PARSER;
        case CONTEXT_STATISTICS:
        case CONTEXT_PREFERENCES:
            return BASE_PARSER;
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * Parses {@code context} into a {@code ModelContext} that has its own tab and returns it.
     * <p>
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified context does not corresponds to a tab
     */
    public static ModelContext parseTab(String context) throws ParseException {
        String trimmedContext = context.trim();
        if (trimmedContext.equals(CONTEXT_MEMES.getContextName())) {
            return CONTEXT_MEMES;
        } else if (trimmedContext.equals(CONTEXT_TEMPLATES.getContextName())) {
            return CONTEXT_TEMPLATES;
        } else if (trimmedContext.equals(CONTEXT_CREATE.getContextName())) {
            return CONTEXT_CREATE;
        } else if (trimmedContext.equals(CONTEXT_STATISTICS.getContextName())) {
            return CONTEXT_STATISTICS;
        } else if (trimmedContext.equals(CONTEXT_EXPORT.getContextName())) {
            return CONTEXT_EXPORT;
        } else if (trimmedContext.equals(CONTEXT_IMPORT.getContextName())) {
            return CONTEXT_IMPORT;
        } else if (trimmedContext.equals(CONTEXT_PREFERENCES.getContextName())) {
            return CONTEXT_PREFERENCES;
        }
        throw new ParseException(MESSAGE_INVALID_TAB);
    }

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
     * Parses {@code x} and {@code y} into a {@code Coordinates} and returns it.
     *
     * @throws ParseException if x or y is not between 0 to 1
     */
    public static Coordinates parseCoordinates(String x, String y) throws ParseException {
        try {
            return new Coordinates(parseCoordinate(x), parseCoordinate(y));
        } catch (InvalidCoordinatesException e) {
            throw new ParseException(Coordinates.MESSAGE_INVALID_COORDINATES, e);
        }
    }

    /**
     * Parses {@code coordinate} into a float suitable for use as a coordinate for {@code Coordinates} and returns it.
     *
     * @throws ParseException if coordinate is not between 0 to 1
     */
    public static float parseCoordinate(String coordinate) throws ParseException {
        float val;
        try {
            val = Float.parseFloat(coordinate);
        } catch (NumberFormatException nfe) {
            throw new ParseException(Coordinates.MESSAGE_INVALID_COORDINATES);
        }
        if (val < 0 || val > 1) {
            throw new ParseException(Coordinates.MESSAGE_INVALID_COORDINATES);
        }
        return val;
    }

    /**
     * Parses {@code change} into a float representing a change in {@code Coordinates}.
     * @throws ParseException if change cannot be parsed as as number
     */
    public static float parseCoordinateChange(String change) throws ParseException {
        float val;
        try {
            val = Float.parseFloat(change);
        } catch (NumberFormatException nfe) {
            throw new ParseException(MESSAGE_INVALID_COORDINATES_CHANGE);
        }
        if (val < -1 || val > 1) {
            throw new ParseException(MESSAGE_INVALID_COORDINATES_CHANGE);
        }
        return val;
    }

    /**
     * Parses a {@code String input} into an {@code ImagePath}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static ImagePath parseFilePath(String input) throws ParseException {
        requireNonNull(input);
        String trimmedPath = input.trim();
        if (!ImagePath.isValidFilePath(trimmedPath)) {
            throw new ParseException(ImagePath.MESSAGE_CONSTRAINTS);
        }
        return new ImagePath(trimmedPath);
    }

    /**
     * Parses a {@code String input} into a {@code DirectoryPath}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static DirectoryPath parseDirectoryPath(String input) throws ParseException {
        requireNonNull(input);
        String trimmedPath = input.trim();
        if (!FileUtil.isValidDirectoryPath(trimmedPath)) {
            throw new ParseException(MESSAGE_INVALID_DIRECTORYPATH);
        }
        return new DirectoryPath(trimmedPath);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
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
     * Parses {@code color} into a {@code MemeTextColor}.
     */
    public static MemeTextColor parseMemeTextColor(String color) throws ParseException {
        requireNonNull(color);
        String trimmedColor = color.trim();
        if (!MemeTextColor.isValidMemeTextColor(trimmedColor)) {
            throw new ParseException(MemeTextColor.MESSAGE_CONSTRAINTS);
        }
        return new MemeTextColor(trimmedColor);
    }

    /**
     * Parses {@code Collection<String> styles} into a {@code Set<MemeTextStyle>}.
     */
    public static Set<MemeTextStyle> parseMemeTextStyles(Collection<String> styles) throws ParseException {
        requireNonNull(styles);
        final Set<MemeTextStyle> styleSet = new HashSet<>();
        for (String style : styles) {
            String trimmedStyle = style.trim().toLowerCase();
            if (!MemeTextStyle.isValidMemeTextStyle(trimmedStyle)) {
                throw new ParseException(MemeTextStyle.MESSAGE_CONSTRAINTS);
            }
            styleSet.add(new MemeTextStyle(trimmedStyle));
        }
        return styleSet;
    }

    /**
     * Parses {@code size} into a {@code MemeTextSize}.
     */
    public static MemeTextSize parseMemeTextSize(String size) throws ParseException {
        requireNonNull(size);
        String trimmedSize = size.trim();
        if (!MemeTextSize.isValidMemeTextSize(trimmedSize)) {
            throw new ParseException(MemeTextSize.MESSAGE_CONSTRAINTS);
        }
        return new MemeTextSize(trimmedSize);
    }
}
