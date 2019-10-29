package seedu.elisa.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.elisa.commons.core.GuiSettings;
import seedu.elisa.commons.core.LogsCenter;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.exceptions.IllegalValueException;
import seedu.elisa.logic.Logic;
import seedu.elisa.logic.commands.CommandResult;
import seedu.elisa.logic.commands.DownCommandResult;
import seedu.elisa.logic.commands.PriorityCommand;
import seedu.elisa.logic.commands.UpCommandResult;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.logic.parser.exceptions.ParseException;
import seedu.elisa.model.item.CalendarList;
import seedu.elisa.model.item.EventList;
import seedu.elisa.model.item.ReminderList;
import seedu.elisa.model.item.TaskList;
import seedu.elisa.model.item.VisualizeList;




/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final Image redElisa = new Image(getClass().getClassLoader()
            .getResource("images/FocusElisa.png").toString());
    private final Image blueElisa = new Image(getClass().getClassLoader()
            .getResource("images/ElisaImageWithoutWords.png").toString());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private EventListPanel eventListPanel;
    private TaskListPanel taskListPanel;
    private ReminderListPanel reminderListPanel;
    private CalendarPanel calendarPanel;
    private ResultDisplay resultDisplay;

    private String reminderAlarmUrl = getClass().getClassLoader().getResource("sounds/alertChime.mp3").toString();
    private AudioClip reminderAlarm = new AudioClip(reminderAlarmUrl);

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane eventListPanelPlaceholder;

    @FXML
    private StackPane reminderListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane calendarPanelPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private TabPane viewsPlaceholder;

    @FXML
    private ImageView elisaImage;

    @FXML
    private Text elisaText;

    @FXML
    private Text elisaDescription;

    @FXML
    private Text elisaDescription2;

    private final Paint elisaTextBlueColor = elisaText.getFill();
    private final Paint elisaDescBlueColor = elisaDescription.getFill();
    private final Paint elisaTextRedColor = Paint.valueOf("ff0000");
    private final Paint elisaDescRedColor = Paint.valueOf("fe4949");

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        //Listen to changes in tab selection
        viewsPlaceholder.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        try {
                            logic.getModel().setVisualList(t1.getId());
                            updatePanels();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (IllegalValueException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        logic.getPriorityMode().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    elisaImage.setImage(redElisa);
                    setTextRed();
                } else {
                    elisaImage.setImage(blueElisa);
                    setTextDefault();
                    if (logic.isSystemToggle()) {
                        Platform.runLater(() -> resultDisplay.setFeedbackToUser(PriorityCommand.PRIORITY_MODE_OFF));
                    }
                }
            }
        });
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setTextRed() {
        elisaText.setFill(elisaTextRedColor);
        elisaDescription.setFill(elisaDescRedColor);
        elisaDescription2.setFill(elisaDescRedColor);
    }

    private void setTextDefault() {
        elisaText.setFill(elisaTextBlueColor);
        elisaDescription.setFill(elisaDescBlueColor);
        elisaDescription2.setFill(elisaDescBlueColor);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        updatePanels();

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        //Get property.addListener

        logic.getActiveRemindersListProperty().addListener(new ListChangeListener<Item>() {
            @Override
            public void onChanged(Change<? extends Item> c) {
                System.out.println("Change Detected");
                while (c.next()) {
                    for (Item newItem : c.getAddedSubList()) {
                        Platform.runLater(() -> {
                            resultDisplay.setFeedbackToUser(newItem.getReminderMessage());
                            reminderAlarm.play(50);
                        });
                    }
                }
            }
        });
        //to listen for change in active
        //while !active.isEmpty()
        //resultDisplay.setFeedbackToUser(property.popReminder.getReminderMessage);

        CommandBox commandBox = new CommandBox(this::executeCommand);
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

    /**
     * Switches the view.
     *
     * @param list
     */
    private void handleSwitchView(VisualizeList list) {
        if (list instanceof TaskList) {
            viewsPlaceholder.getSelectionModel().select(0);
        } else if (list instanceof EventList) {
            viewsPlaceholder.getSelectionModel().select(1);
        } else if (list instanceof ReminderList) {
            viewsPlaceholder.getSelectionModel().select(2);
        } else if (list instanceof CalendarList) {
            viewsPlaceholder.getSelectionModel().select(3);
        }
    }

    /**
     * Scrolls the target pane up
     *
     * @param pane
     */
    private void scrollUp(String pane) {
        switch(pane) {
        case "resultDisplay":
            resultDisplay.scrollUp();
            break;
        case "tabPane":
            eventListPanel.scrollUp();
            taskListPanel.scrollUp();
            reminderListPanel.scrollUp();
            break;
        default:
        }
    }

    /**
     * Scrolls the target pane down
     *
     * @param pane
     */
    private void scrollDown(String pane) {
        switch(pane) {
        case "resultDisplay":
            resultDisplay.scrollDown();
            break;
        case "tabPane":
            eventListPanel.scrollDown();
            taskListPanel.scrollDown();
            reminderListPanel.scrollDown();
            break;
        default:
        }
    }

    /**
     * Updates the panels to display the correct list of item.
     */
    public void updatePanels() {
        taskListPanel = new TaskListPanel(logic.getVisualList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        eventListPanel = new EventListPanel(logic.getVisualList());
        eventListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());

        reminderListPanel = new ReminderListPanel(logic.getVisualList());
        reminderListPanelPlaceholder.getChildren().add(reminderListPanel.getRoot());

        calendarPanel = new CalendarPanel(logic.getVisualList());
        calendarPanelPlaceholder.getChildren().add(calendarPanel.getRoot());
    }

    public TaskListPanel getTaskListPanel() {
        return taskListPanel;
    }

    public EventListPanel getEventListPanel() {
        return eventListPanel;
    }

    public ReminderListPanel getReminderListPanel() {
        return reminderListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.elisa.logic.Logic#execute(String)
     */
    @FXML
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            resultDisplay.setMessageFromUser(commandText);
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            if (!(commandResult instanceof UpCommandResult) && !(commandResult instanceof DownCommandResult)) {
                resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            handleSwitchView(logic.getVisualList());

            if (commandResult instanceof UpCommandResult) {
                scrollUp(commandResult.getPane());
                return commandResult;
            }

            if (commandResult instanceof DownCommandResult) {
                scrollDown(commandResult.getPane());
                return commandResult;
            }

            updatePanels();
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
