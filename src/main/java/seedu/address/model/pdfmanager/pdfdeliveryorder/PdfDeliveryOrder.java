package seedu.address.model.pdfmanager.pdfdeliveryorder;

import java.util.List;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import seedu.address.model.company.Company;
import seedu.address.model.task.Task;

/**
 * Represents a list of delivery orders in PDF format.
 */
public class PdfDeliveryOrder {

    //A4 size is 595 x 842
    public static final Rectangle COMPANY_HOLDER = new Rectangle(30, 705, 520, 120);
    public static final Rectangle CUSTOMER_HOLDER = new Rectangle(30, 555, 260, 120);
    public static final Rectangle DELIVERY_NUMBER_HOLDER = new Rectangle(400, 555, 150, 125);
    public static final Rectangle TASK_DESCRIPTION_HOLDER = new Rectangle(30, 290, 520, 250);
    public static final Rectangle TERMS_AND_CONDITIONS_HOLDER = new Rectangle(30, 230, 520, 60);
    public static final Rectangle SIGNATURE_HOLDER = new Rectangle(30, 25, 240, 140);
    public static final Rectangle COMPANY_SIGNATURE_HOLDER = new Rectangle(305, 25, 240, 140);

    private PdfDocument pdfDocument;
    private List<Task> tasks;
    private Company company;

    public PdfDeliveryOrder(PdfDocument pdfDocument, List<Task> tasks, Company company) {
        this.pdfDocument = pdfDocument;
        this.tasks = tasks;
        this.company = company;
    }

    /**
     * Creates delivery orders for the delivery tasks.
     */
    public void create() {
        for (Task task : tasks) {
            PdfPage newPage = pdfDocument.addNewPage();
            PdfCanvas pdfCanvas = new PdfCanvas(newPage);

            List<PdfCanvasLayout> allCanvas = List.of(
                    new PdfCompanyCanvas(pdfCanvas, pdfDocument, COMPANY_HOLDER, company),
                    new PdfCustomerCanvas(pdfCanvas, pdfDocument, CUSTOMER_HOLDER, task.getCustomer()),
                    new PdfDeliveryNumberCanvas(pdfCanvas, pdfDocument, DELIVERY_NUMBER_HOLDER, task),
                    new PdfTaskDescriptionCanvas(pdfCanvas, pdfDocument, TASK_DESCRIPTION_HOLDER, task),
                    new PdfConditionsCanvas(pdfCanvas, pdfDocument, TERMS_AND_CONDITIONS_HOLDER),
                    new PdfCustomerSignatureLayout(pdfCanvas, pdfDocument, SIGNATURE_HOLDER),
                    new PdfCompanySignatureCanvas(pdfCanvas, pdfDocument, COMPANY_SIGNATURE_HOLDER, company));

            allCanvas.forEach(PdfCanvasLayout::generate);

        }

    }
}
