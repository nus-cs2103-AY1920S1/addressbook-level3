package seedu.address.logic.calendar.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKDESCRIPTION;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKPLACE;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTAG;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTIME;
import static seedu.address.logic.calendar.parser.CliSyntax.PREFIX_TASKTITLE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.calendar.commands.AddCommand;
import seedu.address.logic.calendar.parser.exceptions.ParseException;
import seedu.address.model.calendar.person.Task;
import seedu.address.model.calendar.person.TaskDescription;
import seedu.address.model.calendar.person.TaskPlace;
import seedu.address.model.calendar.person.TaskTime;
import seedu.address.model.calendar.person.TaskTitle;
import seedu.address.model.calendar.tag.TaskTag;






/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TASKTITLE, PREFIX_TASKTIME, PREFIX_TASKDESCRIPTION,
                        PREFIX_TASKPLACE, PREFIX_TASKTAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_TASKTITLE, PREFIX_TASKPLACE, PREFIX_TASKTIME,
                PREFIX_TASKDESCRIPTION) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        TaskTitle taskTitle = ParserUtil.parseName(argMultimap.getValue(PREFIX_TASKTITLE).get());
        TaskTime taskTime = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_TASKTIME).get());
        TaskDescription taskDescription = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_TASKDESCRIPTION).get());
        TaskPlace taskPlace = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_TASKPLACE).get());
        Set<TaskTag> taskTagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TASKTAG));

        Task task = new Task(taskTitle, taskTime, taskDescription, taskPlace, taskTagList);

        return new AddCommand(task);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
