package seedu.address.ui;

import java.util.Comparator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.statistics.GenerateStatisticsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;

/**
 * A UI for the Statistics Tab.
 */
public class StatisticsBox extends Tabs<AnchorPane> {
    private static final String FXML = "Statistics.fxml";

    @FXML
    private ListView<Event> eventScheduleListView;

    @FXML
    private Label upcomingLabel;


    public StatisticsBox(ObservableList<Event> eventList, Logic logic, MainWindow mainWindow) {
        super(FXML, mainWindow, logic);
        ObservableList<Event> sortedByStartDateEventList = eventList
                .sorted(Comparator.comparing(Event::getStartDate));
        eventScheduleListView.setItems(sortedByStartDateEventList);
        eventScheduleListView.setCellFactory(listView -> new EventListViewCell());
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
     * generates a new window which will display a new List of date object and all the events which occurs on
     * the corresponding dates.
     * @throws ParseException thrown when input format is in the wrong order or format and could not be parsed.
     * @throws CommandException thrown when input format is in the wrong format.
     */
    @FXML
    private void generateStatistics() throws ParseException, CommandException {
        mainWindow.executeCommand(GenerateStatisticsCommand.COMMAND_WORD);
    }
}
