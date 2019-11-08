package com.typee.ui;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import com.typee.commons.core.GuiSettings;
import com.typee.commons.core.LogsCenter;
import com.typee.commons.exceptions.DataConversionException;
import com.typee.commons.util.PdfUtil;
import com.typee.logic.Logic;
import com.typee.logic.commands.CalendarCloseDisplayCommand;
import com.typee.logic.commands.CalendarNextMonthCommand;
import com.typee.logic.commands.CalendarOpenDisplayCommand;
import com.typee.logic.commands.CalendarPreviousMonthCommand;
import com.typee.logic.commands.CommandResult;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.ui.calendar.CalendarWindow;
import com.typee.ui.calendar.exceptions.CalendarCloseDisplayException;
import com.typee.ui.game.StartWindow;
import com.typee.ui.report.ReportWindow;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private EngagementListPanel engagementListPanel;
    //Tab related attributes.
    private TabPanel tabPanel;
    private ObservableList<Tab> tabList;
    private Tab currentTab;

    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    //Added tab panel by Ko Gi Hun 8/10/19
    @FXML
    private StackPane tabPanelPlaceHolder;

    //main window VBox
    @FXML
    private VBox mainWindow;

    @FXML
    private Label lblWindowTitle;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic)
            throws DataConversionException {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure tab list generated from json
        this.tabList = logic.getTabList();

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        currentTab = new Tab("Engagement");
        currentTab.setController(new EngagementListPanel(logic.getFilteredEngagementList()));

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() throws DataConversionException {
        lblWindowTitle.setText("Engagement Window");
        engagementListPanel = new EngagementListPanel(logic.getFilteredEngagementList());
        mainWindow.getChildren().add(engagementListPanel.getRoot());

        //adding tab panel holder
        tabPanel = new TabPanel(tabList);
        tabPanelPlaceHolder.getChildren().add(tabPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getEngagementListFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    public void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        currentTab.getController().handleExit();
        primaryStage.hide();
    }

    /**
     * Switch the window to the {@code Tab} specified.
     */
    private void handleTabSwitch(Tab tabInput) {
        currentTab.getController().handleExit();
        Parent root = tabInput.getController().getRoot();
        mainWindow.getChildren().clear();
        mainWindow.getChildren().add(root);
        lblWindowTitle.setText(tabInput.getName() + " Window");
        currentTab = tabInput;
    }

    /**
     * Handles the calendar interaction represented by the specified {@code CommandResult}.
     * @param commandResult The specified {@code CommandResult}.
     */
    private void handleCalendarInteraction(CommandResult commandResult) throws CommandException {
        if (currentTab == null || !(currentTab.getController() instanceof CalendarWindow)) {
            throw new CommandException("Calendar commands can only be used in the calendar window.");
        }
        CalendarWindow calendarWindow = (CalendarWindow) currentTab.getController();
        String calendarCommandType = commandResult.getCalendarCommandType();
        switch (calendarCommandType) {
        case CalendarOpenDisplayCommand.COMMAND_WORD:
            calendarWindow.openSingleDayEngagementsDisplayWindow(commandResult.getCalendarDate());
            break;
        case CalendarCloseDisplayCommand.COMMAND_WORD:
            try {
                calendarWindow.closeSingleDayEngagementsDisplayWindow(commandResult.getCalendarDate());
                break;
            } catch (CalendarCloseDisplayException e) {
                throw new CommandException(e.getMessage());
            }
        case CalendarNextMonthCommand.COMMAND_WORD:
            calendarWindow.populateCalendarWithNextMonth();
            break;
        case CalendarPreviousMonthCommand.COMMAND_WORD:
            calendarWindow.populateCalendarWithPreviousMonth();
            break;
        default:
            throw new CommandException("Invalid calendar command.");
        }
    }

    private void handlePdf(Path docPath) throws IOException {
        PdfUtil.openDocument(docPath);
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException, IOException {
        try {
            CommandResult commandResult = logic.execute(commandText);

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isTabCommand()) {
                Tab tab = commandResult.getTab();
                handleTabSwitch(fetchTabInformation(tab.getName()));
            }

            if (commandResult.isCalendarCommand()) {
                handleCalendarInteraction(commandResult);
            }

            if (commandResult.isPdfCommand()) {
                handlePdf(commandResult.getDocPath());
            }

            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            return commandResult;
        } catch (CommandException | ParseException | IOException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Fetches tab information from the tab menu list to the tab retrieved after {@code TabCommand}
     */
    private Tab fetchTabInformation(String tabName) throws IOException {
        Tab tabToReturn = new Tab();
        for (Tab tabInList : tabList) {
            if (tabInList.getName().equals(tabName)) {
                tabToReturn = tabInList;
                logger.info("tab matches: " + tabToReturn);
                break;
            }
        }
        switch (tabName) {
        case "Calendar":
            tabToReturn.setController(new CalendarWindow(logic.getFilteredEngagementList()));
            break;
        case "TypingGame":
            tabToReturn.setController(new StartWindow());
            break;
        case "Report":
            tabToReturn.setController(new ReportWindow(logic.getSortedEngagementList()));
            break;
        case "Engagement":
            tabToReturn.setController(new EngagementListPanel(logic.getFilteredEngagementList()));
            break;
        default:
            break;
        }
        logger.info("tab after fetch: " + tabToReturn);
        return tabToReturn;
    }
}
