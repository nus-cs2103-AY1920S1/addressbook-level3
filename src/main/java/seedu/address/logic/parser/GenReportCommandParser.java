package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SIGNATURE_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GenReportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
            if (args.trim().length() < 1) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenReportCommand.MESSAGE_USAGE));
            }
            String[] argArray = args.trim().split(" ", 2);
            String index = argArray[0];
            String sign = "";
            if (argArray.length == 2) {
                sign = argArray[1];
                if (!sign.matches("^[ A-Za-z]+$") || sign.length() + 1 > 40) {
                    throw new ParseException(MESSAGE_INVALID_SIGNATURE_FORMAT);
                }
            }
            if (index.matches("[0-9]+")) {
                Index genReportBodyId = Index.fromZeroBased(Integer.parseInt(index));
                return new GenReportCommand(genReportBodyId, sign);
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenReportCommand.MESSAGE_USAGE));
            }
        } catch (ParseException pe) {
            if (pe.getMessage().equals(MESSAGE_INVALID_SIGNATURE_FORMAT)) {
                throw new ParseException(MESSAGE_INVALID_SIGNATURE_FORMAT);
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GenReportCommand.MESSAGE_USAGE));
        }
    }
}
