package unrealunity.visit.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import unrealunity.visit.commons.core.GuiSettings;
import unrealunity.visit.commons.core.LogsCenter;
import unrealunity.visit.logic.Logic;
import unrealunity.visit.logic.commands.CommandResult;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.logic.parser.exceptions.ParseException;
import unrealunity.visit.model.person.VisitReport;

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
    private PersonListPanel personListPanel;
    private AppointmentListPanel appointmentListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private MotdWindow motdWindow;
    private VisitRecordWindow visitWindow;
    private VisitListPanel visitListPanel;
    private EmptyVisitList emptyVisitList;
    private AliasListWindow aliasListWindow;
    private ProfileWindow profilePanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem messageMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane reminderListPanelPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;


        // Load font
        Font.loadFont(getClass().getResourceAsStream("/font/Gill-Sans-MT.ttf"), 10);

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        visitWindow = new VisitRecordWindow(windowEvent -> {
            resultDisplay.setFeedbackToUser(visitWindow.getMessage());
            visitWindow.clearFields();
        });
        visitListPanel = new VisitListPanel();
        emptyVisitList = new EmptyVisitList();
        aliasListWindow = new AliasListWindow();
        profilePanel = new ProfileWindow();
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
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        appointmentListPanel = new AppointmentListPanel(logic.getFilteredAppointmentList());
        reminderListPanelPlaceholder.getChildren().add(appointmentListPanel.getRoot());
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

    /**
     * Opens the motd window or focuses on it if it's already opened.
     */
    @FXML
    public void handleMotd() {
        motdWindow = new MotdWindow(logic);
        if (!motdWindow.isShowing()) {
            motdWindow.show();
        } else {
            motdWindow.focus();
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
        visitWindow.hide();
        visitListPanel.hide();
        emptyVisitList.hide();
        profilePanel.hide();
        primaryStage.hide();
    }


    /**
     * Opens the profile panel or focuses on it if it's already opened.
     */
    @FXML
    public void handleProfilePanel() {
        if (!profilePanel.isShowing()) {
            profilePanel.show();
        } else {
            profilePanel.focus();
        }
    }

    /**
     * Opens the visit form or focuses on it if it's already opened.
     */
    @FXML
    public void handleShowVisitForm() {
        if (visitListPanel.isShowing()) {
            visitListPanel.hide();
        }
        if (!visitWindow.isShowing()) {
            visitWindow.show();
        } else {
            visitWindow.focus();
        }
    }

    /**
     * Opens the visit form or focuses on it if it's already opened.
     */
    @FXML
    public void handleShowVisitList() {
        if (!visitListPanel.isShowing()) {
            visitListPanel.show();
            primaryStage.requestFocus();
        } else {
            visitListPanel.focus();
        }
    }

    /**
     * Opens the empty visit list prompt window or focuses on it if it's already opened.
     */
    @FXML
    public void handleEmptyVisitList() {
        if (!emptyVisitList.isShowing()) {
            emptyVisitList.show();
        } else {
            emptyVisitList.focus();
        }
    }

    /**
     * Opens the list of existing user-defined aliases or focuses on it if it's already opened.
     */
    @FXML
    public void handleAliasListWindow() {
        if (!aliasListWindow.isShowing()) {
            aliasListWindow.show();
        } else {
            aliasListWindow.focus();
        }
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
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

            if (commandResult.isShowMotd()) {
                handleMotd();
            }

            if (commandResult.isAddVisit()) {
                visitWindow.setReportInfo(commandResult.getIdx(), commandResult.getDate(), logic);
                handleShowVisitForm();
            }

            if (commandResult.isEditVisit()) {
                visitWindow.setOldReportInfo(commandResult.getIdx(), commandResult.getReportIdx(),
                        commandResult.getOldReport(), logic);
                if (visitListPanel.isShowing()) {
                    visitListPanel.hide();
                }
                handleShowVisitForm();
            }

            if (commandResult.isShowVisitList()) {
                ObservableList<VisitReport> visits = commandResult.getObservableVisitList();
                if (visits.isEmpty()) {
                    if (visitListPanel.isShowing()) {
                        visitListPanel.hide();
                    }
                    handleEmptyVisitList();
                } else {
                    visitListPanel.setup(visits);
                    handleShowVisitList();
                }
            }

            if (commandResult.isShowProfile()) {
                profilePanel.setup(commandResult.getProfilePerson(), logic);
                handleProfilePanel();
            }

            if (commandResult.isShowAliasList()) {
                aliasListWindow.setup(commandResult.getFeedbackToUser());
                handleAliasListWindow();
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
