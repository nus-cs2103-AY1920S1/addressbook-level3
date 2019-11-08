package seedu.address.model.pdfmanager;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;

/**
 * Creates and saves details provided into a PDF file.
 */
public class PdfCreator {

    public final String filePath;

    public PdfCreator(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves drivers` tasks for a specific date into a PDF file.
     *
     * @param tasks tasks list.
     * @param dateOfDelivery date of delivery.
     * @throws IOException if directory used for saving is not found.
     */
    public void saveDriverTaskPdf(List<Task> tasks, List<Driver> drivers, LocalDate dateOfDelivery)
            throws IOException {
        Document document = createDocument();
        insertCoverPage(document, dateOfDelivery);
        insertDriverTask(document, tasks, drivers, dateOfDelivery);

        //close to save
        document.close();
    }

    private void createFileIfMissing() throws IOException {
        FileUtil.createIfMissing(Paths.get(filePath));
    }

    /**
     * Creates a PDF document.
     *
     * @return PDF document ready to be filled with content.
     * @throws IOException if file path is not created or found.
     */
    private Document createDocument() throws IOException {
        createFileIfMissing();

        PdfDocument pdf = new PdfDocument(new PdfWriter(filePath));
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

    private void insertDriverTask(Document document, List<Task> tasks, List<Driver> drivers, LocalDate dateOfDelivery) {
        PdfWrapperLayout wrapperLayout = new PdfWrapperLayout(document);
        wrapperLayout.populateDocumentWithTasks(tasks, drivers, dateOfDelivery);
    }
}
