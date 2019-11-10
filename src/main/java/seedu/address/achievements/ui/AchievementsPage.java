package seedu.address.achievements.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.achievements.logic.AchievementsLogic;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.CodeWindow;
import seedu.address.ui.CommandBox;
import seedu.address.ui.HelpWindow;
import seedu.address.ui.Page;
import seedu.address.ui.PageManager;
import seedu.address.ui.PageType;
import seedu.address.ui.ResultDisplay;
import seedu.address.ui.UiPart;

/**
 * Page for showing the user achievements
 */
public class AchievementsPage extends UiPart<Region> implements Page {

    private static final PageType pagetype = PageType.ACHIEVEMENTS;

    private static final String FXML = "achievements/Achievements.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private BorderPane achievementsPane;

    private AchievementsLogic achievementsLogic;

    private ResultDisplay resultDisplay;

    private HelpWindow helpWindow;

    private CodeWindow codeWindow;

    @FXML
    private VBox achievementsPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private Label test;

    public AchievementsPage(AchievementsLogic achievementsLogic) {
        super(FXML, new BorderPane());
        this.achievementsLogic = achievementsLogic;
        this.helpWindow = new HelpWindow();
        fillInnerParts();
        setAccelerators();
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
         * https://bugs.openjdk.java.net/browse/JDK-8131666 is fixed in later version of
         * SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will not
         * work when the focus is in them because the key event is consumed by the
         * TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is in
         * CommandBox or ResultDisplay.
         */
        achievementsPlaceholder.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
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
        this.helpWindow = new HelpWindow();
        this.codeWindow = new CodeWindow();

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Refresh UI with new data from Statistics.
     */
    void refreshUi() {
        achievementsPlaceholder.getChildren().clear();
        achievementsPlaceholder.getChildren().add(
                new AchievementsCard(achievementsLogic.getStatisticsView()).getRoot());
    }

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = achievementsLogic.execute(commandText);
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
     * Opens the code window or focuses on it if it's already opened.
     */
    @FXML
    public void handleCode() {
        if (!codeWindow.isShowing()) {
            codeWindow.show();
        } else {
            codeWindow.focus();
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

    @Override
    public void closeResources() {
        helpWindow.hide();
        codeWindow.hide();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        closeResources();
        PageManager.closeWindows();
    }

    @Override
    public PageType getPageType() {
        return pagetype;
    }

    @Override
    public Parent getParent() {
        refreshUi();
        return super.getRoot();
    }
}
