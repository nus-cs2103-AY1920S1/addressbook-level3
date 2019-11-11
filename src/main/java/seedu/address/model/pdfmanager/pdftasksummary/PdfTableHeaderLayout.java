package seedu.address.model.pdfmanager.pdftasksummary;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;

/**
 * Represents a table header's details in a table format in the PDF document.
 */
public class PdfTableHeaderLayout {

    public PdfTableHeaderLayout() {
    }

    /**
     * Creates a table to encapsulate the table header's details in the PDF document.
     */
    public Table createTable() {
        Table headerTable = new Table(10).useAllAvailableWidth().setFixedLayout();

        Cell eventTimeTitle = getEventTimeTitle();
        Cell taskTitle = getTaskTitle();

        headerTable.addCell(PdfLayout.boldCell(eventTimeTitle));
        headerTable.addCell(PdfLayout.boldCell(taskTitle));

        return designTable(headerTable);
    }

    private Cell getEventTimeTitle() {
        return PdfLayout.createCell(1, 2, "Duration");
    }

    private Cell getTaskTitle() {
        return PdfLayout.createCell(1, 8, "Task's Details");
    }

    private Table designTable(Table headerTable) {
        headerTable.setFontColor(ColorConstants.BLACK);
        return headerTable;
    }
}
