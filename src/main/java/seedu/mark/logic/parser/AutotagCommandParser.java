package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_FOLDER;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOT_FOLDER;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOT_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOT_URL;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_URL;

import java.util.List;

import seedu.mark.logic.commands.AutotagCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.predicates.BookmarkPredicate;
import seedu.mark.model.tag.Tag;

/**
 * Parses input arguments and creates a new AutotagCommand object
 */
public class AutotagCommandParser implements Parser<AutotagCommand> {

    /**
     * Creates a {@link BookmarkPredicate} based on the values present in the
     * given {@code argMultimap}.
     *
     * @param argMultimap An {@code ArgumentMultimap} containing values that the
     *                    new predicate should contain, if any.
     * @return {@code BookmarkPredicate} containing the given values.
     * @throws ParseException if any of the relevant values in the
     *                        {@code argMultimap} is an empty String.
     */
    public static BookmarkPredicate makePredicateFromMultimap(ArgumentMultimap argMultimap) throws ParseException {
        BookmarkPredicate predicate = new BookmarkPredicate();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> keywords = argMultimap.getAllValues(PREFIX_NAME);
            checkValuesNonEmpty(keywords);
            predicate = predicate.withNameKeywords(keywords);
        }
        if (argMultimap.getValue(PREFIX_URL).isPresent()) {
            List<String> keywords = argMultimap.getAllValues(PREFIX_URL);
            checkValuesNonEmpty(keywords);
            predicate = predicate.withUrlKeywords(keywords);
        }
        if (argMultimap.getValue(PREFIX_FOLDER).isPresent()) {
            List<String> folderNames = argMultimap.getAllValues(PREFIX_FOLDER);
            checkValuesNonEmpty(folderNames);
            predicate = predicate.withFolder(folderNames);
        }
        if (argMultimap.getValue(PREFIX_NOT_NAME).isPresent()) {
            List<String> keywords = argMultimap.getAllValues(PREFIX_NOT_NAME);
            checkValuesNonEmpty(keywords);
            predicate = predicate.withoutNameKeywords(keywords);
        }
        if (argMultimap.getValue(PREFIX_NOT_URL).isPresent()) {
            List<String> keywords = argMultimap.getAllValues(PREFIX_NOT_URL);
            checkValuesNonEmpty(keywords);
            predicate = predicate.withoutUrlKeywords(keywords);
        }
        if (argMultimap.getValue(PREFIX_NOT_FOLDER).isPresent()) {
            List<String> folderNames = argMultimap.getAllValues(PREFIX_NOT_FOLDER);
            checkValuesNonEmpty(folderNames);
            predicate = predicate.withoutFolder(folderNames);
        }
        return predicate;
    }

    /**
     * Checks that there is no empty value in the given list.
     *
     * @param values Strings to check.
     * @throws ParseException if any String in {@code values} is blank.
     */
    private static void checkValuesNonEmpty(List<String> values) throws ParseException {
        if (values.stream().anyMatch(String::isBlank)) {
            throw new ParseException(AutotagCommand.MESSAGE_CONDITION_EMPTY);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AutotagCommand
     * and returns an AutotagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AutotagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_URL, PREFIX_NOT_NAME, PREFIX_NOT_URL,
                        PREFIX_FOLDER, PREFIX_NOT_FOLDER);

        if (argMultimap.getPreamble().isEmpty() || argMultimap.getPreamble().split("\\s+").length > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AutotagCommand.MESSAGE_USAGE));
        }

        Tag tagToApply = ParserUtil.parseTag(argMultimap.getPreamble());

        BookmarkPredicate predicate = makePredicateFromMultimap(argMultimap);

        if (predicate.isEmpty()) {
            throw new ParseException(AutotagCommand.MESSAGE_NO_CONDITION_SPECIFIED);
        }

        SelectiveBookmarkTagger tagger = new SelectiveBookmarkTagger(tagToApply, predicate);

        return new AutotagCommand(tagger);
    }
}
