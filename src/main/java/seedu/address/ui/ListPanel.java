package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;

/**
 * Panel containing the list of persons and events.
 */
public class ListPanel extends UiPart<Region> {
    private static final String FXML = "ListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ListPanel.class);

    @FXML
    private ListView<Employee> personListView;

    @FXML
    private ListView<Event> eventListView;


    public ListPanel(ObservableList<Employee> employeeList, ObservableList<Event> eventList, MainWindow mainWindow) {
        super(FXML);
        personListView.setItems(employeeList);
        personListView.setCellFactory(listView -> new EmployeeListViewCell(mainWindow));
        eventListView.setItems(eventList);
        eventListView.setCellFactory(listView -> new EventListViewCell(mainWindow));
    }



    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Employee} using a {@code EmployeeCard}.
     */
    class EmployeeListViewCell extends ListCell<Employee> {
        private MainWindow mainWindow;

        EmployeeListViewCell(MainWindow mainWindow) {
            this.mainWindow = mainWindow;
        }

        @Override
        protected void updateItem(Employee employee, boolean empty) {
            super.updateItem(employee, empty);
            if (empty || employee == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EmployeeCard(employee, getIndex() + 1, mainWindow).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Event} using a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<Event> {
        private MainWindow mainWindow;

        EventListViewCell(MainWindow mainWindow) {
            this.mainWindow = mainWindow;
        }
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);
            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                EventCard eventCard = new EventCard(event, getIndex() + 1, mainWindow);
                setGraphic(eventCard.getRoot());
            }
        }
    }

}
