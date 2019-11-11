package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLASSID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARKING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK_TIME;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.TreeSet;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.calendar.EditTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.TaskTime;

/**
 * Parses input arguments and creates a new EditTaskCommand object
 */
public class EditTaskCommandParser implements Parser<EditTaskCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditTaskCommand
     * and returns an EditTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CLASSID, PREFIX_TASK_TIME, PREFIX_MARKING);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE), pe);
        }

        EditTaskCommand.EditTaskDescriptor editTaskDescriptor = new EditTaskCommand.EditTaskDescriptor();
        if (argMultimap.getValue(PREFIX_CLASSID).isPresent()) {
            editTaskDescriptor.setClassId(ParserUtil.parseClassId(argMultimap.getValue(PREFIX_CLASSID).get()));
        }
        if (argMultimap.getValue(PREFIX_MARKING).isPresent()) {
            editTaskDescriptor.setMarking(ParserUtil.parseMarking(argMultimap.getValue(PREFIX_MARKING).get()));
        }
        parseTimesForEdit(argMultimap.getAllValues(PREFIX_TASK_TIME)).ifPresent(editTaskDescriptor::setTaskTimes);


        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTaskCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTaskCommand(index, editTaskDescriptor);
    }

    /**
     * Parses {@code Collection<String> taskTimes} into a {@code Set<TaskTime>} if {@code taskTimes} is non-empty.
     * If {@code taskTimes} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<TaskTime>} containing zero taskTimes.
     */
    private Optional<TreeSet<TaskTime>> parseTimesForEdit(Collection<String> taskTimes) throws ParseException {
        assert taskTimes != null;

        if (taskTimes.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> taskTimeSet = taskTimes.size() == 1
                && taskTimes.contains("")
                ? Collections.emptySet() : taskTimes;
        return Optional.of(ParserUtil.parseTaskTimes(taskTimeSet));
    }
}
