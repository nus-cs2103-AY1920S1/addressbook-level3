package seedu.address.model.pdfmanager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;

import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * A outer layout that encapsulates all the layouts used.
 */
public class PdfWrapperLayout extends PdfLayout {

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
    public void populateDocumentWithTasks(List<Task> tasks, LocalDate dateOfDelivery) {
        List<Task> tasksOnDate = filterTasksBasedOnDate(tasks, dateOfDelivery);
        List<Task> sortedTasks = sortTaskByEventTime(tasksOnDate);

        List<Driver> driversOnDate = extractDriversFromTaskList(sortedTasks);
        List<Driver> sortedDrivers = sortDriverByName(driversOnDate);

        //initialise outerlayout
        insertDriverTasksIntoDocument(sortedDrivers, sortedTasks, dateOfDelivery);
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

    /**
     * Sorts driver list accordingly to driver's name in ascending order.
     */
    private List<Driver> sortDriverByName(List<Driver> drivers) {
        Comparator<Driver> driverComparator = Comparator.comparing(d -> d.getName().toString());
        drivers.sort(driverComparator);

        return drivers;
    }

    /**
     * Creates a driver list out of task list.
     * The driver list contains the drivers working on the specific date.
     *
     * @param tasks task list for a specific date.
     * @return driver list that contains only drivers that is working on the specific date.
     */
    private List<Driver> extractDriversFromTaskList(List<Task> tasks) {
        List<Driver> driverList = new ArrayList<>();
        for (Task task : tasks) {
            Driver driver = task.getDriver().get();
            if (!driverList.contains(driver)) {
                driverList.add(driver);
            }
        }

        return driverList;
    }

    /**
     * Filter task list to get tasks on a specific date. Only account for ONGOING and COMPLETE tasks.
     * INCOMPLETE tasks are not needed as they are not assigned to any drivers.
     *
     * @param tasks task list.
     * @param date  date of delivery.
     * @return filtered task list that contains only tasks on a specific date and are ONGOING or COMPLETE.
     */
    private List<Task> filterTasksBasedOnDate(List<Task> tasks, LocalDate date) {
        List<Task> filteredTasks = tasks
                .stream()
                .filter(task -> task.getDate().equals(date)
                        && !task.getStatus().equals(TaskStatus.INCOMPLETE))
                .collect(Collectors.toList());

        return filteredTasks;
    }

    /**
     * Sort tasks by ascending time of delivery.
     * All the tasks must be assigned tasks with eventTime.
     * Uses {@code filterTasksBasedOnDate} to get only assigned tasks for a specific date.
     */
    private List<Task> sortTaskByEventTime(List<Task> tasksToSort) {
        //list has been filtered by assigned tasks only so eventTime must exist.
        Comparator<Task> taskComparator = Comparator.comparing(t -> t.getEventTime().get());
        tasksToSort.sort(taskComparator);
        return tasksToSort;
    }
}
