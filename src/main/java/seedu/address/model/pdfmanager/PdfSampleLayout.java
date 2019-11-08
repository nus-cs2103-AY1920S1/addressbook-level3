package seedu.address.model.pdfmanager;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;

/**
 * Represents a sample layout to show how the information in the PDF document is represented.
 */
public class PdfSampleLayout {

    public PdfSampleLayout() {

    }

    /**
     * Creates a table to show a sample layout of the tasks displayed in the PDF document.
     */
    public Table createTable() {
        Table sampleTable = new Table(10).useAllAvailableWidth().setFixedLayout();

        Cell titleCell = PdfLayout.createCell(1, 10, "SAMPLE DRIVER'S TASK.");
        titleCell.setBold();

        Cell eventTimeCell = PdfLayout.createCell(2, 2, "Duration of Delivery");
        Cell taskDetailCell = PdfLayout.createCell(1, 8, "Task's Information\n");
        Cell customerDetailCell = PdfLayout.createCell(1, 8, "Customer's Information\n");

        sampleTable.addCell(titleCell);
        sampleTable.addCell(PdfLayout.insertPadding(eventTimeCell));
        sampleTable.addCell(PdfLayout.insertPadding(taskDetailCell));
        sampleTable.addCell(PdfLayout.insertPadding(customerDetailCell));

        return sampleTable;
    }
}
