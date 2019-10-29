package seedu.address.logic.calendar.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDAY;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDEADLINE;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDESCRIPTION;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTAG;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTIME;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTITLE;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.calendar.commands.EditCommand;
import seedu.address.logic.calendar.parser.exceptions.ParseException;
import seedu.address.model.calendar.tag.TaskTag;

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
                ArgumentTokenizer.tokenize(args, PREFIX_TASKTITLE, PREFIX_TASKDAY, PREFIX_TASKDESCRIPTION,
                    PREFIX_TASKDEADLINE, PREFIX_TASKTIME, PREFIX_TASKTAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditTaskDescriptor editTaskDescriptor = new EditCommand.EditTaskDescriptor();
        if (argMultimap.getValue(PREFIX_TASKTITLE).isPresent()) {
            editTaskDescriptor.setTaskTitle(ParserUtil.parseTitle(argMultimap.getValue(PREFIX_TASKTITLE).get()));
        }
        if (argMultimap.getValue(PREFIX_TASKDAY).isPresent()) {
            editTaskDescriptor.setTaskDay(ParserUtil.parseDay(argMultimap.getValue(PREFIX_TASKDAY).get()));
        }
        if (argMultimap.getValue(PREFIX_TASKDESCRIPTION).isPresent()) {
            editTaskDescriptor.setTaskDescription(ParserUtil.parseDescription(argMultimap
                    .getValue(PREFIX_TASKDESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_TASKDEADLINE).isPresent()) {
            editTaskDescriptor.setTaskDeadline(ParserUtil.parseDeadline(argMultimap
                .getValue(PREFIX_TASKDEADLINE).get()));
        }
        if (argMultimap.getValue(PREFIX_TASKTIME).isPresent()) {
            editTaskDescriptor.setTaskTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TASKTIME).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TASKTAG)).ifPresent(editTaskDescriptor::setTaskTags);

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<TaskTag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<TaskTag>} containing zero tags.
     */
    private Optional<Set<TaskTag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
