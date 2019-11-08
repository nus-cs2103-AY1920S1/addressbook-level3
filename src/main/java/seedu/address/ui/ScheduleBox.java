/*
@@author shihaoyap
 */

package seedu.address.ui;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.schedule.DisplayScheduleForDateCommand;
import seedu.address.logic.commands.schedule.DisplayScheduleForYearMonthCommand;
import seedu.address.logic.commands.schedule.GenerateScheduleCommand;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Event;

/**
 * A ui for the Schedule Tab that is displayed on the secondary Tab of the application.
 */
public class ScheduleBox extends Tabs<AnchorPane> {
    private static final String FXML = "Schedule.fxml";
    private String[] monthName = {"January", "February", "March", "April",
                                  "May", "June", "July", "August", "September",
                                  "October", "November", "December"};
    private int currentMonthInFocus;
    private int currentYearInFocus;

    @FXML
    private ListView<Event> eventScheduleListView;

    @FXML
    private Label dateLabel;

    @FXML
    private Label monthLabel;

    @FXML
    private Label currentMonthYear;

    @FXML
    private DatePicker datePicker;

    public ScheduleBox(ObservableList<Event> eventList, Logic logic, MainWindow mainWindow) {
        super(FXML, mainWindow, logic);
        Calendar cal = Calendar.getInstance();
        eventScheduleListView.setItems(eventList);
        eventScheduleListView.setCellFactory(listView -> new EventListViewCell());
        dateLabel.setText("Select Date to View Events");
        monthLabel.setText("Select Month to View Events");
        currentMonthInFocus = cal.get(Calendar.MONTH);
        currentYearInFocus = cal.get(Calendar.YEAR);
        currentMonthYear.setText(monthName[currentMonthInFocus] + "," + currentYearInFocus);
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
     * Inputs {@code datePicker.getVaue()} that displays the events for that specific date
     * with an {@code EventCard}.
     */
    @FXML
    private void handlePickDate() throws ParseException, CommandException {
        LocalDate currentDate = datePicker.getValue();
        currentMonthYear.setText(monthName[currentDate.getMonth().getValue() - 1] + "," + currentDate.getYear());
        mainWindow.executeCommand(DisplayScheduleForDateCommand.COMMAND_WORD
                + " " + CliSyntax.PREFIX_DATE
                + datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    /**
     * decreases the current Month and year that is in focus if applicable. Displays all the events that
     * are happening in that current Month and Year.
     * @throws ParseException thrown when input format is in the wrong order or format and could not be parsed.
     * @throws CommandException thrown when input format is in the wrong format.
     */
    @FXML
    private void onPrevClickButton() throws ParseException, CommandException {
        datePicker.getEditor().clear();
        if (currentMonthInFocus == 0) {
            currentYearInFocus--;
            currentMonthInFocus = 11;
        } else {
            currentMonthInFocus--;
        }
        if (currentMonthInFocus < 9) {
            mainWindow.executeCommand(DisplayScheduleForYearMonthCommand.COMMAND_WORD
                    + " " + CliSyntax.PREFIX_YEAR_MONTH + "0"
                    + (currentMonthInFocus + 1) + "/" + currentYearInFocus);
            currentMonthYear.setText(monthName[currentMonthInFocus] + "," + currentYearInFocus);
        } else {
            mainWindow.executeCommand(DisplayScheduleForYearMonthCommand.COMMAND_WORD
                    + " " + CliSyntax.PREFIX_YEAR_MONTH + (currentMonthInFocus + 1) + "/" + currentYearInFocus);
            currentMonthYear.setText(monthName[currentMonthInFocus] + "," + currentYearInFocus);
        }
    }

    /**
     * increases the current Month and year that is in focus if applicable. Displays all the events that
     * are happening in that current Month and Year.
     * @throws ParseException thrown when input format is in the wrong order or format and could not be parsed.
     * @throws CommandException thrown when input format is in the wrong format.
     */
    @FXML
    private void onNextClickButton() throws ParseException, CommandException {
        datePicker.getEditor().clear();
        if (currentMonthInFocus == 11) {
            currentYearInFocus++;
            currentMonthInFocus = 0;
        } else {
            currentMonthInFocus++;
        }
        if (currentMonthInFocus < 9) {
            mainWindow.executeCommand(DisplayScheduleForYearMonthCommand.COMMAND_WORD
                    + " " + CliSyntax.PREFIX_YEAR_MONTH + "0"
                    + (currentMonthInFocus + 1) + "/" + currentYearInFocus);
            currentMonthYear.setText(monthName[currentMonthInFocus] + "," + currentYearInFocus);
        } else {
            mainWindow.executeCommand(DisplayScheduleForYearMonthCommand.COMMAND_WORD
                    + " " + CliSyntax.PREFIX_YEAR_MONTH + (currentMonthInFocus + 1) + "/" + currentYearInFocus);
            currentMonthYear.setText(monthName[currentMonthInFocus] + "," + currentYearInFocus);
        }
    }

    /**
     * generates a new window which will display a new List of date object and all the events which occurs on
     * the corresponding dates.
     * @throws ParseException thrown when input format is in the wrong order or format and could not be parsed.
     * @throws CommandException thrown when input format is in the wrong format.
     */
    @FXML
    private void generateSchedule() throws ParseException, CommandException {
        mainWindow.executeCommand(GenerateScheduleCommand.COMMAND_WORD);
    }


    public void setLabelText(String text) {
        if (text.length() > 7) {
            LocalDate currentDate = LocalDate.parse(text);
            currentMonthYear.setText(monthName[currentDate.getMonth().getValue() - 1] + "," + currentDate.getYear());
            datePicker.setValue(currentDate);
        } else if (text.length() > 1) {
            YearMonth currentYearMonth = YearMonth.parse(text);
            currentMonthYear.setText(monthName[currentYearMonth.getMonth().getValue() - 1]
                    + "," + currentYearMonth.getYear());
            datePicker.getEditor().clear();
        } else {
            currentMonthYear.setText("");
            datePicker.getEditor().clear();
        }
    }


}
