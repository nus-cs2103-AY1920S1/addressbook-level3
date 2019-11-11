package seedu.address.model.pdfmanager.pdfdeliveryorder;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Paragraph;

/**
 * Represents the canvas that contains the terms and conditions.
 */
public class PdfConditionsCanvas extends PdfCanvasLayout {

    public static final String TERMS_AND_CONDITIONS = "N.B.: Customers are requested to examine the "
            + "above mentioned goods are in good order as goods once sold cannot be returned or exchanged. ";

    public PdfConditionsCanvas(PdfCanvas pdfCanvas, PdfDocument pdfDocument, Rectangle rectangle) {
        super(pdfCanvas, pdfDocument, rectangle);
    }

    /**
     * Generates a layout for the terms and conditions.
     */
    public void generate() {
        Paragraph conditions = createParagraph(TERMS_AND_CONDITIONS, 10);

        canvas.add(conditions);

        canvas.close();
    }
}
