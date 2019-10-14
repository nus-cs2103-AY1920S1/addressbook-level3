package tagline.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tagline.commons.core.GuiSettings;
import tagline.commons.core.LogsCenter;
import tagline.logic.Logic;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.CommandResult.ViewType;
import tagline.logic.commands.exceptions.CommandException;
import tagline.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static final double RESULT_PANE_FADE_TRANSITION_DURATION = 0.5;
    private static final double RESULT_PANE_FADE_TRANSITION_OPACITY_FROM = 0.3;
    private static final double RESULT_PANE_FADE_TRANSITION_OPACITY_TO = 1.0;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private ChatPane chatPane;

    /** The current view displayed in the result pane. */
    private ViewType currentViewType;

    /** Stores all result pane views using the ViewType as the key. */
    private Map<ViewType, ResultView> resultViewMap;

    private HelpWindow helpWindow;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane chatPanePlaceholder;

    @FXML
    private StackPane resultPanePlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, thus
         * some accelerators (e.g F1) will not work when the focus is in them because
         * the key event is consumed by the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Changes the view in the result pane according to ViewType.
     *
     * @param viewType the ViewType to switch to
     */
    void setCurrentViewType(ViewType viewType) {
        //no preferred view, don't switch
        if (viewType == ViewType.NONE) {
            return;
        }

        //already correct view, no need to switch
        if (currentViewType == viewType) {
            return;
        }

        setResultPaneView(resultViewMap.get(viewType));
        currentViewType = viewType;
    }

    /**
     * Adds a fade in transition to a result view.
     *
     * @param resultView the ResultView to animate
     */
    void animateFadeIn(ResultView resultView) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.seconds(RESULT_PANE_FADE_TRANSITION_DURATION));
        fadeTransition.setFromValue(RESULT_PANE_FADE_TRANSITION_OPACITY_FROM);
        fadeTransition.setToValue(RESULT_PANE_FADE_TRANSITION_OPACITY_TO);

        fadeTransition.setNode(resultView.getRoot());
        fadeTransition.play();
    }

    /**
     * Changes the view in the result pane.
     *
     * @param resultView Next view to display
     */
    void setResultPaneView(ResultView resultView) {
        resultPanePlaceholder.getChildren().clear();

        animateFadeIn(resultView);
        resultPanePlaceholder.getChildren().add(resultView.getRoot());
    }

    /**
     * Initializes the chat pane.
     */
    void initChatPane() {
        chatPane = new ChatPane();
        chatPane.fillInnerParts(this::executeCommand);
        chatPanePlaceholder.getChildren().add(chatPane.getRoot());
    }

    /**
     * Initializes the result pane and all its views.
     */
    void initResultPane() {
        resultViewMap = new HashMap<>();

        ContactResultView contactResultView = new ContactResultView();
        contactResultView.fillInnerParts(logic.getFilteredContactList());
        resultViewMap.put(ViewType.CONTACT, contactResultView);

        DummyResultView dummyResultView = new DummyResultView();
        resultViewMap.put(ViewType.DUMMY, dummyResultView);

        //set to contact result pane by default
        setCurrentViewType(ViewType.CONTACT);
    }

    /**
     * Initializes the status bar.
     */
    void initStatusBar() {
        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        initStatusBar();
        initChatPane();
        initResultPane();
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
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see tagline.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        chatPane.setCommandFromUser(commandText);

        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            chatPane.setFeedbackToUser(commandResult.getFeedbackToUser());

            setCurrentViewType(commandResult.getViewType());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            chatPane.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
