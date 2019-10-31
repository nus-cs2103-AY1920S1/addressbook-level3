package organice.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import organice.commons.core.GuiSettings;
import organice.commons.core.LogsCenter;
import organice.logic.Logic;
import organice.logic.commands.CommandResult;
import organice.logic.commands.exceptions.CommandException;
import organice.logic.parser.exceptions.ParseException;
import organice.model.Model;
import organice.model.person.Priority;
import organice.model.person.Status;
import organice.model.person.Type;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final String STYLE = "-fx-background-radius: 4; -fx-border-radius: 4; -fx-font-family: Segoe UI;"
            + "-fx-font-size: 13px; -fx-text-fill: white;";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;

    private Model model;
    private Logic logic;

    private CommandBox commandBox;
    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private Form form;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.model = model;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        form = null;

        commandBox = new CommandBox(this::executeCommand);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public StackPane getCommandBoxPlaceholder() {
        return commandBoxPlaceholder;
    }

    public StackPane getPersonListPanelPlaceholder() {
        return personListPanelPlaceholder;
    }

    public CommandBox getCommandBox() {
        return commandBox;
    }

    public Form getForm() {
        return form;
    }

    public ResultDisplay getResultDisplay() {
        return resultDisplay;
    }

    public Logic getLogic() {
        return logic;
    }

    public Model getModel() {
        return model;
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

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Reset the UI to the initial state of the window
     */
    void resetInnerParts() {
        personListPanelPlaceholder.getChildren().clear();
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        commandBoxPlaceholder.getChildren().clear();
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
     * Sets the colour of the 'Status' tag according to the status.
     */
    public static String getColourOfStatus(Status status) {
        if (status.isNotProcessing()) {
            return STYLE + "-fx-background-color: #213896;";
        } else {
            return STYLE + "-fx-background-color: #5476ff;";
        }
    }

    /**
     * Sets the colour of the 'Priority' tag according to the status.
     */
    public static String getColourOfPriority(Priority priority) {

        if (priority.isHighPriority()) {
            return STYLE + "-fx-background-color: #cc3232;";
        } else if (priority.isMediumPriority()) {
            return STYLE + "-fx-background-color: #db7b2b;";
        } else {
            return STYLE + "-fx-background-color: #5cb54c";
        }
    }
    /**
     * Swaps the PersonListPanel if a match command is executed.
     */
    public void handleMatch() {
        personListPanel = new PersonListPanel(logic.getMatchList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    /**
     * Changes PersonListPanel to display normal persons.
     */
    public void handleOtherCommands() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    /**
     * Swaps the PersonListPanel if a sort command is executed.
     */
    public void handleSort() {
        personListPanel = new PersonListPanel(logic.getSortList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
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

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see organice.logic.Logic#execute(String)
     */
    public CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isForm()) {
                FormAnimation.fadingAnimation(this);
                Type formType = commandResult.getFormType();
                FormUiManager formUiManager = new FormUiManager(this, formType, model, logger);
                personListPanelPlaceholder.getChildren().clear();
                if (formType.isDoctor()) {
                    form = new DoctorForm();
                    personListPanelPlaceholder.getChildren().add(((DoctorForm) form).getRoot());
                } else if (formType.isDonor()) {
                    form = new DonorForm();
                    personListPanelPlaceholder.getChildren().add(((DonorForm) form).getRoot());
                } else if (formType.isPatient()) {
                    form = new PatientForm(this);
                    personListPanelPlaceholder.getChildren().add(((PatientForm) form).getRoot());
                }

                formUiManager.getPersonDetails();
                return commandResult;
            }

            if (commandResult.isMatch()) {
                handleMatch();
            } else if (commandResult.isSort()) {
                handleSort();
            } else {
                handleOtherCommands();
            }

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
