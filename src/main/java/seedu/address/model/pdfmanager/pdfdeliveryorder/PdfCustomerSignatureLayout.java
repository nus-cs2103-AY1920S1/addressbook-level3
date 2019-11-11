package seedu.address.model.pdfmanager.pdfdeliveryorder;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

/**
 * Represents the canvas that contains customer signature holder layout.
 */
public class PdfCustomerSignatureLayout extends PdfCanvasLayout {

    public PdfCustomerSignatureLayout(PdfCanvas pdfCanvas, PdfDocument pdfDocument, Rectangle rectangle) {
        super(pdfCanvas, pdfDocument, rectangle);
    }

    /**
     * Generates a layout for customer signature.
     */
    public void generate() {
        Table table = new Table(10).setFixedLayout().useAllAvailableWidth();

        Cell title = createTitle("Received the above goods in good order and condition by: ");
        Cell signature = createLabel("Customer's\nSignature & Chop");
        Cell dateAndTime = createLabel("Date & Time");

        table.addCell(title);
        table.addCell(signature);
        table.addCell(dateAndTime);

        canvas.add(table);

        canvas.close();
    }

    /**
     * Creates a title layout for customer signature holder.
     */
    private Cell createTitle(String title) {
        Cell titleCell = createCell(1, 10, title);

        //styling
        styleCell(titleCell);

        return titleCell;
    }

    /**
     * Creates a layout to hold customer signature
     */
    private Cell createLabel(String label) {
        Cell labelCell = createCell(1, 5, label);

        //styling
        labelCell.setBorderTop(new SolidBorder(0.5f));
        labelCell.setTextAlignment(TextAlignment.CENTER);
        styleCell(labelCell);

        return labelCell;
    }

    private void styleCell(Cell cell) {
        cell.setMinHeight(contentHolder.getHeight() / 2);
        cell.setMarginTop(0);
    }
}
