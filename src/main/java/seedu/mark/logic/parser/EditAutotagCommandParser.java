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
 * Parses input arguments and creates a new EditAutotagCommand object. An {@code EditAutotagCommandParser}
 * makes use of the method {@link AutotagCommandParser#makePredicateFromMultimap(ArgumentMultimap)}
 * when parsing the arguments.
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

        String tagNameToEdit = argMultimap.getPreamble();
        boolean hasInvalidTagName = tagNameToEdit.isEmpty() || tagNameToEdit.split("\\s+").length > 1;
        if (hasInvalidTagName) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAutotagCommand.MESSAGE_USAGE));
        }

        BookmarkPredicate predicate = AutotagCommandParser.makePredicateFromMultimap(argMultimap);

        String newTagName = getNewTagName(tagNameToEdit, argMultimap);

        boolean hasNotBeenEdited = newTagName.equals(tagNameToEdit) && predicate.isEmpty();
        if (hasNotBeenEdited) {
            throw new ParseException(EditAutotagCommand.MESSAGE_NO_FIELD_EDITED);
        }

        SelectiveBookmarkTagger newTagger = new SelectiveBookmarkTagger(ParserUtil.parseTag(newTagName), predicate);

        return new EditAutotagCommand(tagNameToEdit, newTagger);
    }

    private String getNewTagName(String originalTagName, ArgumentMultimap argMultimap) throws ParseException {
        String newTagName = originalTagName;
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            List<String> values = argMultimap.getAllValues(PREFIX_TAG);
            if (values.size() != 1) {
                throw new ParseException(EditAutotagCommand.MESSAGE_MULTIPLE_TAG_NAMES);
            }
            newTagName = values.get(0); // only one tag name
        }
        return newTagName;
    }
}
