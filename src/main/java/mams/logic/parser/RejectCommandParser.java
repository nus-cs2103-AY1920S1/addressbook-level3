package mams.logic.parser;

import static java.util.Objects.requireNonNull;
import static mams.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static mams.logic.parser.CliSyntax.PREFIX_REASON;

import mams.commons.core.index.Index;
import mams.commons.exceptions.IllegalValueException;
import mams.logic.commands.RejectCommand;
import mams.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ApproveCommand} object
 */
public class RejectCommandParser implements Parser<RejectCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code ApproveCommand}
     * and returns a {@code ApproveCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RejectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REASON);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RejectCommand.MESSAGE_USAGE_REJECT), ive);

        }

        String remark = argMultimap.getValue(PREFIX_REASON).orElse("");

        return new RejectCommand(index, remark);
    }
}
