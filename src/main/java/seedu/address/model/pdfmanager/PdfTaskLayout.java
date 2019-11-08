package seedu.address.model.pdfmanager;

import java.util.Optional;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;

import seedu.address.model.Description;
import seedu.address.model.EventTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Represents a task's information in a table format in the PDF document.
 */
public class PdfTaskLayout {

    private static boolean toggleColor = false;

    private Task task;

    public PdfTaskLayout(Task task) {
        this.task = task;
    }

    /**
     * Creates a table to encapsulate the tasks' information in the PDF document.
     */
    public Table createTable() {
        //setting basic layout
        Table taskTable = new Table(10).useAllAvailableWidth().setFixedLayout();

        //populate cells
        Cell taskIdCell = getTaskIdCell(task.getId());
        Cell descriptionCell = getDescriptionCell(task.getDescription());
        Cell statusCell = getStatusCell(task.getStatus());

        Optional<EventTime> optionalEventTime = task.getEventTime();
        assert optionalEventTime.isPresent();
        Cell eventTimeCell = getEventTimeCell(optionalEventTime.get());

        taskTable.addCell(PdfLayout.alignCellMiddle(eventTimeCell));
        taskTable.addCell(PdfLayout.alignCellMiddle(taskIdCell));
        taskTable.addCell(descriptionCell);
        taskTable.addCell(statusCell);

        //create customer table
        PdfCustomerLayout customerLayout = new PdfCustomerLayout(task.getCustomer());
        Table customerTable = customerLayout.createTable();

        //add customer table into task table
        Cell customerTableCell = new Cell(1, 8).add(customerTable);
        taskTable.addCell(customerTableCell);

        Table designedTable = designTable(taskTable);

        return designedTable;
    }

    public Cell getTaskIdCell(int taskId) {
        String idStr = "Task ID\n" + taskId;
        return PdfLayout.createCell(1, 1, idStr);
    }

    public Cell getDescriptionCell(Description description) {
        String descriptionStr = "Goods\n" + description;
        return PdfLayout.createCell(1, 5, descriptionStr);
    }

    public Cell getStatusCell(TaskStatus status) {
        String statusStr = "Status\n" + status;
        return PdfLayout.createCell(1, 2, statusStr)
                .setFontColor((status.equals(TaskStatus.ON_GOING)
                        ? ColorConstants.RED
                        : ColorConstants.GREEN));
    }

    public Cell getEventTimeCell(EventTime eventTime) {
        String eventTimeStr = eventTime.toString();
        return PdfLayout.createCell(2, 2, eventTimeStr);
    }

    /**
     * Alternates the color of the rows in the table.
     */
    public Table designTable(Table taskTable) {
        if (toggleColor) {
            taskTable.setBackgroundColor(ColorConstants.WHITE);
        } else {
            Color lightGrey = new DeviceRgb(240, 240, 240);
            taskTable.setBackgroundColor(lightGrey);
        }
        toggleColor = !toggleColor;
        return taskTable;
    }
}
