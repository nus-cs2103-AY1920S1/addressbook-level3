package budgetbuddy.ui;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import budgetbuddy.commons.core.Config;
import budgetbuddy.commons.core.GuiSettings;
import budgetbuddy.commons.core.LogsCenter;
import budgetbuddy.logic.Logic;
import budgetbuddy.logic.commands.CommandCategory;
import budgetbuddy.logic.commands.CommandContinuation;
import budgetbuddy.logic.commands.CommandResult;
import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.logic.script.ScriptBindingInterfaces;
import budgetbuddy.logic.script.ScriptEngine;
import budgetbuddy.logic.script.ScriptEnvironmentInitialiser;
import budgetbuddy.ui.tab.AccountTab;
import budgetbuddy.ui.tab.LoanTab;
import budgetbuddy.ui.tab.PanelTab;
import budgetbuddy.ui.tab.RuleTab;
import budgetbuddy.ui.tab.ScriptTab;
import budgetbuddy.ui.tab.TransactionTab;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private HashMap<CommandCategory, PanelTab> tabMap;

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane sideBarPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane outputDisplayPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        logic.addToScriptEnvironment(new ScriptUiBinding());
        logic.addToScriptEnvironment(new ScriptBinding());

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
        tabMap = new HashMap<>();

        if (Config.isDemo()) {
            new DemoWindow(this).show();
        }
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

        // instantiate all tabs
        AccountTab accountTab = new AccountTab(logic.getAccountList());
        tabMap.put(CommandCategory.ACCOUNT, accountTab);

        TransactionTab transactionTab = new TransactionTab(logic.getTransactionList());
        tabMap.put(CommandCategory.TRANSACTION, transactionTab);

        RuleTab ruleTab = new RuleTab(logic.getRuleList());
        tabMap.put(CommandCategory.RULE, ruleTab);

        LoanTab loanTab = new LoanTab(logic.getFilteredLoanList(), logic.getSortedDebtorList());
        tabMap.put(CommandCategory.LOAN, loanTab);

        ScriptTab scriptTab = new ScriptTab(logic.getScriptList());
        tabMap.put(CommandCategory.SCRIPT, scriptTab);

        OutputDisplay outputDisplay =
                new OutputDisplay(accountTab, transactionTab, ruleTab, loanTab, scriptTab);
        outputDisplayPlaceholder.getChildren().add(outputDisplay.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter();
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        // activate initial tab
        updateView(transactionTab, CommandCategory.TRANSACTION);

        // set focus event handler
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            // hotkey (Ctrl + Shift) to switch focus to command line text box
            if (event.isShiftDown() && event.isControlDown()) {
                Node commandField = commandBox.getRoot().getChildrenUnmodifiable().get(0);
                commandField.requestFocus();
            }
            // add hotkey (Ctrl + D) to switch between primary and secondary panels (if secondary panel exists)
            PanelTab currentTab = (PanelTab) outputDisplay.getRoot().getSelectionModel().getSelectedItem();
            if (currentTab.hasSecondaryPanel() && event.isControlDown() && event.getCode()
                    .equals(KeyCode.D)) {
                if (currentTab.isPrimaryPanelSelected()) {
                    currentTab.setSecondaryPanel();
                } else {
                    currentTab.setPrimaryPanel();
                }
            }
        });
    }

    /**
     * Updates the current tab on display
     */
    private void updateView(PanelTab tabToView, CommandCategory category) {
        if (tabToView == null) {
            return;
        }

        if (category.isSecondaryCategory()) {
            tabToView.setSecondaryPanel();
        } else {
            tabToView.setPrimaryPanel();
        }

        TabPane pane = (TabPane) outputDisplayPlaceholder.getChildren().get(0);
        pane.getSelectionModel().select(tabToView);
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
        GuiSettings guiSettings =
                new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(), (int) primaryStage.getX(),
                        (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    private File handleShowFilePicker() {
        FileChooser filePicker = new FileChooser();
        filePicker.setTitle("Open file for command");
        return filePicker.showOpenDialog(primaryStage);
    }

    /**
     * Executes the command and returns the result.
     *
     * @see Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            while (true) {
                logger.info("Result: " + commandResult.getFeedbackToUser());
                resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

                CommandCategory category = commandResult.getCommandCategory();
                PanelTab tabToView = category.isSecondaryCategory()
                                     ? tabMap.get(category.getPrimaryCategory(category))
                                     : tabMap.get(category);
                updateView(tabToView, category);

                CommandResult newResult = handleContinuations(commandResult);
                if (newResult != null) {
                    commandResult = newResult;
                    continue;
                }

                break;
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Handles command continuations.
     */
    private CommandResult handleContinuations(CommandResult commandResult) throws CommandException {
        for (CommandContinuation<?> cont : commandResult.getContinuations()) {
            switch (cont.getType()) {
            case EXIT:
                handleExit();
                break;
            case SHOW_HELP:
                handleHelp();
                break;
            case SHOW_FILE_PICKER:
                File result = handleShowFilePicker();
                return cont.asShowFilePicker().continueCommand(result == null ? null : result.toPath());
            default:
                assert false : "Unhandled command continuation type";
                logger.warning("Unhandled command continuation type");
                break;
            }
        }
        return null;
    }

    /**
     * Provides convenience functions that involve the MainWindow to the script environment.
     * <p>
     * This class breaks a lot of abstraction barriers in order for the scripting experience to be ergonomic.
     * The abstraction-breaking is contained within this class.
     */
    private class ScriptBinding implements ScriptEnvironmentInitialiser {
        @Override
        public void initialise(ScriptEngine engine) {
            engine.setVariable("executeCommand",
                    (ScriptBindingInterfaces.StringOnly) this::scriptExecuteCommand);
        }

        /**
         * Provides <code>executeCommand(command) -> CommandResult</code>.
         */
        private Object scriptExecuteCommand(String command) throws Exception {
            if (Platform.isFxApplicationThread()) {
                return MainWindow.this.executeCommand(command);
            } else {
                throw new IllegalStateException("Cannot execute command from non-application thread");
            }
        }
    }
}
