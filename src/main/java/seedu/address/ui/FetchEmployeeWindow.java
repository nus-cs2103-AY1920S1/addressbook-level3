package seedu.address.ui;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;

import seedu.address.logic.processor.DistinctDatesProcessor;
import seedu.address.model.distinctdate.DistinctDate;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;
//import seedu.address.model.employee.Employee;

/**
 * New Window to display the newly generated List of DistinctDate Objects
 */
public class FetchEmployeeWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(FetchEmployeeWindow.class);
    private static final String FXML = "FetchEmployeeWindow.fxml";

    @FXML
    private ListView<DistinctDate> dateListView;

    @FXML
    private AnchorPane employeeCard;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView imgBox;

    /**
     * Creates a new FetchEmployeeWindow.
     *
     * @param root Stage to use as the root of the FetchEmployeeWindow.
     */
    private FetchEmployeeWindow(Stage root, Logic logic, Integer index) {
        super(FXML, root);
        Employee employee = logic.getFilteredEmployeeList().get(index);
        if (employee.getEmployeeGender().gender.equals("male")) {
            Image image = new Image("/images/maleEmployee.png");
            imgBox.setImage(image);
        } else {
            Image image = new Image("/images/femaleEmployee.png");
            imgBox.setImage(image);
        }
        ObservableList<Event> eventDateList = logic.getFullEventList();
        updateCards(employee, eventDateList);
        name.setText(employee.getEmployeeName().fullName + " ID: " + employee.getEmployeeId().id); //for debug
        phone.setText(employee.getEmployeePhone().value);
        address.setText(employee.getEmployeeAddress().value);
        email.setText(employee.getEmployeeEmail().value);
        employee.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Creates a new FetchEmployeeWindow.
     */
    public FetchEmployeeWindow(Logic logic, Integer index) {
        this(new Stage(), logic, index);
    }

    /**
     * Updates the DistinctDate Cards.
     */
    private void updateCards(Employee employee, ObservableList<Event> eventDateList) {
        List<DistinctDate> dateList = DistinctDatesProcessor.generateEmployeesDistinctDateList(eventDateList, employee);
        ObservableList<DistinctDate> observableDateList = FXCollections.observableList(dateList);
        dateListView.setItems(observableDateList);
        dateListView.setCellFactory(listView -> new DateListViewCell());
    }

    /**
     * Shows the FetchEmployeeWindow.
     */
    public void show() {
        logger.fine("Showing generated employee schedule - all dates and corresponding events.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the FetchEmployeeWindow is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the FetchEmployeeWindow.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the FetchEmployeeWindow.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code DistinctDate} using a {@code DateCard}.
     */
    class DateListViewCell extends ListCell<DistinctDate> {
        @Override
        protected void updateItem(DistinctDate date, boolean empty) {
            super.updateItem(date, empty);
            if (empty || date == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DateCard(date, getIndex() + 1, null).getRoot());
            }
        }
    }
}
