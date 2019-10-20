package seedu.address.calendar.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.calendar.commands.Command;
import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.model.Month;
import seedu.address.calendar.model.MonthOfYear;
import seedu.address.calendar.model.Year;
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
        Month currentMonth = calendar.getMonth();
        MonthOfYear monthOfYear = currentMonth.getMonthOfYear();
        MonthHeader monthHeader = new MonthHeader(monthOfYear);
        monthHeaderPlaceholder.getChildren().add(monthHeader.getRoot());

        Year year = currentMonth.getYear();
        YearHeader yearHeader = new YearHeader(year);
        yearHeaderPlaceholder.getChildren().add(yearHeader.getRoot());

        MonthView monthView = new MonthView(currentMonth);
        monthViewPlaceholder.getChildren().add(monthView.generateMonthGrid());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    private void updateCalendarPage(Month updatedMonth) {
        Year year = updatedMonth.getYear();
        MonthOfYear monthOfYear = updatedMonth.getMonthOfYear();

        updateYearHeader(year);
        updateMonthHeader(monthOfYear);
        updateMonthView(updatedMonth);
    }

    private void updateYearHeader(Year year) {
        YearHeader yearHeader = new YearHeader(year);
        yearHeaderPlaceholder.getChildren().clear();
        yearHeaderPlaceholder.getChildren().add(yearHeader.getRoot());
    }

    private void updateMonthHeader(MonthOfYear monthOfYear) {
        MonthHeader monthHeader = new MonthHeader(monthOfYear);
        monthHeaderPlaceholder.getChildren().clear();
        monthHeaderPlaceholder.getChildren().add(monthHeader.getRoot());
    }

    private void updateMonthView(Month month) {
        MonthView monthView = new MonthView(month);
        monthViewPlaceholder.getChildren().clear();
        monthViewPlaceholder.getChildren().add(monthView.generateMonthGrid());
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
            Month updatedMonth = calendar.getMonth();
            updateCalendarPage(updatedMonth);
            calendar.completeUpdate();
        }
    }
}