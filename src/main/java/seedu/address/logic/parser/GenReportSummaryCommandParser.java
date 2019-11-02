package seedu.address.logic.parser;

import seedu.address.logic.commands.GenReportSummaryCommand;

//@@author bernicechio

/**
 * Parses input signature and creates a new GenReportSummaryCommand object.
 */
public class GenReportSummaryCommandParser implements Parser<GenReportSummaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the GenReportSummaryCommand
     * and returns a GenReportSummaryCommand object for execution.
     **/
    public GenReportSummaryCommand parse(String args) {
        return new GenReportSummaryCommand(args);
    }

}
