package seedu.address.ui;

import static java.awt.Desktop.getDesktop;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.OmniPanelTab;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.autocomplete.AutoCompleter;
import seedu.address.ui.commandboxhistory.CommandBoxHistory;
import seedu.address.ui.queue.QueueListPanel;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> implements AutoComplete, OmniPanel {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;
    private AutoCompleter autoCompleter;
    private CommandBoxHistory commandBoxHistory;
    private OmniPanelTab currentOmniPanelTab;

    private HashSet<Runnable> deferredDropSelectors;

    // Independent Ui parts residing in this Ui container
    private AutoCompleteOverlay aco;
    private CommandBox commandBox;
    private PersonListPanel patientListPanel;
    private PersonListPanel staffListPanel;
    private QueueListPanel queueListPanel;
    private EventListPanel appointmentListPanel;
    private EventListPanel dutyShiftListPanel;
    private ResultDisplay resultDisplay;
    private TabBar tabBar;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane omniPanelPlaceholder;

    @FXML
    private StackPane queueListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane tabBarPlaceholder;

    @FXML
    private SplitPane upperPane;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.autoCompleter = new AutoCompleter();
        this.commandBoxHistory = new CommandBoxHistory();

        this.deferredDropSelectors = new HashSet<>();

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        getRoot().addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deferredDropSelectors.forEach(e -> e.run());
            }
        });

        upperPane.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                case TAB:
                    event.consume();
                    commandBox.getRoot().requestFocus();
                    break;
                default:
                }
            }
        });

        logic.bindOmniPanelTabConsumer(this::setOmniPanelTab);
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
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        patientListPanel = new PersonListPanel(logic.getFilteredPatientList(), deferredDropSelectors);
        staffListPanel = new PersonListPanel(logic.getFilteredStaffList(), deferredDropSelectors);

        appointmentListPanel = new EventListPanel(logic.getFilteredAppointmentList());
        dutyShiftListPanel = new EventListPanel(logic.getFilteredDutyShiftList());

        tabBar = new TabBar(this);
        tabBarPlaceholder.getChildren().add(tabBar.getRoot());

        queueListPanel = new QueueListPanel(logic.getConsultationRoomList(),
                logic.getQueueList(), logic.getReferenceIdResolver());
        queueListPanelPlaceholder.getChildren().add(queueListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBox = new CommandBox(this::executeCommand, this);
        commandBoxPlaceholder.getChildren().addAll(commandBox.getRoot());

        aco = new AutoCompleteOverlay(this::autoCompleterSelected);
        anchorPane.getChildren().add(aco.getRoot());
        anchorPane.setBottomAnchor(aco.getRoot(), 0.0);
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        //Screen Size
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = gd.getDisplayMode().getWidth();
        int screenHeight = gd.getDisplayMode().getHeight();

        if (screenHeight < guiSettings.getWindowHeight() || screenWidth < guiSettings.getWindowWidth()) {
            return;
        }

        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());

        if (guiSettings.getWindowCoordinates() == null
                || screenWidth < guiSettings.getWindowWidth() + guiSettings.getWindowCoordinates().getX()
                || screenHeight < guiSettings.getWindowHeight() + guiSettings.getWindowCoordinates().getY()) {
            return;
        }

        primaryStage.setX(guiSettings.getWindowCoordinates().getX());
        primaryStage.setY(guiSettings.getWindowCoordinates().getY());
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        try {
            getDesktop().browse(URI.create("https://clerkpro.netlify.com/userguide#Features"));
        } catch (IOException e) {
            e.printStackTrace();
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
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
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

    /**
     * Called whenever AutoComplete selected command.
     */
    public void updateCommandAutoComplete(String commandText) {
        aco.showSuggestions(commandText, autoCompleter.update(commandText).getSuggestions());
        Region acoRoot = aco.getRoot();
        acoRoot.setTranslateX(Math.min(acoRoot.getTranslateX(), getRoot().getWidth() - acoRoot.getWidth()));
        commandBox.restoreFocusLater();
        logic.eagerEvaluate(commandText);
    }

    /**
     * Receives Key Press event from Command Box and executes expected behaviours.
     */
    public void updateSelectionKeyPressedCommandBox(KeyCode keyCode) {
        switch (keyCode) {
        case UP:
            if (aco.isSuggesting()) {
                aco.traverseSelection(true);
                break;
            }
            commandBox.setCommandTextField(commandBoxHistory.getOlder());
            break;
        case DOWN:
            if (aco.isSuggesting()) {
                aco.traverseSelection(false);
                break;
            }
            commandBox.setCommandTextField(commandBoxHistory.getNewer());
            break;
        case ENTER:
            if (aco.isSuggesting()) {
                aco.simulateMouseClick();
                break;
            }
            commandBoxHistory.add(commandBox.handleCommandEntered());
            break;
        default:
        }
    }

    /**
     * Called whenever AutoComplete has a selection.
     */
    private void autoCompleterSelected(String selectedText) {
        commandBox.appendCommandTextField(selectedText);
    }

    /**
     * Sets OmniPanelTab.
     */
    @Override
    public void setOmniPanelTab(OmniPanelTab omniPanelTab) {
        currentOmniPanelTab = omniPanelTab;
        tabBar.selectTabUsingIndex(omniPanelTab.getTabBarIndex());
        switch (omniPanelTab) {
        case PATIENTS_TAB:
            omniPanelPlaceholder.getChildren().setAll(patientListPanel.getRoot());
            break;
        case APPOINTMENTS_TAB:
            omniPanelPlaceholder.getChildren().setAll(appointmentListPanel.getRoot());
            break;
        case DOCTORS_TAB:
            omniPanelPlaceholder.getChildren().setAll(staffListPanel.getRoot());
            break;
        case DUTY_SHIFT_TAB:
            omniPanelPlaceholder.getChildren().setAll(dutyShiftListPanel.getRoot());
            break;
        default:
        }
    }

    @Override
    public void regainOmniPanelSelector() {
        switch (currentOmniPanelTab) {
        case PATIENTS_TAB:
            patientListPanel.regainSelector();
            break;
        case DOCTORS_TAB:
            staffListPanel.regainSelector();
            break;
        default:
        }
    }
}
