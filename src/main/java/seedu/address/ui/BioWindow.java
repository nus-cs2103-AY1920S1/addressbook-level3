package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class BioWindow extends UiPart<Stage> {

    private static final String FXML = "BioWindow.fxml";
    private static String displayImage = "/images/user.png";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private Profile profile;
    private BioTable bioTable;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private HBox profilePlaceholder;

    @FXML
    private VBox bioTablePlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public BioWindow(Stage primaryStage, Logic logic) {
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

    public ResultDisplay getResultDisplay() {
        return resultDisplay;
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

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        Image img = new Image(MainApp.class.getResourceAsStream(displayImage));

        // SAMPLE DATA
        BioTableFieldDataPair name = new BioTableFieldDataPair("Name:", "Amy");
        BioTableFieldDataPair nric = new BioTableFieldDataPair("NRIC:", "S1234567Z");
        BioTableFieldDataPair gender = new BioTableFieldDataPair("Gender:", "Female");
        BioTableFieldDataPair dob = new BioTableFieldDataPair("DOB:", "21/03/1940");
        BioTableFieldDataPair hp = new BioTableFieldDataPair("HP:", "98765432");
        BioTableFieldDataPair emergencyHp = new BioTableFieldDataPair("Emergency HP:", "91234567");
        BioTableFieldDataPair medicalCondition = new BioTableFieldDataPair("Medical Condition:",
                "Type II Diabetes, High Blood Pressure");
        BioTableFieldDataPair address = new BioTableFieldDataPair("Address:",
                "Blk 123 Example Road\n#12-34\nS(612345)");
        BioTableFieldDataPair dpPath = new BioTableFieldDataPair("DP Path:",
                "/Users/Amy/dp.png");
        BioTableFieldDataPair bgColour = new BioTableFieldDataPair("Background Colour:", "navy-blue");
        BioTableFieldDataPair fontColour = new BioTableFieldDataPair("Font Colour:", "yellow");
        BioTableFieldDataPair myGoals = new BioTableFieldDataPair("My Goals:",
                "lose 4kg from 29/09/2019 to 30/09/2019");

        ObservableList<BioTableFieldDataPair> list = FXCollections.observableArrayList();
        list.addAll(name, nric, gender, dob, hp, emergencyHp, medicalCondition, address, dpPath, bgColour,
                fontColour, myGoals);

        profile = new Profile(img, "Amy", "\"If at first you don't succeed, call it version 1.0."
                + "\"\n-Anonymous");
        profilePlaceholder.getChildren().add(profile.getRoot());

        bioTable = new BioTable();
        bioTable.getTableView().setItems(list);
        bioTablePlaceholder.getChildren().add(bioTable.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
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

    /**
     * Switches this window to the MainWindow.
     */
    @FXML
    public void switchToMainWindow(String feedbackToUser) {
        hide();
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        MainWindow mainWindow = new MainWindow(primaryStage, logic);
        mainWindow.show();
        mainWindow.fillInnerParts();
        mainWindow.getResultDisplay().setFeedbackToUser(feedbackToUser);

    }

    void show() {
        primaryStage.show();
    }

    public void hide() {
        getRoot().hide();
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
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            if (!commandResult.isShowBio()) {
                switchToMainWindow(commandResult.getFeedbackToUser());
            }
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
