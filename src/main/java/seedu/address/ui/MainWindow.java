package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import seedu.address.ui.components.CommandBox;
import seedu.address.ui.components.ResultDisplay;
import seedu.address.ui.components.StatusBarFooter;
import seedu.address.ui.trips.EditTripPage;
import seedu.address.ui.trips.TripsPage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static String FXML = "MainWindow.fxml";

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


        fillInnerParts();
        //setAccelerators();

        helpWindow = new HelpWindow();
    }

    private void fillInnerParts() {
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(model.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

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
    /**
     * Fills up all the placeholders of this window.
     */
    //protected abstract void fillInnerParts();

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    protected void show() {
        primaryStage.show();
    }

    /**
     * Executes the command and returns the result.
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

            if (commandResult.getPage().isPresent()) {
                switchWindow(commandResult.getPage().get());
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
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        model.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public void switchHandler(Node node, CommandUpdater commandUpdater) {
        //transition
        List<Node> currentChildren = contentPlaceholder.getChildren();
        node.translateXProperty().set(primaryStage.getWidth());
        contentPlaceholder.getChildren().add(node);

        Timeline timeline = new Timeline();
        KeyValue yTranslateKv =
                new KeyValue(node.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(new Duration(200), yTranslateKv);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        this.commandUpdater = commandUpdater;
    }

    private void switchWindow(Class<? extends Page> mainWindowClass) {
        Page navigation = null;
        if (TripsPage.class.equals(mainWindowClass)) {
            navigation = new TripsPage(this, logic, model);
        } else if (EditTripPage.class.equals(mainWindowClass)) {
            navigation = new EditTripPage(this, logic, model);
        } /*else if (DaysPage.class.equals(mainWindowClass)) {
            navigation = DaysPage::switchTo;
        } else if (EditDayPage.class.equals(mainWindowClass)) {
            navigation = EditDayPage::switchTo;
        } else if (EventsPage.class.equals(mainWindowClass)) {
            navigation = EventsPage::switchTo;
        } else if (EditEventPage.class.equals(mainWindowClass)) {
            navigation = EditEventPage::switchTo;
        } else if (PreferencesPage.class.equals(mainWindowClass)) {
            navigation = PreferencesPage::switchTo;
        }*/
        if (navigation != null) {
            navigation.switchTo();
        }
    }

    @FunctionalInterface
    public interface CommandUpdater {
        void executeUpdateCallback();
    }
}
