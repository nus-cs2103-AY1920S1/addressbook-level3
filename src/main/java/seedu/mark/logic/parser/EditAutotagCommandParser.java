package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_FOLDER;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOT_FOLDER;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOT_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NOT_URL;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_URL;

import java.util.List;

import seedu.mark.logic.commands.EditAutotagCommand;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.predicates.BookmarkPredicate;

/**
 * Parses input arguments and creates a new EditAutotagCommand object
 */
public class EditAutotagCommandParser implements Parser<EditAutotagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditAutotagCommand
     * and returns an EditAutotagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAutotagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_NAME, PREFIX_URL, PREFIX_NOT_NAME, PREFIX_NOT_URL,
                        PREFIX_FOLDER, PREFIX_NOT_FOLDER);

        if (argMultimap.getPreamble().isEmpty() || argMultimap.getPreamble().split("\\s+").length > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAutotagCommand.MESSAGE_USAGE));
        }

        String tagNameToEdit = argMultimap.getPreamble();

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

        String newTagName = tagNameToEdit;
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            List<String> values = argMultimap.getAllValues(PREFIX_TAG);
            if (values.size() != 1) {
                throw new ParseException(EditAutotagCommand.MESSAGE_MULTIPLE_TAG_NAMES);
            }
            newTagName = values.get(0); // only one tag name
        }

        if (newTagName.equals(tagNameToEdit) && predicate.isEmpty()) {
            throw new ParseException(EditAutotagCommand.MESSAGE_NO_FIELD_EDITED);
        }

        SelectiveBookmarkTagger newTagger = new SelectiveBookmarkTagger(ParserUtil.parseTag(newTagName), predicate);

        return new EditAutotagCommand(tagNameToEdit, newTagger);
    }

    /**
     * Checks that there is no empty value in the given list.
     *
     * @param values Strings to check.
     * @throws ParseException if any String in {@code values} is blank.
     */
    private static void checkValuesNonEmpty(List<String> values) throws ParseException {
        if (values.stream().anyMatch(String::isBlank)) {
            throw new ParseException(EditAutotagCommand.MESSAGE_CONDITION_EMPTY);
        }
    }

}
