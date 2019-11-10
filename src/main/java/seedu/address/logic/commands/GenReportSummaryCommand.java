package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.body.Body;
import seedu.address.storage.ReportGenerator;

//@@author bernicechio

/**
 * Generates a PDF report for the specific body ID.
 */
public class GenReportSummaryCommand extends Command {


    public static final String COMMAND_WORD = "genReportSummary";

    private static final String MESSAGE_GENREPORT_SUCCESS = "Generated report summary";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates a PDF report with an overview for all bodies.\n"
            + "Please refer to the User Guide for more details on how to generate a report";

    private static final String MESSAGE_REPORT_NOT_GENERATED = "Report not generated";

    private ReportGenerator reportGenerator = new ReportGenerator();

    private final String sign;

    public GenReportSummaryCommand(String sign) {
        this.sign = sign;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Body> lastShownList = model.getFilteredBodyList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_REPORT_NOT_GENERATED);
        }
        reportGenerator.generateSummary(lastShownList, sign);
        return new CommandResult(MESSAGE_GENREPORT_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenReportSummaryCommand); // instanceof handles nulls
    }
}
