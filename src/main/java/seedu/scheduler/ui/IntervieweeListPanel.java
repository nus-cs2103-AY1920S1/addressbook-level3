package seedu.scheduler.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.Region;

import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.model.person.Interviewee;

/**
 * Panel containing the list of interviewee.
 */
public class IntervieweeListPanel extends UiPart<Region> {
    private static final String FXML = "IntervieweeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(IntervieweeListPanel.class);

    private ObservableList<Interviewee> intervieweeList;

    @FXML
    private TableView intervieweeTableView;


    IntervieweeListPanel(ObservableList<Interviewee> intervieweeList) {
        super(FXML);
        this.intervieweeList = intervieweeList;
        initialise();
    }

    /**
     * Set the columns and the data from each column
     */
    private void initialise() {
        setTableColumn();
        if (this.intervieweeList.isEmpty()) {
            this.intervieweeTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } else {
            this.intervieweeTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        }
        this.intervieweeTableView.setItems(this.intervieweeList);
    }

    /**
     * Set the table columns.
     */
    private void setTableColumn() {
        TableColumn<ObservableList<String>, String> nameTitle =
                new TableColumn<ObservableList<String>, String>("Name");
        nameTitle.setCellValueFactory(new PropertyValueFactory<ObservableList<String>, String>("Name"));
        editTableColumn(nameTitle);
        TableColumn<ObservableList<String>, String> phoneTitle =
                new TableColumn<ObservableList<String>, String>("Phone");
        phoneTitle.setCellValueFactory(new PropertyValueFactory<ObservableList<String>, String>("phone"));
        editTableColumn(phoneTitle);
        TableColumn<ObservableList<String>, String> emailTitle =
                new TableColumn<ObservableList<String>, String>("Emails");
        emailTitle.setCellValueFactory(new PropertyValueFactory<ObservableList<String>, String>("Emails"));
        editTableColumn(emailTitle);
        TableColumn<ObservableList<String>, String> facultyTitle =
                new TableColumn<ObservableList<String>, String>("Faculty");
        facultyTitle.setCellValueFactory(new PropertyValueFactory<ObservableList<String>, String>("Faculty"));
        editTableColumn(facultyTitle);
        TableColumn<ObservableList<String>, String> yearTitle =
                new TableColumn<ObservableList<String>, String>("Academic Year");
        yearTitle.setCellValueFactory(new PropertyValueFactory<ObservableList<String>, String>("yearOfStudy"));
        editTableColumn(yearTitle);
        TableColumn<ObservableList<String>, String> departmentTitle =
                new TableColumn<ObservableList<String>, String>("Department Choices");
        departmentTitle.setCellValueFactory(
                new PropertyValueFactory<ObservableList<String>, String>("DepartmentChoices"));
        editTableColumn(departmentTitle);
        TableColumn<ObservableList<String>, String> timeSlotTitle =
                new TableColumn<ObservableList<String>, String>("Time Slots");
        timeSlotTitle.setCellValueFactory(
                new PropertyValueFactory<ObservableList<String>, String>("AvailableTimeslots"));
        editTableColumn(timeSlotTitle);
        TableColumn<ObservableList<String>, String> allocatedTimeTitle =
                new TableColumn<ObservableList<String>, String>("Allocated Slot");
        allocatedTimeTitle.setCellValueFactory(
                new PropertyValueFactory<ObservableList<String>, String>("allocatedSlot"));
        editTableColumn(allocatedTimeTitle);
        TableColumn<ObservableList<String>, String> tagTitle =
                new TableColumn<ObservableList<String>, String>("Tags");
        tagTitle.setCellValueFactory(new PropertyValueFactory<ObservableList<String>, String>("Tags"));
        editTableColumn(tagTitle);

        intervieweeTableView.getColumns().addAll(nameTitle, phoneTitle, emailTitle, facultyTitle, yearTitle,
                departmentTitle, timeSlotTitle, allocatedTimeTitle, tagTitle);
    }

    /**
     * Create a new TableColumn object.
     * @param tableColumn The titles for each columns.
     */
    private void editTableColumn(TableColumn tableColumn) {
        tableColumn.setReorderable(false);
        tableColumn.setSortable(false);
        tableColumn.setMinWidth(80);
    }
}
