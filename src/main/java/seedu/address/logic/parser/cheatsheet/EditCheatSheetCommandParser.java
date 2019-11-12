package seedu.address.logic.parser.cheatsheet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CHEATSHEET_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.cheatsheet.EditCheatSheetCommand;
import seedu.address.logic.commands.cheatsheet.EditCheatSheetCommand.EditCheatSheetDescriptor;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.cheatsheet.Content;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCheatSheetCommand object
 */
public class EditCheatSheetCommandParser implements Parser<EditCheatSheetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCheatSheetCommand
     * and returns an EditCheatSheetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCheatSheetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TITLE, PREFIX_CONTENT, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCheatSheetCommand.MESSAGE_USAGE), pe);
        }

        EditCheatSheetDescriptor editCheatSheetDescriptor = new EditCheatSheetDescriptor();
        if (argMultimap.getValue(PREFIX_TITLE).isPresent()) {
            editCheatSheetDescriptor.setTitle(ParserUtil.parseCheatSheetTitle(
                    argMultimap.getValue(PREFIX_TITLE).get()));
        }

        editCheatSheetDescriptor.setIndexes(parseIndexesForEdit(argMultimap.getAllValues(PREFIX_CONTENT)));

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editCheatSheetDescriptor::setTags);

        if (!editCheatSheetDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCheatSheetCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCheatSheetCommand(index, editCheatSheetDescriptor);
    }

    /**
     * Parses {@code Collection<String> indexes} into a {@code ArrayList<Integer>} if {@code indexes} is non-empty.
     * If {@code indexes} contain only one element which is an empty string, it will be parsed into a
     * {@code ArrayList<Integer>} containing zero contents.
     */
    private ArrayList<Integer> parseIndexesForEdit(Collection<String> indexes) throws ParseException {
        assert indexes != null;

        if (indexes.isEmpty() || (indexes.size() == 1 && indexes.contains(""))) {
            return null;
        }

        ArrayList<Integer> newIndexes = new ArrayList<>();

        for (String s: indexes) {
            try {
                newIndexes.add(Integer.parseInt(s));
            } catch (Exception e) {
                throw new ParseException(MESSAGE_INVALID_CHEATSHEET_DISPLAYED_INDEX);
            }
        }

        return newIndexes;
    }

    /**
     * Parses {@code Collection<String> contents} into a {@code Set<Content>} if {@code contents} is non-empty.
     * If {@code contents} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Content>} containing zero contents.
     */
    private Optional<Set<Content>> parseContentsForEdit(Collection<String> contents, Set<Tag> tags)
            throws ParseException {
        assert contents != null && tags != null;

        if (contents.isEmpty()) {
            return Optional.empty();
        }

        Collection<String> contentSet = contents.size() == 1 && contents.contains("")
                ? Collections.emptySet() : contents;
        return Optional.of(ParserUtil.parseCheatSheetContents(contentSet, tags));
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
