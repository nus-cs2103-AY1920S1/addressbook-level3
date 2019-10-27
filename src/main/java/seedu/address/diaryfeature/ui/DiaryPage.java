package seedu.address.diaryfeature.ui;


import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.diaryfeature.logic.parser.DiaryBookParser;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.CommandBox;
import seedu.address.ui.Page;
import seedu.address.ui.PageType;
import seedu.address.ui.ResultDisplay;


/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class DiaryPage extends UiPart<Region> implements Page {
    private static final PageType pageType = PageType.DIARY;


    private static final String FXML = "DiaryPage.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());


    // Independent Ui parts residing in this Ui container
    private DiaryListPanel diaryListPanel;
    private ResultDisplay resultDisplay;
    private DiaryBookParser parser;
    private DiaryModel model;


    @FXML
    private Scene diaryScene;

    @FXML
    private BorderPane diaryPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane diaryListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;



    public DiaryPage() {
        super(FXML);
        this.parser = new DiaryBookParser();
        this.model = new DiaryModel();
        diaryScene = new Scene(diaryPane);
        fillInnerParts();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    private void fillInnerParts() {

        diaryListPanel = new DiaryListPanel(model.getFilteredDiaryEntryList());
        diaryListPanelPlaceholder.getChildren().add(diaryListPanel.getRoot());



        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Executes the command and returns the result.
     *
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            Command command = parser.parseCommand(commandText);
            CommandResult commandResult = command.execute(model);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());


            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText + e);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
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