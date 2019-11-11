package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
import seedu.address.storage.ReportGenerator;

//@@author bernicechio
/**
 * Generates a PDF report for the specific body ID.
 */
public class GenReportCommand extends Command {


    public static final String COMMAND_WORD = "genReport";

    public static final String MESSAGE_GENREPORT_SUCCESS = "Generated report: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a PDF report for the specific body ID.\n"
            + "Please refer to the User Guide for more details on how to generate a report";

    private static final String MESSAGE_REPORT_NOT_GENERATED = "Report not generated";

    private final Index targetIndexNum;

    private final String sign;

    private ReportGenerator reportGenerator = new ReportGenerator();

    public GenReportCommand(Index targetIndexNum, String sign) {
        this.targetIndexNum = targetIndexNum;
        this.sign = sign;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        IdentificationNumber targetIdNum = IdentificationNumber.customGenerateId("B",
                targetIndexNum.getZeroBased());
        Body bodyToGenReport = null;
        List<Body> lastShownList = model.getFilteredBodyList();

        for (Body body : lastShownList) {
            if (body.getIdNum().equals(targetIdNum)) {
                bodyToGenReport = body;
            }
        }
        if (bodyToGenReport == null) {
            throw new CommandException(MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX);
        }

        boolean generated = reportGenerator.generate(bodyToGenReport, sign);
        if (!generated) {
            throw new CommandException(MESSAGE_REPORT_NOT_GENERATED);
        }
        return new CommandResult(String.format(MESSAGE_GENREPORT_SUCCESS, targetIdNum));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GenReportCommand // instanceof handles nulls
                && targetIndexNum.equals(((GenReportCommand) other).targetIndexNum)
                && sign.equals(((GenReportCommand) other).sign)); // state check
    }
}
