package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PDF;

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

    // document types for input
    public static final String PDF_SUMMARY = "summary";
    public static final String PDF_DELIVERY_ORDER = "order";

    //file names
    public static final String FILE_NAME_SUMMARY = "DeliveryTasks";
    public static final String FILE_NAME_DELIVERY_ORDER = "DeliveryOrders";

    //file paths
    public static final String FILE_PATH_SUMMARY = "./data/DeliveryTasks/" + FILE_NAME_SUMMARY + " %1$s.pdf";
    public static final String FILE_PATH_DELIVERY_ORDER = "./data/DeliveryOrders/"
            + FILE_NAME_DELIVERY_ORDER + " %1$s.pdf";

    //messages
    public static final String MESSAGE_SUCCESS_SUMMARY =
            "Successfully generate all drivers' task for %1$s in a PDF document. \n"
            + "It is saved in a folder in the same directory as your deliveria.jar file as "
            + FILE_NAME_SUMMARY + " %1$s.pdf";
    public static final String MESSAGE_SUCCESS_DELIVERY_ORDER =
            "SuccessFully generated all delivery orders for %1$s in a PDF document. \n"
            + "It is saved in a folder in the same directory as your deliveria.jar file "
            + "as " + FILE_NAME_DELIVERY_ORDER + " %1$s.pdf";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": "
            + "Generates a Task Summary in PDF for the specific date. (Document Type: `summary`)\n"
            + "Generates Delivery Orders in PDF for the specific date. (Document Type: `order`)\n"
            + "Date field is optional. "
            + "if a date is not specified, then today's date will be chosen. \n"
            + "Parameters: "
            + "[pdf/DOCUMENT TYPE] [dt/DATE] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PDF + PDF_DELIVERY_ORDER + " "
            + PREFIX_DATETIME + "29/11/2019";

    private String documentType;
    private String filePath;

    private Optional<LocalDate> date;

    public SavePdfCommand(String documentType, Optional<LocalDate> date) {
        this.documentType = documentType;
        this.date = date;
        this.filePath = (documentType.equals(PDF_SUMMARY)) ? FILE_PATH_SUMMARY : FILE_PATH_DELIVERY_ORDER;
    }

    public SavePdfCommand(String documentType, Optional<LocalDate> date, String filePath) {
        this.documentType = documentType;
        this.date = date;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        //set date to today if date isn't indicated
        if (date.isEmpty()) {
            date = Optional.of(GlobalClock.dateToday());
        }
        LocalDate dateOfDelivery = date.get();

        String filePathWithDate = String.format(filePath, dateOfDelivery);

        try {
            generatePdf(model, filePathWithDate, documentType, dateOfDelivery);
        } catch (IOException | PdfNoTaskToDisplayException e) {
            throw new CommandException(e.getMessage());
        }

        return getCommandResultPdf(documentType, dateOfDelivery);
    }

    /**
     * Generates a pdf document based on type of document requested.
     *
     * @param model model.
     * @param filePathWithDate file path consists of date of delivery.
     * @param documentType type of pdf document.
     * @param dateOfDelivery date of delivery.
     * @throws IOException if unable to save file or if file is in used.
     * @throws PdfNoTaskToDisplayException if there is no tasks to display.
     */
    private void generatePdf(Model model, String filePathWithDate, String documentType, LocalDate dateOfDelivery)
            throws IOException, PdfNoTaskToDisplayException {
        if (documentType.equals(PDF_SUMMARY)) {
            model.generateTaskSummaryPdf(filePathWithDate, dateOfDelivery);
        } else {
            //delivery order
            model.generateDeliveryOrderPdf(filePathWithDate, dateOfDelivery);
        }
    }

    private CommandResult getCommandResultPdf(String documentType, LocalDate dateOfDelivery) {
        if (documentType.equals(PDF_SUMMARY)) {
            return new CommandResult(String.format(MESSAGE_SUCCESS_SUMMARY, dateOfDelivery));
        } else {
            //delivery order
            return new CommandResult(String.format(MESSAGE_SUCCESS_DELIVERY_ORDER, dateOfDelivery));
        }
    }
}
