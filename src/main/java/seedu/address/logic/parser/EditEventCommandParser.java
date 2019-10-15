package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_HOURS_NEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_MANPOWER_NEEDED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_VENUE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditEventCommandParser implements Parser<EditEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditEventCommand
     * and returns an EditEventCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditEventCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EVENT_NAME, PREFIX_EVENT_VENUE, PREFIX_EVENT_MANPOWER_NEEDED,
                        PREFIX_EVENT_HOURS_NEEDED, PREFIX_EVENT_START_DATE, PREFIX_EVENT_END_DATE, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE), pe);
        }

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        if (argMultimap.getValue(PREFIX_EVENT_NAME).isPresent()) {
            editEventDescriptor.setName(ParserUtil.parseEventName(argMultimap.getValue(PREFIX_EVENT_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_VENUE).isPresent()) {
            editEventDescriptor.setVenue(ParserUtil.parseVenue(argMultimap.getValue(PREFIX_EVENT_VENUE).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_MANPOWER_NEEDED).isPresent()) {
            editEventDescriptor.setManpowerNeeded(ParserUtil.parseManpowerNeeded(
                    argMultimap.getValue(PREFIX_EVENT_MANPOWER_NEEDED).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_HOURS_NEEDED).isPresent()) {
            editEventDescriptor.setHoursNeeded(ParserUtil.parseHoursNeeded(
                    argMultimap.getValue(PREFIX_EVENT_HOURS_NEEDED).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_START_DATE).isPresent()) {
            editEventDescriptor.setStartDate(ParserUtil.parseStartDate(
                    argMultimap.getValue(PREFIX_EVENT_START_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_END_DATE).isPresent()) {
            editEventDescriptor.setEndDate(ParserUtil.parseEndDate(
                    argMultimap.getValue(PREFIX_EVENT_END_DATE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editEventDescriptor::setTags);

        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditEventCommand.MESSAGE_NOT_EDITED);
        }

        return new EditEventCommand(index, editEventDescriptor);
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
