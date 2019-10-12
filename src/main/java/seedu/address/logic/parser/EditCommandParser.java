package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_DESCRIPTION;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditItemDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code description} and {@code args} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String description, String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASK_DESCRIPTION, PREFIX_EVENT_DESCRIPTION,
                        PREFIX_REMINDER_DESCRIPTION, PREFIX_DATETIME, PREFIX_REMINDER, PREFIX_PRIORITY, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(description);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditItemDescriptor editItemDescriptor = new EditItemDescriptor();
        if (argMultimap.getValue(PREFIX_TASK_DESCRIPTION).isPresent()) {
            editItemDescriptor.setDescription(
                    ParserUtil.parseDescription(
                            argMultimap.getValue(PREFIX_TASK_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_DESCRIPTION).isPresent()) {
            editItemDescriptor.setDescription(
                    ParserUtil.parseDescription(
                            argMultimap.getValue(PREFIX_EVENT_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_REMINDER_DESCRIPTION).isPresent()) {
            editItemDescriptor.setDescription(
                    ParserUtil.parseDescription(
                            argMultimap.getValue(PREFIX_REMINDER_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_REMINDER).isPresent()) {
            editItemDescriptor.setReminder(
                    ParserUtil.parseReminder(
                            argMultimap.getValue(PREFIX_REMINDER).get()).get());
        }
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editItemDescriptor.setPriority(
                    ParserUtil.parsePriority(
                            argMultimap.getValue(PREFIX_PRIORITY).get()).get());
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editItemDescriptor::setTags);

        if (!editItemDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editItemDescriptor);
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
