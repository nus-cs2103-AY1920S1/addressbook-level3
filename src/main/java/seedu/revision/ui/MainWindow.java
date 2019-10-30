package seedu.revision.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import seedu.revision.commons.core.GuiSettings;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.logic.MainLogic;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.AddressBook;
import seedu.revision.model.Model;
import seedu.revision.model.ReadOnlyAddressBook;
import seedu.revision.model.quiz.Mode;
import seedu.revision.model.util.SampleDataUtil;
import seedu.revision.ui.answerables.AnswerableListPanel;


/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends Window {

    private final Logger logger = LogsCenter.getLogger(getClass());

    public MainWindow(Stage primaryStage, MainLogic mainLogic) {
        super(primaryStage, mainLogic);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        answerableListPanel = new AnswerableListPanel(mainLogic.getFilteredAnswerableList());
        answerableListPanelPlaceholder.getChildren().add(answerableListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(mainLogic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Initialises the session for quiz. Loads the window components for quiz.
     * @throws CommandException
     */
    @FXML
    public void handleStart(Mode mode) throws CommandException {
        if (this.mainLogic.getFilteredAnswerableList().size() > 0) {
            StartQuizWindow startQuizWindow = new StartQuizWindow(getPrimaryStage(), getMainLogic(), mode);
            startQuizWindow.show();
            startQuizWindow.fillInnerParts();
        } else {
            throw new CommandException("No questions were found matching that category/difficulty"
                    + "Quiz start aborted. Type 'list' to view your full list of questions again.");
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    protected void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        mainLogic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Opens the restore window.
     */
    @FXML
    public void handleRestore(Model passedModel) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setContentText("Are you sure? This cannot be undone.");

        ButtonType confirmRestore = new ButtonType(
                "Yes",
                ButtonBar.ButtonData.OK_DONE
        );

        ButtonType declineRestore = new ButtonType(
                "No",
                ButtonBar.ButtonData.CANCEL_CLOSE
        );
        alert.getButtonTypes().setAll(confirmRestore, declineRestore);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == confirmRestore) {
            ReadOnlyAddressBook sampleData;
            sampleData = SampleDataUtil.getSampleAddressBook();
            passedModel.setAddressBook(new AddressBook(sampleData));
        }
    }

    public AnswerableListPanel getAnswerableListPanel() {
        return answerableListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see MainLogic#execute(String)
     */
    @Override
    protected CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = mainLogic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }
            if (commandResult.isStart()) {
                handleStart(commandResult.getMode());
            }

            if (commandResult.isShowRestore()) {
                handleRestore(commandResult.getModel());
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
