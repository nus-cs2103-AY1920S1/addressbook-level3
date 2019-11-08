package seedu.address.model.pdfmanager;

import java.time.LocalDate;
import java.util.List;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;

import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;

/**
 * A outer layout that encapsulates all the layouts used.
 */
public class PdfWrapperLayout {

    private Document document;

    public PdfWrapperLayout(Document document) {
        this.document = document;
    }

    /**
     * Inserts the summary of each driver's tasks into the PDF document.
     *
     * @param tasks task list.
     * @param dateOfDelivery date of delivery.
     */
    public void populateDocumentWithTasks(List<Task> tasks, List<Driver> drivers, LocalDate dateOfDelivery) {
        //initialise outerlayout
        insertDriverTasksIntoDocument(drivers, tasks, dateOfDelivery);
    }

    /**
     * Adds all the delivery tasks that were assigned to each drivers into the PDF document.
     *
     * @param driverList drivers that were available on the day.
     * @param taskList tasks that were assigned to the drivers on the day.
     * @param dateOfDelivery date of delivery.
     */
    private void insertDriverTasksIntoDocument(List<Driver> driverList, List<Task> taskList, LocalDate dateOfDelivery) {
        for (Driver driver : driverList) {
            //line break
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

            //create page header layout
            PdfPageHeaderLayout pageHeaderLayout = new PdfPageHeaderLayout(document);
            pageHeaderLayout.createPageHeader();

            //create driver layout which contains his tasks for the day.
            PdfDriverLayout driverLayout = new PdfDriverLayout(document, driver, dateOfDelivery);
            driverLayout.createDriverDetails();

            //create Table header layout
            PdfTableHeaderLayout tableHeaderLayout = new PdfTableHeaderLayout();
            Table headerTable = tableHeaderLayout.createTable();
            document.add(headerTable);

            //loop through the task list and append this driver's tasks into the layout
            for (Task task : taskList) {
                addTaskUnderDriver(driver, task);
            }
        }
    }

    /**
     * Appends delivery tasks under the driver layout if the tasks belong to him.
     *
     * @param driver driver on duty.
     * @param task task to be append if task belongs to driver.
     */
    private void addTaskUnderDriver(Driver driver, Task task) {
        //since the task list is filtered to only have assigned tasks, then driver WILL NOT be null.
        assert task.getDriver().isPresent();
        Driver driverAssigned = task.getDriver().get();
        if (driverAssigned.equals(driver)) {
            PdfTaskLayout taskLayout = new PdfTaskLayout(task);
            Table taskTable = taskLayout.createTable();

            //ensure no splitting tables
            taskTable.setKeepTogether(true);

            document.add(taskTable);
        }
    }

}
