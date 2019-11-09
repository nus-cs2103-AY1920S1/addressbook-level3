package seedu.module.logic.parser.deadlinecommandparsers;

import static seedu.module.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TASK_LIST_NUMBER;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.deadlinecommands.EditDeadlineCommand;
import seedu.module.logic.commands.deadlinecommands.EditDeadlineDescCommand;
import seedu.module.logic.commands.deadlinecommands.EditDeadlineTimeCommand;
import seedu.module.logic.parser.ArgumentMultimap;
import seedu.module.logic.parser.ParserUtil;
import seedu.module.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and edits the deadline object.
 */
public class EditDeadlineCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the EditDeadlineCommand
     * and returns an EditDeadlineCommand object for execution.
     * @param argsMultimap
     * @return EditDeadlineCommand
     * @throws ParseException if the user input does not conform the expected format.
     */
    public EditDeadlineCommand parse(ArgumentMultimap argsMultimap) throws ParseException {
        Index index = ParserUtil.parseIndex(argsMultimap.getPreamble());
        if (argsMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            String description = argsMultimap.getValue(PREFIX_DESCRIPTION).get();
            int taskListNum = Integer.parseInt(argsMultimap.getValue(PREFIX_TASK_LIST_NUMBER).get().trim());
            return new EditDeadlineDescCommand(index, description, taskListNum);
        } else if (argsMultimap.getValue(PREFIX_TIME).isPresent()) {
            String time = argsMultimap.getValue(PREFIX_TIME).get();
            int taskListNum = Integer.parseInt(argsMultimap.getValue(PREFIX_TASK_LIST_NUMBER).get().trim());
            return new EditDeadlineTimeCommand(index, time, taskListNum);
        } else {
            throw new ParseException(EditDeadlineCommand.MESSAGE_USAGE);
        }
    }
}



