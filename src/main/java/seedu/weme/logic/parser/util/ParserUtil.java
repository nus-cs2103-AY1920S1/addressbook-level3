package seedu.weme.logic.parser.util;

import static java.util.Objects.requireNonNull;
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
import seedu.weme.commons.util.StringUtil;
import seedu.weme.logic.parser.contextparser.ExportParser;
import seedu.weme.logic.parser.contextparser.ImportParser;
import seedu.weme.logic.parser.contextparser.MemeParser;
import seedu.weme.logic.parser.contextparser.TemplateParser;
import seedu.weme.logic.parser.contextparser.WemeParser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.model.DirectoryPath;
import seedu.weme.model.ModelContext;
import seedu.weme.model.imagePath.ImagePath;
import seedu.weme.model.meme.Description;
import seedu.weme.model.tag.Tag;
import seedu.weme.model.template.Name;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_CONTEXT = "Tab provided is not a valid tab.";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_FILEPATH = "File not found or invalid file path given.";
    public static final String MESSAGE_INVALID_DIRECTORYPATH = "Invalid directory path given.";

    public static final MemeParser MEMES_PARSER = new MemeParser();
    public static final TemplateParser TEMPLATE_PARSER = new TemplateParser();
    public static final ImportParser IMPORT_PARSER = new ImportParser();
    public static final ExportParser EXPORT_PARSER = new ExportParser();

    /**
     * Returns a Parser depending on the given ModelContext.
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
        case CONTEXT_STATISTICS:
        case CONTEXT_PREFERENCES:
            // TODO: This is a temporary placeholder until all tabs have been implemented
            return new WemeParser() {
            };
        default:
            throw new IllegalArgumentException();
        }
    }

    /**
     * Parses {@code context} into a {@code ModelContext} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified context is invalid (Not one of the enums).
     */
    public static ModelContext parseContext(String context) throws ParseException {
        String trimmedContext = context.trim();
        if (trimmedContext.equals(CONTEXT_MEMES.getContextName())) {
            return CONTEXT_MEMES;
        } else if (trimmedContext.equals(CONTEXT_TEMPLATES.getContextName())) {
            return CONTEXT_TEMPLATES;
        } else if (trimmedContext.equals(CONTEXT_STATISTICS.getContextName())) {
            return CONTEXT_STATISTICS;
        } else if (trimmedContext.equals(CONTEXT_EXPORT.getContextName())) {
            return CONTEXT_EXPORT;
        } else if (trimmedContext.equals(CONTEXT_IMPORT.getContextName())) {
            return CONTEXT_IMPORT;
        } else if (trimmedContext.equals(CONTEXT_PREFERENCES.getContextName())) {
            return CONTEXT_PREFERENCES;
        }
        throw new ParseException(MESSAGE_INVALID_CONTEXT);
    }

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
     * Parses a {@code String input} into an {@code ImagePath}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static ImagePath parseFilePath(String input) throws ParseException {
        requireNonNull(input);
        String trimmedPath = input.trim();
        if (!ImagePath.isValidFilePath(trimmedPath)) {
            throw new ParseException(MESSAGE_INVALID_FILEPATH);
        }
        return new ImagePath(trimmedPath);
    }

    /**
     * Parses a {@code String input} into a {@code DirectoryPath}.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static DirectoryPath parseDirectoryPath(String input) throws ParseException {
        requireNonNull(input);
        String trimmedPath = input.trim();
        if (!DirectoryPath.isValidDirectoryPath(trimmedPath)) {
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
}
