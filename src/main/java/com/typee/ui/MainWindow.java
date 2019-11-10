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
import com.typee.logic.commands.TabCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.exceptions.ParseException;
import com.typee.ui.calendar.CalendarWindow;
import com.typee.ui.calendar.exceptions.CalendarInteractionException;
import com.typee.ui.game.StartWindow;
import com.typee.ui.report.ReportWindow;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private ObservableList<Tab> tabList;
    private Tab currentTab;

    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane tabPanelPlaceHolder;

    @FXML
    private TabPane menuTabPane;

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
    void fillInnerParts() {
        lblWindowTitle.setText("Engagement Window");
        engagementListPanel = new EngagementListPanel(logic.getFilteredEngagementList());
        mainWindow.getChildren().add(engagementListPanel.getRoot());
        prepareTabMenuList(menuTabPane);

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
     * @@author nordic96
     */
    private void handleTabSwitch(Tab tabInput) {
        currentTab.getController().handleExit();
        logger.info(tabInput.toString());

        Parent root = tabInput.getController().getRoot();

        mainWindow.getChildren().clear();
        mainWindow.getChildren().add(root);
        lblWindowTitle.setText(tabInput.getName() + " Window");
        currentTab = tabInput;

        menuTabPane.getTabs().stream()
                .filter(tab -> tab.getText().equals(tabInput.getName()))
                .forEach(tab -> menuTabPane.getSelectionModel().select(tab));
    }

    /**
     * Handles the calendar interaction represented by the specified {@code CommandResult}.
     *
     * @param commandResult The specified {@code CommandResult}.
     * @throws CommandException If the calendar interaction is invalid.
     */
    private void handleCalendarInteraction(CommandResult commandResult) throws CommandException {
        verifyCurrentTabAsCalendarWindow();
        executeCalendarInteraction(commandResult);
    }

    /**
     * Verifies that the current tab is the calendar window.
     *
     * @throws CommandException If the current tab is not the calendar window.
     */
    private void verifyCurrentTabAsCalendarWindow() throws CommandException {
        if (!(currentTab.getController() instanceof CalendarWindow)) {
            throw new CommandException("Calendar commands can only be used in the calendar window.");
        }
    }

    /**
     * Executes a calendar interaction based on the specified {@code CommandResult}.
     *
     * @param commandResult The specified {@code CommandResult}.
     * @throws CommandException If the calendar interaction is invalid.
     */
    private void executeCalendarInteraction(CommandResult commandResult) throws CommandException {
        CalendarWindow calendarWindow = (CalendarWindow) currentTab.getController();
        try {
            switch (commandResult.getCalendarCommandType()) {
            case CalendarOpenDisplayCommand.COMMAND_WORD:
                calendarWindow.openSingleDayEngagementsDisplayWindow(commandResult.getCalendarDate());
                break;
            case CalendarCloseDisplayCommand.COMMAND_WORD:
                calendarWindow.closeSingleDayEngagementsDisplayWindow(commandResult.getCalendarDate());
                break;
            case CalendarNextMonthCommand.COMMAND_WORD:
                calendarWindow.populateCalendarWithNextMonth();
                break;
            case CalendarPreviousMonthCommand.COMMAND_WORD:
                calendarWindow.populateCalendarWithPreviousMonth();
                break;
            default:
                throw new CommandException("Invalid calendar command.");
            }
        } catch (CalendarInteractionException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Handles the MainWindow after executing PdfCommand, where checking if the current tab is in report window,
     * ReportWindow will call method to refresh display in ReportWindow.
     * @param docPath {@code Path} of the document file generated.
     * @throws IOException if document file cannot be accessed.
     */
    private void handlePdf(Path docPath) throws IOException {
        PdfUtil.openDocument(docPath);
        if (this.currentTab.getController() instanceof ReportWindow) {
            ReportWindow reportWindow = ((ReportWindow) this.currentTab.getController());
            reportWindow.handleStatusLblAfterDocGen(docPath);
        }
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
     * @@author nordic96
     */
    private Tab fetchTabInformation(String tabName) {
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

    /**
     * Populates tabListView with {@code ObservableList<Tab>}.
     * @@author nordic96
     */
    private void prepareTabMenuList(TabPane menuTabPane) {
        menuTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabList.stream()
                .forEach(x -> {
                    javafx.scene.control.Tab tab = new javafx.scene.control.Tab(x.getName());
                    menuTabPane.getTabs().addAll(tab);
                });

        menuTabPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Tab selectedTab = new Tab(menuTabPane.getSelectionModel().getSelectedItem().getText());
                assert selectedTab != null;

                logger.info("tab: " + selectedTab.getName() + " selected.");
                selectedTab = fetchTabInformation(selectedTab.getName());
                handleTabSwitch(selectedTab);
                resultDisplay.setFeedbackToUser(TabCommand.MESSAGE_SUCCESS + selectedTab.getName());
            }
        });
    }
}
