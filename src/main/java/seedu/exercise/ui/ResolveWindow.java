package seedu.exercise.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.logic.Logic;
import seedu.exercise.logic.commands.CommandResult;

/**
 * Controller for resolving schedule conflicts.
 * <p>
 * This window acts as a visual feedback
 * for the conflicting schedules. Provides a left right panel for display of
 * the schedules and a command box. Window will block all events until it is
 * closed or resolved.
 * </p>
 */
public class ResolveWindow extends UiPart<Stage> {

    private static final String FXML = "ResolveWindow.fxml";

    private Logic logic;

    private LeftRightPanel leftRightPanel;
    private ResultDisplay resultDisplay;
    private Stage parent;

    @FXML
    private StackPane commandBoxPlaceHolder;

    @FXML
    private StackPane resultDisplayPlaceHolder;

    @FXML
    private StackPane leftRightPanelPlaceHolder;


    public ResolveWindow(Stage root, Logic logic) {
        super(FXML, root);
        parent = getRoot();
        this.logic = logic;

        blockEvents(root);
        fillInnerParts();

        setWindowSize(logic.getGuiSettings());
        centerWindow();
    }

    public ResolveWindow(Logic logic) {
        this(new Stage(), logic);
    }

    /**
     * Shows the resolve window.
     */
    public void show() {
        parent.show();
        parent.centerOnScreen();
    }

    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the window and clears the text in LeftRightPanel
     *
     * @see LeftRightPanel#clearAllText()
     */
    public void hideAndClearText() {
        parent.hide();
        leftRightPanel.clearAllText();
    }

    public void focus() {
        getRoot().requestFocus();
    }

    public void setLeftRightText(String leftPanelText, String rightPanelText) {
        leftRightPanel.setLeftPanelText(leftPanelText);
        leftRightPanel.setRightPanelText(rightPanelText);
    }

    private void setWindowSize(GuiSettings guiSettings) {
        parent.setHeight(guiSettings.getWindowHeight());
        parent.setWidth(guiSettings.getWindowWidth());
    }

    private void centerWindow() {
        parent.centerOnScreen();
    }

    private void fillInnerParts() {
        fillLeftRightPanel();
        fillCommandBox();
        fillResultDisplay();
    }

    private void fillResultDisplay() {
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceHolder.getChildren().add(resultDisplay.getRoot());
    }

    private void fillLeftRightPanel() {
        leftRightPanel = new LeftRightPanel();
        leftRightPanelPlaceHolder.getChildren().add(leftRightPanel.getRoot());
    }

    private void fillCommandBox() {
        CommandBox commandBox = new CommandBox(this::execute);
        commandBoxPlaceHolder.getChildren().add(commandBox.getRoot());
    }

    private void blockEvents(Stage root) {
        root.initModality(Modality.APPLICATION_MODAL);
    }

    /**
     * Executor for resolve window's command box.
     * <p>
     *     This executor will only allow resolve commands to be executed.
     * </p>
     * @param commandText user input
     * @return Result from executing a valid command
     */
    private CommandResult execute(String commandText) {
        if (commandText.equals("resolve")) {
            hideAndClearText();
            return new CommandResult("Resolved", false, false);
        } else {
            CommandResult result = new CommandResult("Not resolve command", false, false);
            resultDisplay.setFeedbackToUser(result.getFeedbackToUser());
            return result;
        }
    }
}
