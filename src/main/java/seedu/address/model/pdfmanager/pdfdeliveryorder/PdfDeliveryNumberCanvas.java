package seedu.address.model.pdfmanager.pdfdeliveryorder;

import java.time.LocalDate;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import seedu.address.model.task.Task;

/**
 * Represents the canvas that contains the delivery number information.
 */
public class PdfDeliveryNumberCanvas extends PdfCanvasLayout {

    private Task task;

    public PdfDeliveryNumberCanvas(PdfCanvas pdfCanvas, PdfDocument pdfDocument, Rectangle rectangle, Task task) {
        super(pdfCanvas, pdfDocument, rectangle);
        this.task = task;
    }

    /**
     * Generates a layout of the delivery order information.
     */
    public void generate() {
        Table table = new Table(2);
        Cell title = createTitle("DELIVERY ORDER");
        Cell numberLabel = createLabel("D/O No.: ");
        Cell deliveryOrderNo = createDeliveryOrderNo(task.getId());
        Cell termsLabel = createLabel("Terms: ");
        Cell terms = createTerms("30 / 60 days");
        Cell dateLabel = createLabel("D/O Date: ");
        Cell date = createDate(task.getDate());

        table.addCell(title);
        table.addCell(numberLabel);
        table.addCell(deliveryOrderNo);
        table.addCell(termsLabel);
        table.addCell(terms);
        table.addCell(dateLabel);
        table.addCell(date);

        canvas.add(table);

        canvas.close();
    }

    /**
     * Creates the title of the delivery order information.
     */
    private Cell createTitle(String title) {
        Cell titleCell = createCell(1, 2, title);

        //styling
        titleCell.setFontSize(15);
        titleCell.setBold();
        titleCell.setFontColor(ColorConstants.RED);
        titleCell.setTextAlignment(TextAlignment.CENTER);

        return titleCell;
    }

    /**
     * Creates the label layout.
     */
    private Cell createLabel(String label) {
        Cell labelCell = createCell(1, 1, label);
        styleLabelCell(labelCell);

        return labelCell;
    }

    /**
     * Creates the terms of payment layout
     */
    private Cell createTerms(String terms) {
        Cell termsCell = createCell(1, 1, terms);
        styleFieldCell(termsCell);

        return termsCell;
    }

    private Cell createDeliveryOrderNo(int number) {
        Cell deliveryNo = createCell(1, 1, String.valueOf(number));
        styleFieldCell(deliveryNo);
        return deliveryNo;
    }

    /**
     * Creates the delivery order date layout.
     */
    private Cell createDate(LocalDate date) {
        Cell dateLabel = createCell(1, 1, date.toString());
        styleFieldCell(dateLabel);

        return dateLabel;
    }

    private void styleLabelCell(Cell cell) {
        cell.setBold();
        cell.setPadding(5);
    }

    private void styleFieldCell(Cell cell) {
        cell.setBorderBottom(new SolidBorder(0.5f));
        cell.setPadding(5);
        cell.setTextAlignment(TextAlignment.CENTER);
    }
}
