package seedu.module.logic.parser;

import static seedu.module.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.module.logic.parser.CliSyntax.PREFIX_DEADLINE;

import seedu.module.commons.core.index.Index;
import seedu.module.logic.commands.AddDeadlineCommand;
import seedu.module.logic.parser.exceptions.ParseException;
import seedu.module.model.module.Deadline;

/**
 * Parses input arguments and creates a new AddDeadlineCommand object.
 */
public class AddDeadlineCommandParser implements Parser<AddDeadlineCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddDeadlineCommand
     * and returns an AddDeadlineCommand object for execution.
     * @param args
     * @return AddDeadlineCommand
     * @throws ParseException if the user input does not conform the expected format.
     */
    public AddDeadlineCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDeadlineCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DEADLINE);
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        String deadline = argMultimap.getValue(PREFIX_DEADLINE).orElse("");
        return new AddDeadlineCommand(index, new Deadline(deadline));
    }
}
