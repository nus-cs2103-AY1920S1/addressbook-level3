package seedu.address.ui;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
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
    private TextArea eventDescription;

    @FXML
    private Text currentListHeader;

    @FXML
    private Text eventHeader;

    @FXML
    private Text availableListHeader;

    @FXML
    private Button allocateButton;

    @FXML
    private Button freeButton;



    /**
     * Creates a new FetchWindow.
     *
     * @param root Stage to use as the root of the FetchWindow.
     */
    public FetchWindow(Stage root, Logic logic, Integer index) {
        super(FXML, root);
        ObservableList<Employee> employeeList = logic.getFilteredEmployeeList();
        ObservableList<Event> filteredEventList = logic.getFilteredEventList();
        Event event = logic.getFilteredEventList().get(index);
        ObservableList<Employee> employeeListForEvent = getEmployeeListForEvent(event, employeeList);

        eventDescription.setText(event.toStringWithNewLine());
        personListView.setItems(employeeList.filtered(x -> event.isAvailableForEvent(x, filteredEventList)));
        personListView.setCellFactory(listView -> new PersonListViewCell());
        eventListView.setItems(employeeListForEvent);
        eventListView.setCellFactory(listView -> new FetchWindow.PersonListViewCell());
        EventHandler<MouseEvent> handleAllocate = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                int oneBasedIndex = index + 1;
                try {
                    logic.execute("allocate " + oneBasedIndex);
                    Event event = logic.getFilteredEventList().get(index);
                    ObservableList<Employee> employeeListForEvent = getEmployeeListForEvent(event, employeeList);
                    eventListView.setItems(employeeListForEvent);
                    eventListView.setCellFactory(listView -> new FetchWindow.PersonListViewCell());
                } catch (CommandException ex) {
                    logger.fine("This should not appear!");
                } catch (ParseException ex) {
                    logger.fine("This should not appear!");
                }
            }
        };

        EventHandler<MouseEvent> handleFree = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                int oneBasedIndex = index + 1;
                try {
                    logic.execute("free " + oneBasedIndex);
                    Event event = logic.getFilteredEventList().get(index);
                    ObservableList<Employee> employeeListForEvent = getEmployeeListForEvent(event, employeeList);
                    eventListView.setItems(employeeListForEvent);
                    eventListView.setCellFactory(listView -> new FetchWindow.PersonListViewCell());
                } catch (CommandException ex) {
                    logger.fine("This should not appear!");
                } catch (ParseException ex) {
                    logger.fine("This should not appear!");
                }
            }
        };
        allocateButton.addEventFilter(MouseEvent.MOUSE_CLICKED, handleAllocate);
        freeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, handleFree);
    }

    /**
     * Creates a new FetchWindow.
     */
    public FetchWindow(Logic logic, Integer index) {
        this(new Stage(), logic, index);
    }

    /**
     * Gets the current list of employees for the event.
     */
    public ObservableList<Employee> getEmployeeListForEvent(Event event, ObservableList<Employee> employeeList) {
        ObservableList<Employee> list = event.getManpowerAllocatedList().getManpowerList().stream()
                .flatMap(x -> employeeList.stream().map(y -> y.getEmployeeId().id.equals(x) ? y : new Employee()))
                .filter(employee -> employee.getEmployeeName() != null)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return list;
    }

    /**
     * Shows the fetch window.
     */
    public void show() {
        logger.fine("Showing fetched event.");
        getRoot().show();
        getRoot().centerOnScreen();

    }

    /**
     * Returns true if the fetch window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the fetch window.
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
                setGraphic(new EventCard(event, getIndex() + 1, null).getRoot());
            }
        }
    }

}
