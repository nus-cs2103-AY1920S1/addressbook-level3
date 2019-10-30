package seedu.address.diaryfeature.ui;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.diaryfeature.logic.DiaryBookLogic;
import seedu.address.diaryfeature.logic.parser.DiaryBookParser;
import seedu.address.diaryfeature.model.DiaryBook;
import seedu.address.diaryfeature.model.DiaryModel;
import seedu.address.diaryfeature.model.util.SampleDataUtil;
import seedu.address.diaryfeature.storage.JsonDiaryBookStorage;
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
    private DiaryBookLogic logicHandler;



    @FXML
    private Scene diaryScene;

    @FXML
    private VBox diaryPane;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane diaryListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;



    public DiaryPage() {
        super(FXML);
        this.parser = new DiaryBookParser();
        diaryScene = new Scene(diaryPane);
        JsonDiaryBookStorage storage = new JsonDiaryBookStorage(Paths.get("data","diaryBook.json"));


            Optional<DiaryBook> diaryBookOptional;
            DiaryBook initialData;
            try {
                diaryBookOptional = storage.readDiaryBook();
                if (!diaryBookOptional.isPresent()) {
                    logger.info("Data file not found. Will be starting with a sample DiaryBook");
                    initialData = SampleDataUtil.getSampleDiaryBook();
                } else {
                    initialData = diaryBookOptional.get();
                }
            }catch (DataConversionException e) {
                logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
                initialData = new DiaryBook();
            }

        this.logicHandler = new DiaryBookLogic(new DiaryModel(initialData),storage);


        fillInnerParts();
    }

    /**
     * Fills up all the placeholders of this window.
     */
    private void fillInnerParts() {

        diaryListPanel = new DiaryListPanel(logicHandler.getFilteredDiaryEntryList());
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
            CommandResult commandResult = logicHandler.execute(commandText);
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
     * Quit after letting user read the ByeResponse.
     *
     */

    public void exit() {

    }


    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        TimerTask myDelay = new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
                diaryScene.getWindow().hide();

            }
        };
        Timer timer = new Timer();
        timer.schedule(myDelay,350);
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
