package seedu.address.model.pdfmanager.pdfdeliveryorder;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;

/**
 * Represents a canvas layout that is used to encapsulate information.
 */
public abstract class PdfCanvasLayout {

    protected PdfCanvas pdfCanvas;
    protected PdfDocument pdfDocument;
    protected Rectangle contentHolder;
    protected Canvas canvas;

    public PdfCanvasLayout(PdfCanvas pdfCanvas, PdfDocument pdfDocument, Rectangle rectangle) {
        //Position X and Y (0,0) refers to the bottom left point.
        this.pdfCanvas = pdfCanvas;
        this.pdfDocument = pdfDocument;
        this.contentHolder = rectangle;
        canvas = new Canvas(pdfCanvas, pdfDocument, contentHolder);
    }

    /**
     * Generates a canvas filled with content.
     */
    public abstract void generate();

    /**
     * Creates a paragraph of words of a specific font size.
     *
     * @param message message to be displayed.
     * @param fontSize font size.
     */
    public Paragraph createParagraph(String message, float fontSize) {
        Paragraph paragraph = new Paragraph(message);
        paragraph.setFontSize(fontSize);
        return paragraph;
    }

    /**
     * Creates a {@code Cell} object with the message and the measurement specified.
     *
     * @param rowSpan row height.
     * @param colSpan column length.
     * @param message message to be displayed in the cell.
     * @return populated cell with the message.
     */
    public Cell createCell(int rowSpan, int colSpan, String message) {
        Cell newCell = new Cell(rowSpan, colSpan).add(new Paragraph(message));
        newCell.setBorder(Border.NO_BORDER);
        return newCell;
    }

    /**
     * Insert a Border among the content holder.
     */
    public void insertBorder() {
        pdfCanvas.rectangle(contentHolder);
        pdfCanvas.stroke();
    }
}
