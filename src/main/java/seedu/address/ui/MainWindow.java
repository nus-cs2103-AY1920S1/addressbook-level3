package seedu.address.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.card.Card;
import seedu.address.model.file.ViewableFile;
import seedu.address.model.note.Note;
import seedu.address.model.password.Password;
import seedu.address.model.password.analyser.report.AnalysisReport;

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
    private FileListPanel fileListPanel;
    private CardListPanel cardListPanel;
    private NoteListPanel noteListPanel;
    private PasswordListPanel passwordListPanel;
    private HelpWindow helpWindow;
    private ReadDisplayPassword readDisplayPassword;
    private OpenDisplayNote openDisplayNote;
    private ReadDisplayCard readDisplayCard;
    private ResultDisplay resultDisplay;
    private ExpiryDisplay expiryDisplay;
    private ReadDisplayPasswordReport readDisplayPasswordReport;
    private FilePreviewPanel filePreviewPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane objectListPanelPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane cardListPanelPlaceholder;

    @FXML
    private StackPane noteListPanelPlaceholder;

    @FXML
    private StackPane passwordListPanelPlaceholder;

    @FXML
    private StackPane readListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane modeListPanelPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private VBox modeList;

    @FXML
    private VBox objectList;

    @FXML
    private VBox readList;

    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private Button b4;


    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        //readListPanelPlaceholder.setVisible(false);
        readList.setVisible(false);
        readList.setManaged(false);
        expiryDisplay = new ExpiryDisplay(logic.getExpiringCardList());
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
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
        fillInnerPartsWithMode();
    }

    /**
     * Toggles button according to logic.
     */
    void toggleButton() {
        b1.setDisable(false);
        b2.setDisable(false);
        b3.setDisable(false);
        b4.setDisable(false);
        switch (logic.getMode()) {
        case PASSWORD:
            b1.setDisable(true);
            break;
        case FILE:
            b2.setDisable(true);
            break;
        case CARD:
            b3.setDisable(true);
            break;
        case NOTE:
            b4.setDisable(true);
            break;
        default:
            b1.setDisable(false);
            b2.setDisable(false);
            b3.setDisable(false);
            b4.setDisable(false);
        }
    }

    /**
     * Fills up all the read display of this window.
     * @param object Object to be read
     * @param index Index of the object in the filtered list
     */
    @SuppressWarnings("unchecked")
    void fillReadParts(Object object, Index index) {
        if (object instanceof Password) {
            readList.setMaxWidth(420);
            readDisplayPassword = new ReadDisplayPassword();
            readDisplayPassword.setLogic(logic);
            readListPanelPlaceholder.getChildren().add(readDisplayPassword.getRoot());
            readDisplayPassword.setFeedbackToUser((Password) object, index);
        } else if (object instanceof Note) {
            readList.setMaxWidth(420);
            openDisplayNote = new OpenDisplayNote();
            openDisplayNote.setLogic(logic);
            //TODO bad coupling? how else to implement though?
            openDisplayNote.setMainWindow(this);
            readListPanelPlaceholder.getChildren().add(openDisplayNote.getRoot());
            openDisplayNote.setFeedbackToUser((Note) object, index);
        } else if (object instanceof AnalysisReport) {
            readList.setMaxWidth(Double.MAX_VALUE);
            //readList.setMinWidth(600);
            //readList.setMaxWidth(10000);
            //readList.getParent().
            readDisplayPasswordReport = new ReadDisplayPasswordReport();
            readListPanelPlaceholder.getChildren().add(readDisplayPasswordReport.getRoot());
            //readListPanelPlaceholder.prefWidthProperty().bind(primaryStage.widthProperty().multiply(0.80));
            readDisplayPasswordReport.setFeedbackToUser(object);
        } else if (object instanceof Card) {
            readList.setMaxWidth(420);
            readDisplayCard = new ReadDisplayCard();
            readDisplayCard.setLogic(logic);
            readListPanelPlaceholder.getChildren().add(readDisplayCard.getRoot());
            readDisplayCard.setFeedbackToUser((Card) object);
        } else if (object instanceof ViewableFile) {
            readList.setMaxWidth(420);
            filePreviewPanel = new FilePreviewPanel();
            readListPanelPlaceholder.getChildren().add(filePreviewPanel.getRoot());
            filePreviewPanel.setFeedbackToUser((ViewableFile) object);
        }
    }

    /**
     * Fills up all the placeholders of this window using the current mode.
     */
    void fillInnerPartsWithMode() {
        toggleButton();
        switch (logic.getMode()) {
        case FILE:
            fileListPanel = new FileListPanel(logic.getFilteredFileList());
            objectListPanelPlaceholder.getChildren().add(fileListPanel.getRoot());
            statusbarPlaceholder.getChildren().add(
                    new StatusBarFooter(logic.getFileBookFilePath()).getRoot());
            break;
        case CARD:
            cardListPanel = new CardListPanel(logic.getFilteredCardList());
            objectListPanelPlaceholder.getChildren().add(cardListPanel.getRoot());
            statusbarPlaceholder.getChildren().add(
                    new StatusBarFooter(logic.getCardBookFilePath()).getRoot());
            break;
        case NOTE:
            noteListPanel = new NoteListPanel(logic.getFilteredNoteList());
            objectListPanelPlaceholder.getChildren().add(noteListPanel.getRoot());
            statusbarPlaceholder.getChildren().add(
                    new StatusBarFooter(logic.getNoteBookFilePath()).getRoot());
            break;
        case PASSWORD:
            passwordListPanel = new PasswordListPanel(logic.getFilteredPasswordList());
            objectListPanelPlaceholder.getChildren().add(passwordListPanel.getRoot());
            statusbarPlaceholder.getChildren().add(
                    new StatusBarFooter(logic.getPasswordBookFilePath()).getRoot());
            break;
        default:
            personListPanel = new PersonListPanel(logic.getFilteredPersonList());
            objectListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
            statusbarPlaceholder.getChildren().add(
                    new StatusBarFooter(logic.getAddressBookFilePath()).getRoot());
            break;
        }
    }

    /**
     * Handle UI changes on mode change.
     */
    void handleModeChange() {
        objectListPanelPlaceholder.getChildren().clear();
        fillInnerPartsWithMode();
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
     * Opens the expiry display window or focuses on it if it's already opened.
     */
    @FXML
    public void handleExpiry() {
        if (expiryDisplay.hasCards()) {
            if (!expiryDisplay.isShowing()) {
                expiryDisplay.show();
            } else {
                expiryDisplay.focus();
            }
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

    public NoteListPanel getNoteListPanel() {
        return noteListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    CommandResult executeCommand(String commandText) throws CommandException,
            ParseException {
        readList.setVisible(false);
        readList.setManaged(false);
        //readList.setMinWidth(0);
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
            if (commandResult.isGoTo()) {
                logic.setMode(commandResult.getModeToGoTo());
                handleModeChange();
            }
            if (commandResult.isRead()) {
                readList.setVisible(true);
                readList.setManaged(true);
                readListPanelPlaceholder.getChildren().clear();
                //readList.setMinWidth(420);
                fillReadParts(commandResult.getObject(), commandResult.getIndex());
            }
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}

