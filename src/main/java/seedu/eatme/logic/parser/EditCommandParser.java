package seedu.eatme.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.eatme.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.eatme.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.eatme.commons.core.index.Index;
import seedu.eatme.logic.commands.EditCommand;
import seedu.eatme.logic.commands.EditCommand.EditEateryDescriptor;
import seedu.eatme.logic.parser.exceptions.ParseException;
import seedu.eatme.model.eatery.Tag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_CATEGORY, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditEateryDescriptor editEateryDescriptor = new EditEateryDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editEateryDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editEateryDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editEateryDescriptor.setCategory(ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get()));
        }

        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editEateryDescriptor::setTags);

        if (!editEateryDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editEateryDescriptor);
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
