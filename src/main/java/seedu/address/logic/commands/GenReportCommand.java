package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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

    //private final Body body;

    //public GenReportCommand(Body body) {
        //this.body = body;
    //}

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ReportGenerator.execute(); //pass in body as parameter
        String bodyID = "v1.2"; //body.getBodyIdNum();
        return new CommandResult(String.format(MESSAGE_GENREPORT_SUCCESS, bodyID));
    }
}
