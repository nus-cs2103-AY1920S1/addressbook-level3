package seedu.module.logic.parser.deadlinecommandparsers;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.deadlinecommands.DoneAllDeadlineCommand;
import seedu.module.logic.parser.ArgumentMultimap;
import seedu.module.logic.parser.ParserUtil;
import seedu.module.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and marks all the deadline object as done.
 */
public class DoneAllDeadlineCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the DoneAllDeadlineCommand
     * and returns an DoneAllDeadlineCommand object for execution.
     * @param argsMultimap
     * @return DoneAllDeadlineCommand
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DoneAllDeadlineCommand parse(ArgumentMultimap argsMultimap) throws ParseException {
        Index index = ParserUtil.parseIndex(argsMultimap.getPreamble());
        return new DoneAllDeadlineCommand(index);
    }
}

