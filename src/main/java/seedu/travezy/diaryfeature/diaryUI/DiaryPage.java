package seedu.travezy.diaryfeature.diaryUI;


import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.travezy.commons.core.LogsCenter;
import seedu.travezy.diaryfeature.diaryCommands.DiaryCommand;
import seedu.travezy.diaryfeature.diaryModel.DiaryList;
import seedu.travezy.diaryfeature.diaryParser.DiaryParser;
import seedu.travezy.address.logic.AddressBookLogic;
import seedu.travezy.logic.commands.CommandResult;
import seedu.travezy.ui.CommandBox;
import seedu.travezy.ui.Page;
import seedu.travezy.ui.PageType;
import seedu.travezy.ui.ResultDisplay;
import seedu.travezy.ui.UiPart;

public class DiaryPage extends UiPart<VBox> implements Page {

    private final static PageType pageType = PageType.DIARY;
    private static final String FXML = "DiaryPage.fxml";
    private DiaryList holder;



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
    private StackPane resultDisplayPlaceholder;


    public DiaryPage() {
        super(FXML, new VBox());
        diaryScene = new Scene(diaryPane);
        holder = new DiaryList();
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
     * @see AddressBookLogic#execute(String)
     */
    private CommandResult executeCommand(String input)  {
            DiaryParser myParser = new DiaryParser();
            DiaryCommand result = myParser.parse(input);
            result.setReference(holder);
            CommandResult commandResult = result.executeCommand();
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
