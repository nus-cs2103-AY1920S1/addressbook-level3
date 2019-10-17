package seedu.mark.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_FOLDER;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.mark.logic.parser.CliSyntax.PREFIX_URL;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.mark.commons.core.index.Index;
import seedu.mark.logic.commands.EditCommand;
import seedu.mark.logic.commands.EditCommand.EditBookmarkDescriptor;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_URL, PREFIX_REMARK, PREFIX_FOLDER, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditBookmarkDescriptor editBookmarkDescriptor = new EditCommand.EditBookmarkDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editBookmarkDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_URL).isPresent()) {
            editBookmarkDescriptor.setUrl(ParserUtil.parseUrl(argMultimap.getValue(PREFIX_URL).get()));
        }
        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            editBookmarkDescriptor.setRemark(ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get()));
        }
        if (argMultimap.getValue(PREFIX_FOLDER).isPresent()) {
            editBookmarkDescriptor.setFolder(ParserUtil.parseFolder(argMultimap.getValue(PREFIX_FOLDER).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editBookmarkDescriptor::setTags);

        if (!editBookmarkDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editBookmarkDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
