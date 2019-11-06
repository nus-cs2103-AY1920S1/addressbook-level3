package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Module;
import seedu.address.model.semester.Semester;
import seedu.address.model.studyplan.StudyPlan;
import seedu.address.model.tag.Tag;
import seedu.address.ui.exceptions.InvalidResultViewTypeException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private static final String NO_ACTIVE_STUDY_PLAN = "You have no remaining study plans.";
    private static final String STUDY_PLAN_ID = "[ID:%d]";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private SemesterListPanel semesterListPanel;
    private ResultDisplay resultDisplay;
    private CommandBox commandBox;
    private StudyPlanTagPanel studyPlanTagPanel;

    @FXML
    private Label studyPlanId;

    @FXML
    private Label title;

    @FXML
    private Label mcCount;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane semesterListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane studyPlanTagsPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        logger.info("Filling Main Window.");
        StudyPlan sp = logic.getActiveStudyPlan();
        if (sp == null) {
            logger.info("No active study plan present.");
            NoActiveStudyPlanDisplay noActiveStudyPlanDisplay = new NoActiveStudyPlanDisplay();
            semesterListPanelPlaceholder.getChildren().add(noActiveStudyPlanDisplay.getRoot());
            studyPlanId.setText("");
            title.setText(NO_ACTIVE_STUDY_PLAN);
            mcCount.setText("");
        } else {
            logger.info("An active study plan is present.");
            ObservableList<Semester> semesters = sp.getSemesters().asUnmodifiableObservableList();
            semesterListPanel = new SemesterListPanel(semesters, sp.getCurrentSemester());
            semesterListPanelPlaceholder.getChildren().add(semesterListPanel.getRoot());
            studyPlanId.setText(String.format(STUDY_PLAN_ID, sp.getIndex()));
            title.setText(sp.getTitle().toString());
            mcCount.setText(sp.getMcCountString());
            studyPlanTagPanel = new StudyPlanTagPanel(sp.getStudyPlanTags()
                    .asUnmodifiableObservableList());
            studyPlanTagsPlaceholder.getChildren().add(studyPlanTagPanel.getRoot());
        }

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        commandBox = new CommandBox(this::executeCommand, logic.getModulePlanner());
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

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        logger.info("Main Window closing.");
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        resultDisplay.removeResultView();
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            semesterListPanel.refresh();

            StudyPlan sp = logic.getActiveStudyPlan();
            mcCount.setText(sp == null ? "" : sp.getMcCountString());

            if (commandResult.isChangesActiveStudyPlan()) {
                logger.info("Study plan has been changed. Refreshing display.");
                if (sp == null) {
                    NoActiveStudyPlanDisplay noActiveStudyPlanDisplay = new NoActiveStudyPlanDisplay();
                    semesterListPanelPlaceholder.getChildren().remove(0);
                    semesterListPanelPlaceholder.getChildren().add(noActiveStudyPlanDisplay.getRoot());
                    studyPlanId.setText("");
                    title.setText(NO_ACTIVE_STUDY_PLAN);
                } else {
                    ObservableList<Semester> semesters = sp.getSemesters().asUnmodifiableObservableList();
                    semesterListPanel = new SemesterListPanel(semesters, sp.getCurrentSemester());
                    semesterListPanelPlaceholder.getChildren().remove(0);
                    semesterListPanelPlaceholder.getChildren().add(semesterListPanel.getRoot());
                    studyPlanTagPanel = new StudyPlanTagPanel(sp.getStudyPlanTags().asUnmodifiableObservableList());
                    studyPlanTagsPlaceholder.getChildren().remove(0);
                    studyPlanTagsPlaceholder.getChildren().add(studyPlanTagPanel.getRoot());
                    studyPlanId.setText(String.format(STUDY_PLAN_ID, sp.getIndex()));
                    title.setText(sp.getTitle().toString());
                    commandBox.handleChangeOfActiveStudyPlan();
                }
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            ResultViewType resultViewType = commandResult.getResultViewType();
            if (resultViewType != null) {
                handleResult(resultViewType, commandResult.getResultContent());
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Handles the result by assigning the appropriate nodes to the result display.
     */
    private <T> void handleResult(ResultViewType resultViewType, ObservableList<T> resultContent) {
        switch (resultViewType) {
        case TEXT:
            TextArea textArea = new TextArea();
            ObservableList<String> textContent = (ObservableList<String>) resultContent;
            for (String text : textContent) {
                textArea.setText(text);
            }
            resultDisplayPlaceholder.getChildren().add(textArea);
            break;
        case TAG:
            ObservableList<Tag> tagContent = (ObservableList<Tag>) resultContent;
            TagListPanel tagListPanel = new TagListPanel(tagContent);
            resultDisplay.setResultView(tagListPanel.getRoot());
            break;
        case MODULE:
            ObservableList<Module> moduleContent = (ObservableList<Module>) resultContent;
            ModuleListPanel moduleListPanel = new ModuleListPanel(moduleContent);
            resultDisplay.setResultView(moduleListPanel.getRoot());
            break;
        case SEMESTER:
            ObservableList<Semester> studyPlanContent = (ObservableList<Semester>) resultContent;
            SimpleSemesterListPanel simpleSemesterListPanel = new SimpleSemesterListPanel(studyPlanContent);
            resultDisplay.setResultView(simpleSemesterListPanel.getRoot());
            break;
        case COMMIT_HISTORY:
            //ObservableList<Commit> commitContent = (ObservableList<Commit>) resultContent;
            //CommitListPanel commitListPanel = new CommitListPanel(commitContent);
            //resultDisplay.setResultView(commitListPanel.getRoot());
            break;
        default:
            throw new InvalidResultViewTypeException(resultViewType.name());
        }
    }

}
