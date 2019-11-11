package seedu.address.model.pdfmanager.pdfdeliveryorder;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import seedu.address.model.Description;
import seedu.address.model.task.Task;

/**
 * Represents the canvas that contains the task's goods' description.
 */
public class PdfTaskDescriptionCanvas extends PdfCanvasLayout {

    private Task task;

    public PdfTaskDescriptionCanvas(PdfCanvas pdfCanvas, PdfDocument pdfDocument, Rectangle rectangle, Task task) {
        super(pdfCanvas, pdfDocument, rectangle);
        this.task = task;
    }

    /**
     * Generates a layout of task description.
     */
    public void generate() {
        Table table = new Table(10).setFixedLayout().useAllAvailableWidth();

        Cell itemHeader = createTitle(1, 1, "Item");
        Cell descriptionHeader = createTitle(1, 6, "Description");
        Cell remarkHeader = createTitle(1, 3, "Remarks");

        table.addHeaderCell(itemHeader);
        table.addHeaderCell(descriptionHeader);
        table.addHeaderCell(remarkHeader);

        //populate the description layout with tasks
        populateDescription(table, task.getDescription());

        //insertBorder();
        canvas.add(table);
        canvas.add(new Paragraph("CONDITION"));

        canvas.close();
    }

    /**
     * Creates the header title of the table.
     */
    private Cell createTitle(int row, int col, String message) {
        Cell title = createCell(row, col, message);

        //styling
        title.setBold();
        title.setTextAlignment(TextAlignment.CENTER);
        title.setBorder(new SolidBorder(1));
        styleHeaderCell(title);

        return title;
    }

    /**
     * Populates the rows of the description table.
     */
    private void populateDescription(Table table, Description description) {
        Cell itemCell = createCell(1, 1, "1");
        Cell descriptionCell = createCell(1, 6, description.toString());
        Cell remarkCell = createCell(1, 3, "");

        //styling
        itemCell.setTextAlignment(TextAlignment.CENTER);
        styleContent(itemCell);
        styleContent(descriptionCell);
        styleContent(remarkCell);

        table.addCell(itemCell);
        table.addCell(descriptionCell);
        table.addCell(remarkCell);
    }

    private void styleHeaderCell(Cell cell) {
        cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
    }

    private void styleContent(Cell cell) {
        cell.setMinHeight(contentHolder.getHeight());
        cell.setBorder(new SolidBorder(1));
    }
}
