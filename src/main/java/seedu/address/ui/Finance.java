package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import seedu.address.logic.Logic;
import seedu.address.model.employee.Employee;

/**
 * A ui for the Schedule Tab that is displayed on the secondary Tab of the application.
 */
public class Finance extends Tabs<AnchorPane> {
    private static final String FXML = "Finance.fxml";


    @FXML
    private ListView<Employee> eventScheduleListView;



    public Finance(ObservableList<Employee> employeeObservableList, Logic logic, MainWindow mainWindow) {
        super(FXML, mainWindow, logic);
            eventScheduleListView.setItems(employeeObservableList);
            eventScheduleListView.setCellFactory(listView -> new EmployeeListViewCell());
    }

    /**
     * Updates the DistinctDate Cards.
     */
    public void updateCards() {
        eventScheduleListView.setCellFactory(listView -> new EmployeeListViewCell());
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Employee} using a {@code EmployeeCard}.
     */
    class EmployeeListViewCell extends ListCell<Employee> {
        @Override
        protected void updateItem(Employee employee, boolean empty) {
            super.updateItem(employee, empty);
            if (empty || employee == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EmployeeCard1(employee, getIndex() + 1).getRoot());
            }
        }
    }


}

