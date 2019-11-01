package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GenReportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

//@@author bernicechio
/**
 * Parses input workerID and creates a new GenReportCommand object.
 */
public class GenReportCommandParser implements Parser<GenReportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GenReportCommand
     * and returns a GenReportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenReportCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);
            Name sign;
            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                sign = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenReportCommand.MESSAGE_USAGE));
            }
            if (args.trim().length() < 2 || args.trim().charAt(0) != 'B') {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenReportCommand.MESSAGE_USAGE));
            }
            String index = args.trim().substring(1, 2);
            if (index.matches("[0-9]+")) {
                Index genReportBodyId = Index.fromZeroBased(Integer.parseInt(index));
                return new GenReportCommand(genReportBodyId, sign.toString());
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenReportCommand.MESSAGE_USAGE));
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenReportCommand.MESSAGE_USAGE));
        }
    }
}
