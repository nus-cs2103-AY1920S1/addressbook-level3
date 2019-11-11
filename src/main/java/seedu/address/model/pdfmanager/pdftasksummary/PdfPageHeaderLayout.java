package seedu.address.model.pdfmanager.pdftasksummary;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

/**
 * Represents the page header details in the PDF document.
 */
public class PdfPageHeaderLayout {

    private Document document;

    public PdfPageHeaderLayout(Document document) {
        this.document = document;
    }

    /**
     * Creates a page header for each page in the PDF document.
     */
    public void createPageHeader() {
        Paragraph headerParagraph = getPageHeader();
        document.add(designParagraph(headerParagraph));
    }

    private Paragraph getPageHeader() {
        return PdfLayout.createParagraph("Deliveria");
    }

    /**
     * Adds design to {@code Paragraph} object.
     */
    private Paragraph designParagraph(Paragraph paragraph) {
        paragraph.setFontSize(36);
        paragraph.setFontColor(ColorConstants.RED);

        return paragraph;
    }
}
