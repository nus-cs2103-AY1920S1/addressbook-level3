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

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private SemesterListPanel semesterListPanel;
    private ResultDisplay resultDisplay;

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
        StudyPlan sp = logic.getActiveStudyPlan();
        if (sp == null) {
            NoActiveStudyPlanDisplay noActiveStudyPlanDisplay = new NoActiveStudyPlanDisplay();
            semesterListPanelPlaceholder.getChildren().add(noActiveStudyPlanDisplay.getRoot());
            title.setText(NO_ACTIVE_STUDY_PLAN);
            mcCount.setText("");
        } else {
            ObservableList<Semester> semesters = sp.getSemesters().asUnmodifiableObservableList();
            semesterListPanel = new SemesterListPanel(semesters);
            semesterListPanelPlaceholder.getChildren().add(semesterListPanel.getRoot());
            title.setText(sp.getTitle().toString());
            mcCount.setText(sp.getMcCountString());
        }

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand, sp);
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
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        primaryStage.hide();
    }

    public SemesterListPanel getSemesterListPanel() {
        return semesterListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            resultDisplay.removeResultView();
            semesterListPanel.refresh();

            StudyPlan sp = logic.getActiveStudyPlan();
            mcCount.setText(sp == null ? "" : sp.getMcCountString());

            if (commandResult.isChangesActiveStudyPlan()) {
                if (sp == null) {
                    NoActiveStudyPlanDisplay noActiveStudyPlanDisplay = new NoActiveStudyPlanDisplay();
                    semesterListPanelPlaceholder.getChildren().remove(0);
                    semesterListPanelPlaceholder.getChildren().add(noActiveStudyPlanDisplay.getRoot());
                    title.setText(NO_ACTIVE_STUDY_PLAN);
                } else {
                    ObservableList<Semester> semesters = sp.getSemesters().asUnmodifiableObservableList();
                    semesterListPanel = new SemesterListPanel(semesters);
                    semesterListPanelPlaceholder.getChildren().remove(0);
                    semesterListPanelPlaceholder.getChildren().add(semesterListPanel.getRoot());
                    title.setText(sp.getTitle().toString());
                    commandBoxPlaceholder.getChildren().remove(0);
                    CommandBox commandBox = new CommandBox(this::executeCommand, sp);
                    commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
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
            for (String text: textContent) {
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
            //ObservableList<Module> moduleContent = (ObservableList<Module>) resultContent;
            //ModuleListPanel moduleListPanel = new ModuleListPanel(moduleContent);
            //resultDisplay.setResultView(moduleListPanel.getRoot());
            break;
        case STUDY_PLAN:
            //ObservableList<StudyPlan> studyPlanContent = (ObservableList<StudyPlan>) resultContent;
            //StudyPlanListPanel = studyPlanListPanel = new StudyPlanListPanel(studyPlanContent);
            //resultDisplay.setResultView(studyPlanListPanel.getRoot());
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
