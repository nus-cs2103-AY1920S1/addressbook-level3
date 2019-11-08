package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import seedu.address.logic.GlobalClock;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.pdfmanager.exceptions.PdfNoTaskToDisplayException;

/**
 * Saves a PDF file with all drivers' tasks for a specific date assigned.
 */
public class SavePdfCommand extends Command {

    public static final String COMMAND_WORD = "savepdf";
    public static final String FILE_NAME = "DeliveryTasks";

    public static final String MESSAGE_SUCCESS = "Successfully saved all drivers' task into a PDF document. \n"
            + "It is saved in the same folder as your deliveria.jar file as " + FILE_NAME + ".pdf";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Saves all drivers' tasks into PDF for the specific date. "
            + "Date field is optional. "
            + "if a date is not specified, then today's date will be chosen. \n"
            + "Parameters: "
            + "[DATE] \n"
            + "Example: " + COMMAND_WORD + " 20/10/2019";

    private static final String FILE_PATH_FOR_PDF = "./data/" + FILE_NAME + " %1$s.pdf";

    private Optional<LocalDate> date;

    public SavePdfCommand(Optional<LocalDate> date) {
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        //set date to today if date isn't indicated
        if (date.isEmpty()) {
            date = Optional.of(GlobalClock.dateToday());
        }

        LocalDate dateOfDelivery = date.get();

        String filePathWithDate = String.format(FILE_PATH_FOR_PDF, dateOfDelivery);

        try {
            model.saveDriverTaskPdf(filePathWithDate, dateOfDelivery);
        } catch (IOException | PdfNoTaskToDisplayException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
