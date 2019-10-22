package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.DisplayScheduleForDateCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.distinctDate.DistinctDate;
import seedu.address.model.event.Event;

/**
 * A ui for the Schedule Tab that is displayed on the secondary Tab of the application.
 */
public class ScheduleBox extends Tabs<AnchorPane> {
    private static final String FXML = "Schedule.fxml";

    @FXML
    private ListView<Event> eventsListView;

    @FXML
    private ListView<DistinctDate> datesListView;

    @FXML
    private Label dateLabel;

    @FXML
    private DatePicker datePicker;

    public ScheduleBox(ObservableList<Event> eventList, ObservableList<DistinctDate> dateList, Logic logic, MainWindow mainWindow) {
        super(FXML, mainWindow, logic);
        eventsListView.setItems(eventList);
        eventsListView.setCellFactory(listView -> new EventListViewCell());
        datesListView.setItems(dateList);
        datesListView.setCellFactory(listView -> new DateListViewCell());
        dateLabel.setText("Select Date to View Events");
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
                setGraphic(new EventCard(event, getIndex() + 1, mainWindow).getRoot());
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Event} using a {@code EventCard}.
     */
    class DateListViewCell extends ListCell<DistinctDate> {
        @Override
        protected void updateItem(DistinctDate date, boolean empty) {
            super.updateItem(date, empty);
            if (empty || date == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DateCard(date, getIndex() + 1, mainWindow).getRoot());
            }
        }
    }

    /**
     * Inputs {@code datePicker.getVaue()} that displays the events for that specific date
     * with an {@code EventCard}.
     */
    @FXML
    private void handlePickDate() throws ParseException, CommandException {
        System.out.println(datePicker.getValue());

        mainWindow.executeCommand(DisplayScheduleForDateCommand.COMMAND_WORD
                + " " + CliSyntax.PREFIX_DATE
                + datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

}
