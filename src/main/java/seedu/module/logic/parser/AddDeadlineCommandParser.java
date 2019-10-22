package seedu.module.logic.parser;

import static seedu.module.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.module.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.deadlineCommands.AddDeadlineCommand;

import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.module.Deadline;

/**
 * Parses input arguments and creates a new AddDeadlineCommand object.
 */
public class AddDeadlineCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddDeadlineCommand
     * and returns an AddDeadlineCommand object for execution.
     * @param argsMultimap
     * @return AddDeadlineCommand
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddDeadlineCommand parse(ArgumentMultimap argsMultimap) throws ParseException {
        Index index = ParserUtil.parseIndex(argsMultimap.getPreamble());
        if (argsMultimap.getValue(PREFIX_DESCRIPTION).isPresent() && argsMultimap.getValue(PREFIX_TIME).isPresent()) {
            String description = argsMultimap.getValue(PREFIX_DESCRIPTION).get();
            String time = argsMultimap.getValue(PREFIX_TIME).get();
            Deadline deadline = new Deadline(description, time);
            return new AddDeadlineCommand(index, deadline);
        } else {
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }

    }
}
