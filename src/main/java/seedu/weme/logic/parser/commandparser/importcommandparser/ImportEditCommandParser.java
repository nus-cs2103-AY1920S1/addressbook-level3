package seedu.weme.logic.parser.commandparser.importcommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.weme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.weme.logic.parser.util.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.weme.commons.core.index.Index;
import seedu.weme.logic.commands.importcommand.ImportEditCommand;
import seedu.weme.logic.commands.memecommand.MemeEditCommand;
import seedu.weme.logic.parser.Parser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ArgumentMultimap;
import seedu.weme.logic.parser.util.ArgumentTokenizer;
import seedu.weme.logic.parser.util.ParserUtil;
import seedu.weme.model.tag.Tag;

/**
 * Parses input arguments and creates a new ImportEditCommand object
 */
public class ImportEditCommandParser implements Parser<ImportEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportEditCommand
     * and returns an ImportEditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ImportEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportEditCommand.MESSAGE_USAGE), pe);
        }

        MemeEditCommand.EditMemeDescriptor editMemeDescriptor = new MemeEditCommand.EditMemeDescriptor();
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editMemeDescriptor.setDescription(ParserUtil.parseDescription(
                    argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editMemeDescriptor::setTags);

        if (!editMemeDescriptor.isAnyFieldEdited()) {
            throw new ParseException(ImportEditCommand.MESSAGE_NOT_EDITED);
        }

        return new ImportEditCommand(index, editMemeDescriptor);
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
