package seedu.address.model.pdfmanager;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.pdfmanager.exceptions.PdfNoTaskToDisplayException;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Creates and saves details provided into a PDF file.
 */
public class PdfCreator {

    public static final String MESSAGE_NO_ASSIGNED_TASK_FOR_THE_DATE = "There's no assigned tasks for %1$s.";

    public final String filePath;

    public PdfCreator(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePathWithDate(LocalDate date) {
        return String.format(filePath, "(" + date + ")");
    }

    /**
     * Saves drivers` tasks for a specific date into a PDF file.
     *
     * @param tasks tasks list.
     * @param dateOfDelivery date of delivery.
     * @throws IOException if directory used for saving is not found.
     * @throws PdfNoTaskToDisplayException if there is no assigned task on the day.
     */
    public void saveDriverTaskPdf(List<Task> tasks, LocalDate dateOfDelivery)
            throws IOException, PdfNoTaskToDisplayException {
        if (!hasAssignedTasks(tasks, dateOfDelivery)) {
            throw new PdfNoTaskToDisplayException(String.format(MESSAGE_NO_ASSIGNED_TASK_FOR_THE_DATE, dateOfDelivery));
        }

        Document document = createDocument(dateOfDelivery);
        insertCoverPage(document, dateOfDelivery);
        insertDriverTask(document, tasks, dateOfDelivery);

        //close to save
        document.close();
    }

    private void createFileIfMissing(LocalDate dateOfDelivery) throws IOException {
        String filePathWithDate = getFilePathWithDate(dateOfDelivery);
        FileUtil.createIfMissing(Paths.get(filePathWithDate));
    }

    /**
     * Creates a PDF document.
     *
     * @return PDF document ready to be filled with content.
     * @throws IOException if file path is not created or found.
     */
    private Document createDocument(LocalDate dateOfDelivery) throws IOException {
        createFileIfMissing(dateOfDelivery);

        String filePathWithDate = getFilePathWithDate(dateOfDelivery);
        PdfDocument pdf = new PdfDocument(new PdfWriter(filePathWithDate));
        Document newDocument = new Document(pdf);
        newDocument.setMargins(30, 30, 30, 30);

        return newDocument;
    }

    /**
     * Inserts cover page into the PDF document.
     *
     * @param document PDF document.
     * @param dateOfDelivery date of delivery.
     */
    private void insertCoverPage(Document document, LocalDate dateOfDelivery) {
        //add cover page
        String title = "Deliveria";
        String subTitle = "Delivery Tasks for " + dateOfDelivery;

        PdfCoverPageLayout coverPageLayout = new PdfCoverPageLayout(document);
        coverPageLayout.addCoverPage(title, subTitle);
    }

    private void insertDriverTask(Document document, List<Task> tasks, LocalDate dateOfDelivery)
            throws PdfNoTaskToDisplayException {
        PdfWrapperLayout wrapperLayout = new PdfWrapperLayout(document);
        wrapperLayout.populateDocumentWithTasks(tasks, dateOfDelivery);
    }

    /**
     * Checks if the task list contains assigned tasks for the specified date.
     *
     * @param tasks task list.
     * @param date date of delivery.
     * @return true if there are assigned tasks for the specified date.
     */
    private boolean hasAssignedTasks(List<Task> tasks, LocalDate date) {
        List<Task> filteredTasks = tasks
                .stream()
                .filter(task -> task.getDate().equals(date)
                        && !task.getStatus().equals(TaskStatus.INCOMPLETE))
                .collect(Collectors.toList());

        return (filteredTasks.size() != 0);
    }
}
