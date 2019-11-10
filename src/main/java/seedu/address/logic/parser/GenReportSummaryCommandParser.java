package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_SIGNATURE_FORMAT;

import seedu.address.logic.commands.GenReportSummaryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author bernicechio

/**
 * Parses input signature and creates a new GenReportSummaryCommand object.
 */
public class GenReportSummaryCommandParser implements Parser<GenReportSummaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GenReportSummaryCommand
     * and returns a GenReportSummaryCommand object for execution.
     **/
    public GenReportSummaryCommand parse(String args) throws ParseException {
        if (!args.matches("^[ A-Za-z]+$") || args.length() > 40) {
            throw new ParseException(MESSAGE_INVALID_SIGNATURE_FORMAT);
        }
        return new GenReportSummaryCommand(args);
    }

}
