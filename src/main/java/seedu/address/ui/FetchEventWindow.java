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
import seedu.address.logic.commands.allocate.AutoAllocateCommand;
import seedu.address.logic.commands.allocate.DeallocateCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;

/**
 * Controller for a fetch page
 */
public class FetchEventWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(FetchEventWindow.class);
    private static final String FXML = "FetchEventWindow.fxml";
    private final Logic logic;
    private final int eventOneBasedIndex;
    private final Integer index;
    private ObservableList<Employee> employeeList;
    private ObservableList<Event> filteredEventList;
    private Event event;
    private ErrorWindow errorWindow;

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
     * Creates a new FetchEventWindow.
     *
     * @param root Stage to use as the root of the FetchEventWindow.
     */
    public FetchEventWindow(Stage root, Logic logic, Integer index) {
        super(FXML, root);
        this.logic = logic;
        this.index = index;
        this.eventOneBasedIndex = index + 1;

        employeeList = logic.getFullEmployeeList();
        filteredEventList = logic.getFilteredEventList();
        updateCards();

        EventHandler<MouseEvent> handleAllocate = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                try {
                    logic.execute(AutoAllocateCommand.COMMAND_WORD + " " + eventOneBasedIndex);
                    updateCards();
                } catch (CommandException | ParseException ex) {
                    if (errorWindow != null) {
                        errorWindow.hide();
                    }
                    errorWindow = new ErrorWindow(ex.getMessage());
                    errorWindow.show();
                }
            }
        };

        EventHandler<MouseEvent> handleFree = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                int oneBasedIndex = index + 1;
                try {
                    logic.execute(DeallocateCommand.COMMAND_WORD + " " + oneBasedIndex);
                    updateCards();
                } catch (CommandException | ParseException ex) {
                    if (errorWindow != null) {
                        errorWindow.hide();
                    }
                    errorWindow = new ErrorWindow(ex.getMessage());
                    errorWindow.show();
                }
            }
        };
        allocateButton.addEventFilter(MouseEvent.MOUSE_CLICKED, handleAllocate);
        freeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, handleFree);
    }

    /**
     * Creates a new FetchEventWindow.
     */
    public FetchEventWindow(Logic logic, Integer index) {
        this(new Stage(), logic, index);
    }

    /**
     * Updates the EmployeeCard and EventDescriptions.
     */
    public void updateCards() {
        event = filteredEventList.get(index);
        ObservableList<Employee> employeeListForEvent = getEmployeeListForEvent(event, employeeList);
        eventDescription.setText(event.toStringWithNewLine());
        personListView.setItems(employeeList.filtered(x -> event.isAvailableForEvent(x, filteredEventList)));
        personListView.setCellFactory(listView -> new AvailablePersonListViewCell(this));
        eventListView.setItems(employeeListForEvent);
        eventListView.setCellFactory(listView -> new CurrentPersonListViewCell(this));
    }

    /**
     * Gets the current list of employees for the event.
     */
    public ObservableList<Employee> getEmployeeListForEvent(Event event, ObservableList<Employee> employeeList) {
        ObservableList<Employee> list = event.getManpowerAllocatedList().getManpowerList().stream()
                .flatMap(x -> employeeList.stream().map(y -> y.getEmployeeId().equals(x) ? y : new Employee()))
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
     * Focuses on the fetch window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Employee} using a {@code EmployeeCard}.
     */
    class AvailablePersonListViewCell extends ListCell<Employee> {
        private FetchEventWindow fetchWindow;

        AvailablePersonListViewCell(FetchEventWindow fetchWindow) {
            this.fetchWindow = fetchWindow;
        }

        @Override
        protected void updateItem(Employee employee, boolean empty) {
            super.updateItem(employee, empty);
            if (empty || employee == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EmployeeCard(employee, getIndex() + 1, logic, event,
                        eventOneBasedIndex, fetchWindow, true).getRoot());
            }
        }

    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Employee} using a {@code EmployeeCard}.
     */
    class CurrentPersonListViewCell extends ListCell<Employee> {
        private FetchEventWindow fetchWindow;

        CurrentPersonListViewCell(FetchEventWindow fetchWindow) {
            this.fetchWindow = fetchWindow;
        }

        @Override
        protected void updateItem(Employee employee, boolean empty) {
            super.updateItem(employee, empty);
            if (empty || employee == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EmployeeCard(employee, getIndex() + 1, logic, event,
                        eventOneBasedIndex, fetchWindow, false).getRoot());
            }

        }
    }

}
