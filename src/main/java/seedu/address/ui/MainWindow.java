package seedu.address.ui;

import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.transaction.commands.CommandResult;
import seedu.address.transaction.logic.Logic;

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
    private Home home;
    private Inventory inventory;
    private Reimbursements reimbursements;
    private Cashier cashier;
    private Overview overview;
    private Lion lion;

    private HelpWindow helpWindow;


    @FXML
    private AnchorPane homePlaceholder;

    @FXML
    private AnchorPane inventoryPlaceholder;

    @FXML
    private AnchorPane reimbursementsPlaceholder;

    @FXML
    private AnchorPane cashierPlaceholder;

    @FXML
    private AnchorPane overviewPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private VBox lionPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab homeTab;

//    @FXML
//    private StackPane personListPanelPlaceholder;
//
//    @FXML
//    private StackPane resultDisplayPlaceholder;
//
//    @FXML
//    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;

        this.logic = logic;

        // Configure the UI
        //setWindowDefaultSize(logic.getGuiSettings());

        //setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /*private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }*/

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
    void fillInnerParts() throws  Exception{
        home = new Home(logic);
        homePlaceholder.getChildren().add(home.getRoot());

        inventory = new Inventory();
        inventoryPlaceholder.getChildren().add(inventory.getRoot());

        reimbursements = new Reimbursements();
        reimbursementsPlaceholder.getChildren().add(reimbursements.getRoot());

        cashier = new Cashier();
        cashierPlaceholder.getChildren().add(cashier.getRoot());

        overview = new Overview();
        overviewPlaceholder.getChildren().add(overview.getRoot());

        lion = new Lion();
        lionPlaceholder.getChildren().add(lion.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    /*private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }*/

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    /*@FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }*/

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    /*@FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }*/

//    public PersonListPanel getPersonListPanel() {
//        return personListPanel;
//    }

    /**
     * Executes the command and returns the result.
     *
     */
    private CommandResult executeCommand(String commandText) throws Exception{
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            lion.setResponse(commandResult.getFeedbackToUser());
            homePlaceholder.getChildren().removeAll();
            homePlaceholder.getChildren().add(new Home(logic).getRoot());

            inventoryPlaceholder.getChildren().removeAll();
            inventoryPlaceholder.getChildren().add(new Inventory().getRoot());

            reimbursementsPlaceholder.getChildren().removeAll();
            reimbursementsPlaceholder.getChildren().add(new Reimbursements().getRoot());

            cashierPlaceholder.getChildren().removeAll();
            cashierPlaceholder.getChildren().add(new Cashier().getRoot());

            overviewPlaceholder.getChildren().removeAll();
            overviewPlaceholder.getChildren().add(new Overview().getRoot());

            //later when we implement help and exit
            /*if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }*/

            return commandResult;
        } catch (Exception e) {
            logger.info("Invalid command: " + commandText);
            lion.setResponse(e.toString());
            throw e;
        }
    }
}