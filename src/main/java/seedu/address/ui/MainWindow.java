package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.appstatus.PageType;
import seedu.address.ui.components.CommandBox;
import seedu.address.ui.components.ResultDisplay;
import seedu.address.ui.components.StatusBarFooter;
import seedu.address.ui.itinerary.*;
import seedu.address.ui.template.Page;
import seedu.address.ui.trips.EditTripPage;
import seedu.address.ui.trips.TripsPage;
import seedu.address.ui.utility.PreferencesPage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static String FXML = "MainWindow.fxml";
    private static final int PAGE_TRANSITION_DURATION_MILLIS = 500;
    private static final double PAGE_TRANSITION_INITIAL_OPACITY = 0.2;

    protected final Logger logger = LogsCenter.getLogger(getClass());
    protected Stage primaryStage;
    protected Logic logic;
    protected Model model;

    private CommandUpdater commandUpdater;

    @FXML
    protected StackPane commandBoxPlaceholder;

    @FXML
    protected StackPane resultDisplayPlaceholder;

    @FXML
    protected StackPane statusbarPlaceholder;

    @FXML
    protected StackPane contentPlaceholder;

    // Independent Ui parts residing in this Ui container
    protected ResultDisplay resultDisplay;
    HelpWindow helpWindow;

    public MainWindow(Stage primaryStage, Logic logic, Model model) {
        super(FXML, primaryStage);

        this.primaryStage = primaryStage;
        this.logic = logic;
        this.model = model;

        setStageListeners();
        fillInnerParts();
        helpWindow = new HelpWindow();
    }

    private void setStageListeners() {
        ChangeListener<Number> guiChangeListener = (observable, oldValue, newValue) -> {
            if (model.getUserPrefs().isGuiPrefsLocked()) {
                setWindowDefaultSize(model.getGuiSettings());
            } else {
                GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                        (int) primaryStage.getX(), (int) primaryStage.getY());
                model.setGuiSettings(guiSettings);
            }
        };

        primaryStage.widthProperty().addListener(guiChangeListener);
        primaryStage.heightProperty().addListener(guiChangeListener);
        primaryStage.xProperty().addListener(guiChangeListener);
        primaryStage.yProperty().addListener(guiChangeListener);
    }

    private void fillInnerParts() {
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(model.getTravelPalFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    protected void show() {
        setWindowDefaultSize(model.getGuiSettings());
        handleSwitch();
        primaryStage.show();
    }

    /**
     * Executes the command and returns the {@code CommandResult}.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    public CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.doSwitchPage()) {
                handleSwitch();
            }

            commandUpdater.executeUpdateCallback();

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Executes the command and returns the {@code CommandResult}.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    public CommandResult executeGuiCommand(String commandText) {
        try {
            return executeCommand(commandText);
        } catch (CommandException | ParseException e) {
            return null;
        }
    }

    /**
     * Opens the help window.
     */
    @FXML
    private void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        //Save gui size on exit only if gui prefs are not locked.
        if (!model.getUserPrefs().isGuiPrefsLocked()) {
            GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                    (int) primaryStage.getX(), (int) primaryStage.getY());
            model.setGuiSettings(guiSettings);
        }
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Retrieves the {@code Page} type and attempts to switch the content in
     * {@code contentPlaceholder} with it.
     * TODO
     */
    private void handleSwitch() {
        final String MESSAGE_PAGE_NOT_IMPLEMENTED = "Sorry! We haven't implemented the %1$s page!";
        PageType currentPageType = model.getPageStatus().getPageType();
        Page<? extends Node> newPage;

        switch (currentPageType) {
        case TRIP_MANAGER:
            newPage = new TripsPage(this, logic, model);
            break;
        case ADD_TRIP:
            newPage = new EditTripPage(this, logic, model);
            break;
        case PREFERENCES:
            newPage = new PreferencesPage(this, logic, model);
            break;
        case ADD_EVENT:
            newPage = new EditEventPage(this, logic, model);
            break;
        case OVERALL_VIEW:
            newPage = new DaysPage(this, logic, model);
            break;
        case ADD_DAY:
            newPage = new EditDayPage(this, logic, model);
            break;
        case EVENT_PAGE:
            newPage = new EventsPage(this, logic, model);
            break;
        case ITINERARY:
            newPage = new ItineraryPage(this, logic, model);
            break;
        default:
            resultDisplay.setFeedbackToUser(
                    String.format(MESSAGE_PAGE_NOT_IMPLEMENTED, currentPageType.toString()));
            return;
        }

        switchContent(newPage);
        this.commandUpdater = newPage::fillPage;
    }

    /**
     * Switches the content in the {@code contentPlaceholder} {@code StackPane}.
     *
     * @param page The {@code Page} to switch to.
     */
    private void switchContent(Page<? extends Node> page) {
        setWindowDefaultSize(model.getGuiSettings());
        Node pageNode = page.getRoot();

        //transition
        List<Node> currentChildren = contentPlaceholder.getChildren();
        int numChildren = currentChildren.size();
        assert numChildren == 0 || numChildren == 1;

        pageNode.translateXProperty().set(contentPlaceholder.getWidth());
        pageNode.opacityProperty().set(PAGE_TRANSITION_INITIAL_OPACITY);
        contentPlaceholder.getChildren().add(pageNode);

        Timeline timeline = new Timeline();
        KeyValue yTranslateKv =
                new KeyValue(pageNode.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyValue opacityKv =
                new KeyValue(pageNode.opacityProperty(), 1.0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(
                new Duration(PAGE_TRANSITION_DURATION_MILLIS), yTranslateKv, opacityKv);
        timeline.getKeyFrames().add(keyFrame);

        if (numChildren == 1) {
            Node previousPage = currentChildren.get(0);
            timeline.setOnFinished(event -> currentChildren.remove(previousPage));
        }

        timeline.play();
    }

    @FunctionalInterface
    public interface CommandUpdater {
        void executeUpdateCallback();
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

    //setAccelerator code from AB3 for opening help window
    /*
    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     *//*
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);
    /*
        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        /*
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }
    */
}
