package cs.f10.t1.nursetraverse.ui;

import java.util.logging.Logger;

import cs.f10.t1.nursetraverse.commons.core.GuiSettings;
import cs.f10.t1.nursetraverse.commons.core.LogsCenter;
import cs.f10.t1.nursetraverse.logic.Logic;
import cs.f10.t1.nursetraverse.logic.commands.CommandResult;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.logic.parser.exceptions.ParseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
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
    private PatientListPanel patientListPanel;
    private AppointmentListPanel appointmentListPanel;
    private OngoingVisitListPanel ongoingVisitListPanel;
    private AutoCompletePanel autoCompletePanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private CommandBox commandBox;
    private DataPanelsTabPaneManager dataPanelsTabPaneManager;
    private HistoryPanel historyPanel;
    private AutoCompletePanelManager autoCompletePanelManager;

    @FXML
    private StackPane commandBoxPlaceholder;
    @FXML
    private MenuItem helpMenuItem;
    @FXML
    private StackPane patientListPanelPlaceholder;
    @FXML
    private StackPane autoCompletePanelPlaceholder;
    @FXML
    private StackPane resultDisplayPlaceholder;
    @FXML
    private StackPane statusbarPlaceholder;
    @FXML
    private TabPane dataPanelsTabPane;
    @FXML
    private Tab patientTabPage;
    @FXML
    private Tab ongoingVisitTabPage;
    @FXML
    private Tab appointmentTabPage;
    @FXML
    private StackPane appointmentListPanelPlaceholder;
    @FXML
    private StackPane ongoingVisitPanelPlaceholder;
    @FXML
    private StackPane historyPanelPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

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
     *
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
        patientListPanel = new PatientListPanel(logic.getFilteredPatientList());
        patientListPanelPlaceholder.getChildren().add(patientListPanel.getRoot());

        ongoingVisitListPanel = new OngoingVisitListPanel(logic.getObservableOngoingVisitList());
        ongoingVisitPanelPlaceholder.getChildren().add(ongoingVisitListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getPatientBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        autoCompletePanel = new AutoCompletePanel();
        autoCompletePanelPlaceholder.getChildren().add(autoCompletePanel.getRoot());
        autoCompletePanelManager = new AutoCompletePanelManager(
                autoCompletePanel,
                logic.getFilteredPatientList(),
                logic.getFilteredAppointmentList(),
                logic.getObservableHistoryList());

        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        historyPanel = new HistoryPanel(logic.getObservableHistoryList());
        historyPanelPlaceholder.getChildren().add(historyPanel.getRoot());

        appointmentListPanel = new AppointmentListPanel(logic.getFilteredAppointmentList());
        appointmentListPanelPlaceholder.getChildren().add(appointmentListPanel.getRoot());

        // Add observers into commandBox observer list
        commandBox.addObserver(autoCompletePanelManager);
        commandBox.addObserver(resultDisplay);
        // Set data sender for commandBox
        commandBox.setDataSender(autoCompletePanelManager);

        dataPanelsTabPaneManager = new DataPanelsTabPaneManager(dataPanelsTabPane,
                patientTabPage,
                ongoingVisitTabPage,
                appointmentTabPage);
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
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PatientListPanel getPatientListPanel() {
        return patientListPanel;
    }

    public OngoingVisitListPanel getOngoingVisitListPanel() {
        return ongoingVisitListPanel;
    }

    public AppointmentListPanel getAppointmentListPanel() {
        return appointmentListPanel;
    }

    public DataPanelsTabPaneManager getDataPanelsTabPaneManager() {
        return dataPanelsTabPaneManager;
    }

    public HistoryPanel getHistoryPanel() {
        return historyPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
