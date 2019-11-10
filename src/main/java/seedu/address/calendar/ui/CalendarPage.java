package seedu.address.calendar.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.calendar.logic.CalendarLogic;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.ViewOnlyMonth;
import seedu.address.calendar.model.date.Year;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.CommandBox;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.CodeWindow;
import seedu.address.ui.HelpWindow;
import seedu.address.ui.Page;
import seedu.address.ui.PageManager;
import seedu.address.ui.PageType;
import seedu.address.ui.ResultDisplay;
import seedu.address.ui.UiPart;

/**
 * Calendar page. This provides users with access to all calendar functionality.
 */
public class CalendarPage extends UiPart<Region> implements Page {
    private static final String FXML = "CalendarPage.fxml";
    private static final PageType pageType = PageType.CALENDAR;
    private static final String FILE_OPS_ERROR_MESSAGE = "Unable to save calendar";

    private ResultDisplay resultDisplay;
    private CalendarLogic calendarLogic;
    private ReadOnlyDoubleProperty monthViewWidth;
    private ListWindow listWindow;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private CodeWindow codeWindow;
    private HelpWindow helpWindow;

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
    @FXML
    GridPane weekHeader;

    /**
     * Creates a calendar page.
     * @param calendarLogic Calendar logic which is used to handle all logic
     */
    public CalendarPage(CalendarLogic calendarLogic) {
        super(FXML);

        this.calendarLogic = calendarLogic;
        monthViewWidth = weekHeader.widthProperty();

        fillInnerParts();
        listWindow = new ListWindow();
        codeWindow = new CodeWindow();
        helpWindow = new HelpWindow();
    }

    /**
     * Gets {@code this} page type.
     * @return {@code PageType.CALENDAR}
     */
    public PageType getPageType() {
        return pageType;
    }

    /**
     * Sets up calendar page by filling up the placeholders.
     */
    private void fillInnerParts() {
        ViewOnlyMonth currentViewOnlyMonth = calendarLogic.getVisibleMonth();
        MonthOfYear monthOfYear = currentViewOnlyMonth.getMonthOfYear();
        MonthHeader monthHeader = new MonthHeader(monthOfYear);
        monthHeaderPlaceholder.getChildren().add(monthHeader.getRoot());

        Year year = currentViewOnlyMonth.getYear();
        YearHeader yearHeader = new YearHeader(year);
        yearHeaderPlaceholder.getChildren().add(yearHeader.getRoot());

        monthViewPlaceholder.getChildren().add(MonthView.generateMonthGrid(currentViewOnlyMonth, monthViewWidth));

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    private void updateCalendarPage(ViewOnlyMonth updatedViewOnlyMonth) {
        Year year = updatedViewOnlyMonth.getYear();
        MonthOfYear monthOfYear = updatedViewOnlyMonth.getMonthOfYear();

        updateYearHeader(year);
        updateMonthHeader(monthOfYear);
        updateMonthView(updatedViewOnlyMonth);
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

    private void updateMonthView(ViewOnlyMonth viewOnlyMonth) {
        monthViewPlaceholder.getChildren().clear();
        monthViewPlaceholder.getChildren().add(MonthView.generateMonthGrid(viewOnlyMonth, monthViewWidth));
    }

    @Override
    public void closeResources() {
        helpWindow.hide();
        codeWindow.hide();
    }

    @FXML
    private void handleExit() {
        closeResources();
        PageManager.closeWindows();
    }

    /**
     * Handles list command by showing list window with the relevant content.
     * @param feedback The relevant content to show user
     */
    private void handleShowList(String feedback) {
        if (!listWindow.isShowing()) {
            listWindow.show(feedback);
        } else {
            listWindow.requestFocus();
        }
    }

    /**
     * Executes the command and returns the result.
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = calendarLogic.executeCommand(commandText);
            logger.info("Command result in calendar: " + commandResult.getFeedbackToUser());

            if (calendarLogic.hasVisibleUpdates()) {
                ViewOnlyMonth updatedViewOnlyMonth = calendarLogic.getVisibleMonth();
                updateCalendarPage(updatedViewOnlyMonth);
                calendarLogic.completeVisibleUpdates();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowList()) {
                handleShowList(commandResult.getFeedbackToUser());
                resultDisplay.setFeedbackToUser("");
            }

            return commandResult;
        } catch (ParseException | CommandException e) {
            logger.info("Exception in calendar: " + e);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }

    @Override
    public Parent getParent() {
        return super.getRoot();
    }
}
