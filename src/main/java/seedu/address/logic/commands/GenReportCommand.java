package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entity.IdentificationNumber;
import seedu.address.model.entity.body.Body;
import seedu.address.storage.ReportGenerator;

/**
 * Generates a PDF report for the specific body ID.
 */
public class GenReportCommand extends Command {


    public static final String COMMAND_WORD = "genReport";

    public static final String MESSAGE_GENREPORT_SUCCESS = "Generated report: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Generates a PDF report for the specific body ID.\n"
            + "Parameters: BODY_ID\n"
            + "Example: " + COMMAND_WORD + " ";

    private final IdentificationNumber targetIdNum;

    public GenReportCommand(IdentificationNumber targetIdNum) {
        this.targetIdNum = targetIdNum;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Body bodyToGenReport = null;
        List<Body> lastShownList = model.getFilteredBodyList();

        for (Body body : lastShownList) {
            if (body.getBodyIdNum().equals(targetIdNum)) {
                bodyToGenReport = body;
            }
        }

        ReportGenerator.generate(bodyToGenReport);
        return new CommandResult(String.format(MESSAGE_GENREPORT_SUCCESS, targetIdNum));
    }
}
