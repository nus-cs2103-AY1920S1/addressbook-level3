package seedu.address.ui;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;

/**
 * Controller for a fetch page
 */
public class FetchWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(FetchWindow.class);
    private static final String FXML = "FetchWindow.fxml";

    @FXML
    private ListView<Employee> personListView;

    @FXML
    private ListView<Employee> eventListView;

    @FXML
    private Text eventDescription;

    /**
     * Creates a new FetchWindow.
     *
     * @param root Stage to use as the root of the FetchWindow.
     */
    public FetchWindow(Stage root, ObservableList<Employee> employeeList,
                       ObservableList<Event> filteredEventList, Event event) {
        super(FXML, root);
        ObservableList<Employee> list = event.getManpowerAllocatedList().getManpowerList().stream()
                .flatMap(x -> employeeList.stream().map(y -> y.getEmployeeId().id.equals(x) ? y : new Employee()))
                .filter(employee -> employee.getEmployeeName() != null)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        personListView.setItems(employeeList.filtered(x -> event.isAvailableForEvent(x, filteredEventList)));
        personListView.setCellFactory(listView -> new PersonListViewCell());
        eventListView.setItems(list);
        eventListView.setCellFactory(listView -> new FetchWindow.PersonListViewCell());
        eventDescription.setText("EVENT DISPLAYED:" + event.toString());
    }

    /**
     * Creates a new FetchWindow.
     */
    public FetchWindow(ObservableList<Employee> employeeList,
                       ObservableList<Event> filteredEventList, Event event) {
        this(new Stage(), employeeList, filteredEventList, event);
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing fetched event.");
        getRoot().show();
        getRoot().centerOnScreen();

    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
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
            if (empty || event == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EventCard(event, getIndex() + 1).getRoot());
            }
        }
    }

}
