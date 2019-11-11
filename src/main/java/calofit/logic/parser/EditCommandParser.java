package calofit.logic.parser;

import static calofit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static calofit.logic.parser.CliSyntax.PREFIX_CALORIES;
import static calofit.logic.parser.CliSyntax.PREFIX_NAME;
import static calofit.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static calofit.logic.parser.CliSyntax.PREFIX_TAG;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import calofit.commons.core.index.Index;
import calofit.logic.commands.EditCommand;
import calofit.logic.parser.exceptions.ParseException;
import calofit.model.tag.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CALORIES, PREFIX_TAG, PREFIX_REMOVE_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditDishDescriptor editDishDescriptor = new EditCommand.EditDishDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editDishDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_CALORIES).isPresent()) {
            editDishDescriptor.setCalories(ParserUtil.parseCalorie(argMultimap.getValue(PREFIX_CALORIES).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editDishDescriptor::setTags);

        if (argMultimap.getValue(PREFIX_REMOVE_TAG).isPresent()) {
            Set<Tag> tagsToRemove = argMultimap.getAllValues(PREFIX_REMOVE_TAG).stream()
                    .map(Tag::new).collect(Collectors.toSet());
            editDishDescriptor.setTagsToRemove(tagsToRemove);
        }

        if (!editDishDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editDishDescriptor);
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
        boolean hasClear = tags.contains("");
        boolean isSingle = tags.size() == 1;
        if (hasClear && !isSingle) {
            throw new ParseException(EditCommand.MESSAGE_NO_SIMULTANEOUS_CLEAR_ADD);
        }
        Collection<String> tagSet = hasClear ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
