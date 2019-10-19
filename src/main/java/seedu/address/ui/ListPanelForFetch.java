package seedu.address.ui;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;

/**
 * Panel containing the list of persons and events.
 */
public class ListPanelForFetch extends UiPart<Region> {
    private static final String FXML = "ListPanelForFetch.fxml";
    private final Logger logger = LogsCenter.getLogger(ListPanel.class);

    @FXML
    private ListView<Employee> personListView;

    @FXML
    private ListView<Employee> eventListView;

    @FXML
    private Text eventDescription;

    /**
     * Constructor for fetch command
     */
    public ListPanelForFetch(ObservableList<Employee> employeeList,
                             ObservableList<Event> filteredEventList, Event event) {

        super(FXML);
        ObservableList<Employee> list = event.getManpowerAllocatedList().getManpowerList().stream()
                .flatMap(x -> employeeList.stream().map(y -> y.getEmployeeId().id.equals(x) ? y : new Employee()))
                .filter(employee -> employee.getEmployeeName() != null)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        personListView.setItems(employeeList.filtered(x -> event.isAvailableForEvent(x, filteredEventList)));
        personListView.setCellFactory(listView -> new PersonListViewCell());
        eventListView.setItems(list);
        eventListView.setCellFactory(listView -> new PersonListViewCell());
        eventDescription.setText("EVENT DISPLAYED:" + event.toString());
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Employee} using a {@code EmployeeCard}.
     */
    class PersonListViewCell extends ListCell<Employee> {
        @Override
        protected void updateItem(Employee employee, boolean empty) {
            super.updateItem(employee, empty);
            if (empty || employee == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EmployeeCard(employee, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Event} using a {@code EventCard}.
     */
    class EventListViewCell extends ListCell<Event> {
        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);
            //setGraphic(new EventCard(event, getIndex() + 1).getRoot());
            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventCard(event, getIndex() + 1).getRoot());
            }
        }
    }

}
