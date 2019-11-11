package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import seedu.address.person.commons.core.GuiSettings;
import seedu.address.person.commons.core.LogsCenter;
import seedu.address.ui.logic.Logic;
import seedu.address.ui.logic.LogicManager;
import seedu.address.util.CommandResult;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private seedu.address.transaction.logic.Logic transactionLogic;
    private seedu.address.reimbursement.logic.Logic reimbursementLogic;
    private seedu.address.inventory.logic.Logic inventoryLogic;
    private seedu.address.person.logic.Logic personLogic;
    private seedu.address.cashier.logic.Logic cashierLogic;
    private seedu.address.overview.logic.Logic overviewLogic;

    // Independent Ui parts residing in this Ui container
    private Home home;
    private Inventory inventory;
    private Reimbursements reimbursements;
    private Cashier cashier;
    private Overview overview;
    private Lion lion;

    private Logic uiLogic;

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
    private AnchorPane membersPlaceholder;

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

    @FXML
    private Tab membersTab;

    @FXML
    private Tab reimbursementsTab;

    @FXML
    private Tab inventoryTab;

    @FXML
    private Tab cashierTab;

    @FXML
    private Tab overviewTab;

    public MainWindow(Stage primaryStage, seedu.address.transaction.logic.Logic transactionLogic,
                      seedu.address.reimbursement.logic.Logic reimbursementLogic,
                      seedu.address.inventory.logic.Logic inventoryLogic,
                      seedu.address.person.logic.Logic personLogic,
                      seedu.address.cashier.logic.Logic cashierLogic,
                      seedu.address.overview.logic.Logic overviewLogic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;

        //add all our logicManager
        this.transactionLogic = transactionLogic;
        this.reimbursementLogic = reimbursementLogic;
        this.inventoryLogic = inventoryLogic;
        this.personLogic = personLogic;
        this.cashierLogic = cashierLogic;
        this.overviewLogic = overviewLogic;
        this.uiLogic = new LogicManager(tabPane);

        // Configure the UI
        //setWindowDefaultSize(logic.getGuiSettings());

        //setAccelerators();

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() throws Exception {
        lion = Lion.getInstance();
        lionPlaceholder.getChildren().add(lion.getRoot());

        home = new Home(transactionLogic);
        homePlaceholder.getChildren().add(home.getRoot());

        PersonListPanel personListPanel = new PersonListPanel(personLogic.getFilteredPersonList());
        membersPlaceholder.getChildren().add(personListPanel.getRoot());

        reimbursements = new Reimbursements(reimbursementLogic);
        reimbursementsPlaceholder.getChildren().add(reimbursements.getRoot());

        inventory = new Inventory(inventoryLogic);
        inventoryPlaceholder.getChildren().add(inventory.getRoot());

        cashier = new Cashier(cashierLogic);
        cashierPlaceholder.getChildren().add(cashier.getRoot());

        overview = new Overview(overviewLogic);
        overviewPlaceholder.getChildren().add(overview.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        checkIfNotify();

    }

    /**
     *
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
        personLogic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     */
    private CommandResult executeCommand(String commandText) throws Exception {
        try {
            CommandResult commandResult;
            logger.info("---------[User input] " + commandText + "--------");
            if (isUiCommand(commandText)) {
                commandResult = uiLogic.execute(commandText);
            } else if (tabPane.getSelectionModel().getSelectedItem().getText().equals("Home")) {
                commandResult = transactionLogic.execute(commandText);
                reimbursementLogic.updateReimbursementModelAndStorage(transactionLogic.getTransactionList());
            } else if (tabPane.getSelectionModel().getSelectedItem().getText().equals("Members")) {
                commandResult = personLogic.execute(commandText);
            } else if (tabPane.getSelectionModel().getSelectedItem().getText().equals("Reimbursements")) {
                commandResult = reimbursementLogic.execute(commandText);
                transactionLogic.updateTransactionStorage();
            } else if (tabPane.getSelectionModel().getSelectedItem().getText().equals("Inventory")) {
                commandResult = inventoryLogic.execute(commandText);
            } else if (tabPane.getSelectionModel().getSelectedItem().getText().equals("Cashier")) {
                commandResult = cashierLogic.execute(commandText);
            } else {
                commandResult = overviewLogic.execute(commandText);
            }

            logger.info("Result: " + commandResult.getFeedbackToUser());
            lion.setResponse(commandResult.getFeedbackToUser());

            checkIfNotify();

            homePlaceholder.getChildren().removeAll();
            homePlaceholder.getChildren().add(new Home(transactionLogic).getRoot());

            reimbursementsPlaceholder.getChildren().removeAll();
            reimbursementsPlaceholder.getChildren().add(new Reimbursements(reimbursementLogic).getRoot());

            inventoryPlaceholder.getChildren().removeAll();
            inventoryPlaceholder.getChildren().add(new Inventory(inventoryLogic).getRoot());

            cashierPlaceholder.getChildren().removeAll();
            cashierPlaceholder.getChildren().add(new Cashier(cashierLogic).getRoot());

            overviewPlaceholder.getChildren().removeAll();
            overviewPlaceholder.getChildren().add(new Overview(overviewLogic).getRoot());

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (Exception e) {
            logger.info("Invalid command: " + commandText);
            lion.setResponse(e.toString());
            throw e;
        }
    }

    /**
     * Checks if command is a UI-related command
     * @param userInput User input from command box.
     * @return true if it is a UI-related command.
     */
    private boolean isUiCommand(String userInput) {
        userInput.trim();
        String[] userInputArr = userInput.split(" ");
        if (userInputArr.length > 0) {
            return userInput.split(" ")[0].equals("go")
                    || userInput.split(" ")[0].equals("exit");
        }
        return false;
    }

    /**
     * Checks if notifications need to be printed and prints them if applicable.
     */
    private void checkIfNotify() {
        List<CommandResult> notifications = overviewLogic.checkNotifications();

        for (CommandResult notif: notifications) {
            lion.setResponse(notif.getFeedbackToUser());
        }
    }
}
