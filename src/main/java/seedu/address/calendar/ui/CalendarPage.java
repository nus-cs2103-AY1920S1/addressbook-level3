package seedu.address.calendar.ui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import seedu.address.calendar.commands.Command;
import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.Month;
import seedu.address.calendar.parser.CalendarParser;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.Page;
import seedu.address.ui.PageType;

public class CalendarPage implements Page {

    private final static PageType pageType = PageType.CALENDAR;
    private boolean isOpened = false;

    @FXML
    Scene calendarScene;
    @FXML
    VBox calendarPane = new VBox();
    @FXML
    GridPane weekHeader;
    @FXML
    GridPane monthView;
    @FXML // todo change the following to CommandBox class?
    TextField commandBoxPlaceHolder = new TextField();
    @FXML
    Label monthLabel;

    private Calendar calendar;

    public CalendarPage() {
        calendar = new Calendar();
        commandBoxPlaceHolder = new CommandBox(this::executeCommand).getCommandBox();
        setUp();
    }

    public boolean isOpened() {
        return isOpened;
    }

    void setOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }

    public Scene getScene() {
        return calendarScene;
    }

    public PageType getPageType() {
        return pageType;
    }

    /**
     * Sets up calendar page by laying out nodes.
     */
    private void setUp() {
        weekHeader = WeekHeader.generateWeekHeader();

        Month currentMonth = calendar.getMonth();
        MonthView monthV = new MonthView(currentMonth);
        monthView = monthV.generateMonthGrid();
        monthLabel = monthV.generateMonthLabel();
        monthLabel.setTextAlignment(TextAlignment.CENTER);

        calendarPane.setAlignment(Pos.BOTTOM_LEFT);
        calendarPane.getChildren().addAll(monthLabel, weekHeader, monthView, commandBoxPlaceHolder);
        calendarScene = new Scene(calendarPane);
    }

    private void updateCalendarView(MonthView updatedMonthView) {
        updateMonthLabel(updatedMonthView);
        updateMonthGrid(updatedMonthView);
    }

    private void updateMonthLabel(MonthView updatedMonthView) {
        Label updatedMonthLabel = updatedMonthView.generateMonthLabel();
        calendarPane.getChildren().set(0, updatedMonthLabel);
    }

    private void updateMonthGrid(MonthView updatedMonthView) {
        GridPane updatedMonthGrid = updatedMonthView.generateMonthGrid();
        calendarPane.getChildren().set(2, updatedMonthGrid);
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private void executeCommand(String commandText) throws CommandException, ParseException {
        Command command = (new CalendarParser()).parseCommand(commandText);
        command.execute(calendar);

        if (calendar.hasViewUpdates()) {
            Month newMonth = calendar.getMonth();
            MonthView newMonthView = new MonthView(newMonth);
            updateCalendarView(newMonthView);
            calendar.completeUpdate();
        }
    }
}