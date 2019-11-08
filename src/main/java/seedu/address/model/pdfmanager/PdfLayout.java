package seedu.address.model.pdfmanager;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

/**
 * Consists of functions to aid creation and formatting of the content in a layout.
 */
public class PdfLayout {

    /**
     * Creates a {@code Cell} object with the message and the measurement specified.
     *
     * @param rowSpan row height.
     * @param colSpan column length.
     * @param message message to be displayed in the cell.
     * @return populated cell with the message.
     */
    public static Cell createCell(int rowSpan, int colSpan, String message) {
        Cell newCell = new Cell(rowSpan, colSpan).add(new Paragraph(message));
        Cell designedCell = alignCellMiddle(newCell);
        return designedCell;
    }

    /**
     * Creates a {@code Paragraph} object using the string specified.
     *
     * @param str words to be insert.
     */
    public static Paragraph createParagraph(String str) {
        Paragraph paragraph = new Paragraph(str);
        return paragraph;
    }

    /**
     * Adjusts the position of the words to the center.
     *
     * @param paragraph a block of words.
     */
    public static Paragraph alignParagraphMiddle(Paragraph paragraph) {
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setVerticalAlignment(VerticalAlignment.MIDDLE);
        paragraph.setHorizontalAlignment(HorizontalAlignment.CENTER);

        return paragraph;
    }

    /**
     * Adjusts the content in the cell to the center.
     *
     * @param cell Cell of a table in the PDF document.
     */
    public static Cell alignCellMiddle(Cell cell) {
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setMargin(0);
        cell.setPadding(5);
        return cell;
    }

    /**
     * Sets the font in the cell to bold.
     *
     * @param cell Cell of a table in the PDF document.
     */
    public static Cell boldCell(Cell cell) {
        cell.setBold();
        return cell;
    }

    /**
     * Inserts padding into the cell.
     *
     * @param cell Cell of a table in the PDF document.
     */
    public static Cell insertPadding(Cell cell) {
        cell.setPadding(20);
        return cell;
    }
}
