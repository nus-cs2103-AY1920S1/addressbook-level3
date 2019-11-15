package seedu.revision.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seedu.revision.commons.core.GuiSettings;
import seedu.revision.logic.Logic;
import seedu.revision.logic.commands.exceptions.CommandException;
import seedu.revision.logic.commands.main.CommandResult;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.ui.answerables.AnswerableListPanel;
import seedu.revision.ui.statistics.GraphListPanel;
import seedu.revision.ui.statistics.StatisticsListPanel;

/**
 * The Parent Window. Provides the basic application layout containing a menu bar and space where other JavaFX
 * elements can be placed. Other windows can extend this basic window layout.
 */
public abstract class ParentWindow extends UiPart<Stage> {

    protected Stage primaryStage;
    protected Logic logic;

    // Independent Ui parts residing in this Ui container
    protected AnswerableListPanel answerableListPanel;
    protected StatisticsListPanel statisticsListPanel;
    protected GraphListPanel graphListPanel;
    protected ResultDisplay resultDisplay;
    protected HelpWindow helpWindow;

    @FXML
    protected StackPane commandBoxPlaceholder;

    @FXML
    protected MenuItem helpMenuItem;

    @FXML
    protected StackPane statusbarPlaceholder;

    @FXML
    protected StackPane answerableListPanelPlaceholder;

    @FXML
    protected StackPane resultDisplayPlaceholder;

    @FXML
    protected StackPane scoreProgressAndTimerPlaceholder;

    public ParentWindow(String fxml, Stage primaryStage, Logic logic) {
        super(fxml, primaryStage);
        this.primaryStage = primaryStage;
        this.logic = logic;
        setAccelerators();
        helpWindow = new HelpWindow();
        setWindowDefaultSize(this.logic.getGuiSettings());
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Logic getLogic() {
        return logic;
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
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    abstract void fillInnerParts();

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
    protected abstract void handleExit();

    /**
     * Executes the command and returns the result.
     */
    protected abstract CommandResult executeCommand(String commandText)
            throws CommandException, ParseException, IOException;

}
