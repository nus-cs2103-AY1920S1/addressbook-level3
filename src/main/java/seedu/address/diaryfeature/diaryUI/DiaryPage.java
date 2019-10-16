package seedu.address.diaryfeature.diaryUI;


import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.diaryfeature.diaryCommands.DiaryCommand;
import seedu.address.diaryfeature.diaryParser.DiaryParser;
import seedu.address.logic.commands.CommandResult;
import seedu.address.ui.CommandBox;
import seedu.address.ui.Page;
import seedu.address.ui.PageType;
import seedu.address.ui.ResultDisplay;
import seedu.address.ui.UiPart;

public class DiaryPage extends UiPart<VBox> implements Page {

    private final static PageType pageType = PageType.DIARY;
    private static final String FXML = "DiaryPage.fxml";


    // Independent Ui parts residing in this Ui container
    private ResultDisplay resultDisplay;
    private final Logger logger = LogsCenter.getLogger(getClass());

   @FXML
   private Scene diaryScene;

   @FXML
   private VBox diaryPane;


    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    public DiaryPage() {
        super(FXML, new VBox());
        diaryScene = new Scene(diaryPane);
        fillInnerParts();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    private void fillInnerParts() {
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String input)  {
            DiaryParser myParser = new DiaryParser();
            DiaryCommand command = myParser.parse(input);
            CommandResult commandResult = command.executeCommand();
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                //handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isShowPage()) {
                handlePageChange(commandResult);
            }

            return commandResult;
    }

    /**
     * Changes application page.
     */
    @FXML
    private void handlePageChange(CommandResult commandResult) {
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        this.diaryScene.getWindow().hide();
    }


    @Override
    public Scene getScene() {
        return diaryScene;
    }

    @Override
    public PageType getPageType() {
        return pageType;
    }


}
