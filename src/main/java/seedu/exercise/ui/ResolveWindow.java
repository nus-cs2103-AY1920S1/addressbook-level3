package seedu.exercise.ui;

import java.util.logging.Logger;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.logic.Logic;
import seedu.exercise.logic.commands.CommandResult;
import seedu.exercise.logic.commands.ResolveCommand;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.parser.exceptions.ParseException;

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
    private static final String UNABLE_TO_CLOSE_WINDOW = "Please resolve your conflict using the resolve command.\n"
            + "You are not allowed to close this window before resolving this conflict.";
    private static final String INITIAL_HELP_MESSAGE = "Please resolve your conflict with the resolve command."
           + ResolveCommand.MESSAGE_USAGE;

    private final Logger logger = LogsCenter.getLogger(getClass());
    private OnResolveSuccessListener resolveSuccessListener;

    private Logic logic;

    private LeftRightPanel leftRightPanel;
    private CommandBox commandBox;
    private ResultDisplay resultDisplay;
    private ResultDisplay mainWindowDisplay;
    private Stage parent;

    @FXML
    private StackPane commandBoxPlaceHolder;

    @FXML
    private StackPane resultDisplayPlaceHolder;

    @FXML
    private StackPane leftRightPanelPlaceHolder;


    public ResolveWindow(Stage root, Logic logic, ResultDisplay mainWindowDisplay) {
        super(FXML, root);
        parent = getRoot();
        this.logic = logic;
        this.mainWindowDisplay = mainWindowDisplay;

        blockEvents(root);
        fillInnerParts();

        setWindowSize(logic.getGuiSettings());
        centerWindow();
    }

    public ResolveWindow(Logic logic, ResultDisplay mainWindowDisplay) {
        this(new Stage(), logic, mainWindowDisplay);
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
     * @see LeftRightPanel#clearAll()
     */
    public void hideAndClearPanels() {
        leftRightPanel.clearAll();
        resultDisplay.clearText();
        commandBox.clearText();
        parent.hide();
    }

    public void focus() {
        getRoot().requestFocus();
    }

    public void setLeftRightPanel() {
        logger.info("Resolve window showing scheduled "
                + logic.getConflict().getScheduledName()
                + ", conflicting "
                + logic.getConflict().getConflictedName());
        resultDisplay.setFeedbackToUser(INITIAL_HELP_MESSAGE);
        leftRightPanel.setLeftPanel(logic.getConflict().getScheduledRegime());
        leftRightPanel.setRightPanel(logic.getConflict().getConflictingRegime());
    }

    /**
     * The user is not allowed to close the {@code ResolveWindow} using the exit button.
     * All events are consumed and feedback is given to user to resolve the conflict.
     */
    @FXML
    private void handleCloseRequest(Event evt) {
        evt.consume();
        resultDisplay.setFeedbackToUser(UNABLE_TO_CLOSE_WINDOW);
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
        commandBox = new CommandBox(this::execute);
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
    private CommandResult execute(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (!commandResult.isShowResolve()) {
                informResolveSuccessListener(commandResult);
                this.hideAndClearPanels();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    void setOnResolveSuccessListener(OnResolveSuccessListener listener) {
        this.resolveSuccessListener = listener;
    }

    private void informResolveSuccessListener(CommandResult commandResult) {
        if (resolveSuccessListener != null) {
            resolveSuccessListener.onResolveSuccess(commandResult);
        }
    }

    /**
     * Informs interested classes of scheduling conflict resolution being successful.
     */
    interface OnResolveSuccessListener {
        void onResolveSuccess(CommandResult result);
    }
}
