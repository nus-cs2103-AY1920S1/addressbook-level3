package seedu.address.calendar.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.calendar.commands.Command;
import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.Month;
import seedu.address.calendar.model.MonthOfYear;
import seedu.address.calendar.parser.CalendarParser;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.Page;
import seedu.address.ui.PageType;
import seedu.address.ui.UiPart;

public class CalendarPage extends UiPart<Scene> implements Page {
    private static final String FXML = "CalendarPage.fxml";
    private final static PageType pageType = PageType.CALENDAR;

    @FXML
    VBox calendarPane = new VBox();
    @FXML
    GridPane weekHeader;
    @FXML
    GridPane monthView;
    @FXML
    StackPane commandBoxPlaceholder;
    @FXML
    StackPane monthHeaderPlaceholder;
    @FXML
    StackPane yearHeaderPlaceholder;
    @FXML
    StackPane monthViewPlaceholder;
    @FXML
    VBox resultDisplayPlaceholder;


    private Calendar calendar;

    public CalendarPage() {
        super(FXML);
        calendar = new Calendar();
        fillInnerParts();
    }

    public Scene getScene() {
        return getRoot();
    }

    public PageType getPageType() {
        return pageType;
    }

    /**
     * Sets up calendar page by laying out nodes.
     */
    private void fillInnerParts() {
        Month currentMonth = Month.copy(calendar.getMonth());
        MonthOfYear monthOfYear = currentMonth.getMonthOfYear();
        MonthHeader monthHeader = new MonthHeader(monthOfYear);
        monthHeaderPlaceholder.getChildren().add(monthHeader.getRoot());

        int year = currentMonth.getYear();
        YearHeader yearHeader = new YearHeader(year);
        yearHeaderPlaceholder.getChildren().add(yearHeader.getRoot());

        monthViewPlaceholder.getChildren().add(new MonthView(currentMonth).generateMonthGrid());

        resultDisplayPlaceholder.getChildren().add(new ResultDisplay().getRoot());

        commandBoxPlaceholder.getChildren().add(new CommandBox(this::executeCommand).getRoot());
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