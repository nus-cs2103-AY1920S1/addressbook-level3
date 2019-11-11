package seedu.address.model.pdfmanager;

import static seedu.address.commons.core.Messages.MESSAGE_NO_ASSIGNED_TASK_FOR_THE_DATE;
import static seedu.address.commons.core.Messages.MESSAGE_NO_INCOMPLETE_OR_ASSIGNED_TASKS_FOR_THE_DATE;
import static seedu.address.logic.commands.SavePdfCommand.PDF_DELIVERY_ORDER;
import static seedu.address.logic.commands.SavePdfCommand.PDF_SUMMARY;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import seedu.address.model.company.Company;
import seedu.address.model.pdfmanager.exceptions.PdfNoTaskToDisplayException;
import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;

/**
 * Manages generating of PDF documents.
 */
public class PdfManager {

    public static final String MESSAGE_CONSTRAINTS = "There's only 2 document types: `" + PDF_SUMMARY + "' and `"
            + PDF_DELIVERY_ORDER + "'. \n"
            + PDF_SUMMARY + " - generates a Pdf Task Summary.\n"
            + PDF_DELIVERY_ORDER + " - generates a Pdf Delivery order.";
    private static final List<String> documentTypes = List.of(PDF_SUMMARY, PDF_DELIVERY_ORDER);

    public static boolean isValidDocument(String documentType) {
        return documentTypes.contains(documentType);
    }

    /**
     * Generates a task summary for ongoing and completed tasks for each drivers for a specific date in PDF.
     *
     * @param tasks ongoing and completed tasks.
     * @param drivers drivers on duty for the specific date.
     * @param date date of delivery.
     * @throws IOException if file cannot be saved or file is in used.
     * @throws PdfNoTaskToDisplayException if there is no tasks to display for the specific date.
     */
    public static void generateTaskSummary(String filePath, List<Task> tasks, List<Driver> drivers, LocalDate date)
            throws IOException, PdfNoTaskToDisplayException {
        if (tasks.isEmpty()) {
            String errorMessage = String.format(MESSAGE_NO_ASSIGNED_TASK_FOR_THE_DATE, date);
            throw new PdfNoTaskToDisplayException(errorMessage);
        }

        PdfCreator pdfCreator = new PdfCreator(filePath);
        pdfCreator.saveDriverTaskPdf(tasks, drivers, date);
    }

    /**
     * Generates delivery orders for each incomplete and assigned / ongoing tasks for a specific date.
     *
     * @param tasks incomplete and ongoing tasks.
     * @param date date of delivery.
     * @param company an organisation.
     */
    public static void generateDeliveryOrder(String filePath, List<Task> tasks, LocalDate date, Company company)
            throws IOException, PdfNoTaskToDisplayException {
        if (tasks.isEmpty()) {
            String errorMessage = String.format(MESSAGE_NO_INCOMPLETE_OR_ASSIGNED_TASKS_FOR_THE_DATE, date);
            throw new PdfNoTaskToDisplayException(errorMessage);
        }

        PdfCreator pdfCreator = new PdfCreator(filePath);
        pdfCreator.generateDeliveryOrderPdf(tasks, company);
    }
}
