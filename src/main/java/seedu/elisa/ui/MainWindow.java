package seedu.elisa.ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeSet;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import seedu.elisa.commons.core.GuiSettings;
import seedu.elisa.commons.core.LogsCenter;
import seedu.elisa.commons.core.item.Item;
import seedu.elisa.commons.exceptions.IllegalValueException;
import seedu.elisa.game.GameLoop;
import seedu.elisa.game.Grid;
import seedu.elisa.game.Painter;
import seedu.elisa.game.Snake;
import seedu.elisa.logic.Logic;
import seedu.elisa.logic.commands.ClearScreenCommandResult;
import seedu.elisa.logic.commands.CloseCommand;
import seedu.elisa.logic.commands.CloseCommandResult;
import seedu.elisa.logic.commands.CommandResult;
import seedu.elisa.logic.commands.DownCommandResult;
import seedu.elisa.logic.commands.GameCommandResult;
import seedu.elisa.logic.commands.GameHardCommandResult;
import seedu.elisa.logic.commands.OpenCommandResult;
import seedu.elisa.logic.commands.PriorityCommand;
import seedu.elisa.logic.commands.ThemeCommandResult;
import seedu.elisa.logic.commands.UpCommandResult;
import seedu.elisa.logic.commands.exceptions.CommandException;
import seedu.elisa.logic.parser.exceptions.FocusModeException;
import seedu.elisa.logic.parser.exceptions.ParseException;
import seedu.elisa.model.item.CalendarList;
import seedu.elisa.model.item.EventList;
import seedu.elisa.model.item.ReminderList;
import seedu.elisa.model.item.TaskList;
import seedu.elisa.model.item.VisualizeList;
import seedu.elisa.storage.GameStorage;


/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 500;

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final Image redElisa = new Image(getClass().getClassLoader()
            .getResource("images/FocusElisa.PNG").toString());
    private final Image blueElisa = new Image(getClass().getClassLoader()
            .getResource("images/ElisaImageWithoutWords.PNG").toString());
    private final Path gamefilePath = Paths.get("data", "gamescore.json");
    private Stage primaryStage;
    private Logic logic;

    // Elements for Game
    private GameStorage gameStorage;
    private GameLoop loop;
    private Grid grid;
    private GraphicsContext context;
    private TreeSet<Integer> scorelist;

    // Independent Ui parts residing in this Ui container
    private EventListPanel eventListPanel;
    private TaskListPanel taskListPanel;
    private ReminderListPanel reminderListPanel;
    private CalendarPanel calendarPanel;
    private ResultDisplay resultDisplay;
    private Popup popup;

    private String reminderAlarmUrl = getClass().getClassLoader().getResource("sounds/alertChime.mp3").toString();
    private AudioClip reminderAlarm = new AudioClip(reminderAlarmUrl);

    @FXML
    private Scene scene;

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
    private StackPane openItemPlaceholder;

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
    private final Paint elisaTextRedColor = Paint.valueOf("ff8080");
    private final Paint elisaDescRedColor = Paint.valueOf("ffb4b4");

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        gameStorage = new GameStorage(gamefilePath);
        try {
            gameStorage.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        scorelist = gameStorage.getScorelist();
        scorelist.add(0);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        // Listen to changes in focus of stage
        try {
            primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> o, Boolean old, Boolean n) {
                    if (!primaryStage.focusedProperty().get() && popup != null) {
                        popup.hide();
                    } else if (popup != null) {
                        popup.show(primaryStage);
                    } else {
                        // do nothing to the popup
                    }
                }
            });
        } catch (Throwable t) {
            logger.warning("Error with adding listener to primary stage for popup");
        }

        //Listen to changes in tab selection
        viewsPlaceholder.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                        try {
                            if (logic.isFocusMode() && !t1.getId().equalsIgnoreCase("T")) {
                                throw new FocusModeException();
                            }
                            logic.getModel().setVisualList(t1.getId());
                            updatePanels(logic.getVisualList());
                        } catch (FocusModeException e) {
                            viewsPlaceholder.getSelectionModel().select(t);
                            resultDisplay.setFeedbackToUser(e.getMessage());
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
                    setRed();
                } else {
                    setBlue();
                    if (logic.isSystemToggle()) {
                        Platform.runLater(() -> {
                            String feedback;
                            switch (logic.getExitStatus()) {
                            case PRIORITY_TIMEOUT: feedback = PriorityCommand.TIME_OUT;
                                    break;
                            case ALL_TASK_COMPLETED: feedback = PriorityCommand.FINISHED_ALL_TASKS;
                                    break;
                            default:
                                // will never reach here as there are only two cases
                                feedback = "";
                            }

                            resultDisplay.setFeedbackToUser(feedback);
                            updatePanels(logic.getVisualList());
                        });
                    }
                }
            }
        });
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setRed() {
        elisaImage.setImage(redElisa);
        elisaText.setFill(elisaTextRedColor);
        elisaText.setEffect(new Glow(0.2));
        elisaText.setStroke(elisaDescRedColor);
        elisaDescription.setFill(elisaDescRedColor);
        elisaDescription2.setFill(elisaDescRedColor);
    }

    private void setBlue() {
        elisaImage.setImage(blueElisa);
        elisaText.setFill(elisaTextBlueColor);
        elisaText.setEffect(null);
        elisaDescription.setFill(elisaDescBlueColor);
        elisaDescription2.setFill(elisaDescBlueColor);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        updatePanels(logic.getVisualList());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        //Create a ListChangeListener for activeReminders
        ListChangeListener<Item> activeRemindersListener = new ListChangeListener<Item>() {
            @Override
            public void onChanged(Change<? extends Item> c) {
                while (c.next()) {
                    createReminders(c);
                }
            }

            private void createReminders(Change<? extends Item> c) {
                if (c.getAddedSize() > 0) {
                    //Plays sound
                    reminderAlarm.play(30);
                    for (Item newItem : c.getAddedSubList()) {
                        Platform.runLater(() -> {
                            //Populate resultDisplay with reminder textbox
                            resultDisplay.setReminderToUser(newItem.getReminderMessage());
                        });
                    }
                }
            }
        };


        //Binds a ListChangeListener to activeRemindersList
        logic.getActiveRemindersListProperty().addListener(activeRemindersListener);

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
            if (logic.getVisualList() instanceof TaskList) {
                taskListPanel.scrollUp();
            } else if (logic.getVisualList() instanceof EventList) {
                eventListPanel.scrollUp();
            } else if (logic.getVisualList() instanceof ReminderList) {
                reminderListPanel.scrollUp();
            }
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
            if (logic.getVisualList() instanceof TaskList) {
                taskListPanel.scrollDown();
            } else if (logic.getVisualList() instanceof EventList) {
                eventListPanel.scrollDown();
            } else if (logic.getVisualList() instanceof ReminderList) {
                reminderListPanel.scrollDown();
            }
            break;
        default:
        }
    }

    /**
     * Generates an appropriate popup with the given item, formatted accordingly
     * @param item details to fill in the popup with
     */
    private void openUp(Item item) {
        Popup popup = new Popup();
        popup.getContent().add(new OpenItem(item).getRoot());
        popup.setHeight(1000);
        popup.setWidth(500);
        popup.setHideOnEscape(false);

        this.popup = popup;
        popup.show(primaryStage);
    }

    /**
     * Carries out the operations to generate a popup which expands the view of the given item
     * @param cr containing the item to open
     * @return the result of executing this command
     */
    private CommandResult executeOpen(CommandResult cr) {
        CommandResult commandResult = cr;
        if (popup != null) {
            // Previous popup still exists
            commandResult = new CommandResult("Hey, close the previous one first!");
        } else {
            // Open new popup to show the item
            OpenCommandResult result = (OpenCommandResult) commandResult;
            openUp(result.getItem());
            viewsPlaceholder.setEffect(new GaussianBlur());
        }
        return commandResult;
    }

    /**
     * Carries out operations to close the current popup.
     * @param cr to carry out
     * @return result of executing this command
     */
    private CommandResult executeClose(CommandResult cr) {
        CommandResult commandResult = cr;
        if (popup == null) {
            // Nothing to close
            commandResult = new CloseCommandResult(CloseCommand.MESSAGE_FAILURE);
        } else {
            popup.hide();
            this.popup = null;
            viewsPlaceholder.setEffect(null);
        }
        return commandResult;
    }


    /**
     * Updates the panels to display the correct list of item.
     */
    public void updatePanels(VisualizeList targetList) {
        if (targetList instanceof TaskList) {
            taskListPanel = new TaskListPanel(logic.getVisualList());
            taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());
        } else if (targetList instanceof EventList) {
            eventListPanel = new EventListPanel(logic.getVisualList());
            eventListPanelPlaceholder.getChildren().add(eventListPanel.getRoot());
        } else if (targetList instanceof ReminderList) {
            reminderListPanel = new ReminderListPanel(logic.getVisualList());
            reminderListPanelPlaceholder.getChildren().add(reminderListPanel.getRoot());
        } else { }
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
     * Changes the theme
     *
     * @param theme
     */
    private void changeTheme(String theme) {
        scene.getStylesheets().remove(0);
        scene.getStylesheets().remove(0);
        switch(theme.trim()) {
        case "white":
            scene.getStylesheets().add("view/WhiteTheme.css");
            scene.getStylesheets().add("view/Extensions.css");
            break;
        case "black":
            scene.getStylesheets().add("view/DarkTheme.css");
            scene.getStylesheets().add("view/Extensions.css");
            break;
        default:
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.elisa.logic.Logic#execute(String)
     */
    @FXML
    private CommandResult executeCommand(String commandText) throws Exception {
        try {
            resultDisplay.setMessageFromUser(commandText);
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());

            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

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

            if (commandResult instanceof ThemeCommandResult) {
                changeTheme(commandResult.getTheme());
                return commandResult;
            }

            if (commandResult instanceof OpenCommandResult) {
                commandResult = executeOpen(commandResult);
                return commandResult;
            }

            if (commandResult instanceof CloseCommandResult) {
                commandResult = executeClose(commandResult);
                return commandResult;
            }

            if (commandResult instanceof ClearScreenCommandResult) {
                resultDisplay.clear();
            }

            if (commandResult instanceof GameCommandResult) {
                startgame(primaryStage);
            }

            if (commandResult instanceof GameHardCommandResult) {
                startgameHard(primaryStage);
            }

            updatePanels(logic.getVisualList());
            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Starts easy mode of the game
     * @param primaryStage
     * @throws Exception
     */
    public void startgame(Stage primaryStage) throws Exception {

        StackPane root = new StackPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        context = canvas.getGraphicsContext2D();

        canvas.setFocusTraversable(true);

        gameCheck(canvas);

        resetgame();

        root.getChildren().add(canvas);

        Scene gamescene = new Scene(root);

        primaryStage.setResizable(true);
        primaryStage.setHeight(HEIGHT + 100);
        primaryStage.setWidth(WIDTH + 100);
        primaryStage.setTitle("Snake Game");
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setScene(gamescene);
        primaryStage.show();

        Thread t = new Thread(loop);
        t.start();
    }

    /**
     * Checks the key pressed in the game.
     * @param canvas
     */
    private void gameCheck(Canvas canvas) {
        canvas.setOnKeyPressed(e -> {
            Snake snake = grid.getSnake();
            if (loop.isKeyPressed()) {
                return;
            }
            if (loop.isPaused()) {
                scorelist.add(loop.getCurrentScore());
                gameStorage.updateScoreList(loop.getCurrentScore());
                try {
                    gameStorage.save();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            switch (e.getCode()) {
            case UP:
                snake.setUp();
                break;
            case DOWN:
                snake.setDown();
                break;
            case LEFT:
                snake.setLeft();
                break;
            case RIGHT:
                snake.setRight();
                break;
            case ENTER:
                if (loop.isPaused()) {
                    resetgame();
                    Thread thread = new Thread(loop);
                    thread.start();
                }
                break;
            case ESCAPE:
                exitgame();
                break;
            case E:
                if (loop.isPaused()) {
                    resetgameEasy();
                    Thread thread = new Thread(loop);
                    thread.start();
                }
                break;
            case H:
                if (loop.isPaused()) {
                    resetgameHard();
                    Thread thread = new Thread(loop);
                    thread.start();
                }
                break;
            default:
            }
        });
    }
    /**
     * Starts hard mode of the game
     * @param primaryStage
     * @throws Exception
     */
    public void startgameHard(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        context = canvas.getGraphicsContext2D();

        canvas.setFocusTraversable(true);

        gameCheck(canvas);

        resetgameHard();
        root.getChildren().add(canvas);

        Scene gamescene = new Scene(root);

        primaryStage.setResizable(true);
        primaryStage.setHeight(HEIGHT + 100);
        primaryStage.setWidth(WIDTH + 100);
        primaryStage.setTitle("Snake Game");
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.setScene(gamescene);
        primaryStage.show();

        Thread t = new Thread(loop);
        t.start();
    }

    private void resetgame() {
        grid = new Grid(WIDTH, HEIGHT);
        loop = new GameLoop(grid, context, scorelist);
        Painter.paint(grid, context);
    }

    private void resetgameEasy() {
        resetgame();
    }

    private void resetgameHard() {
        grid = new Grid(WIDTH, HEIGHT, true);
        loop = new GameLoop(grid, context, scorelist);
        Painter.paint(grid, context);
    }

    private void exitgame() {
        primaryStage.setScene(scene);
        primaryStage.setHeight(primaryStage.getMinHeight());
        primaryStage.setWidth(primaryStage.getMinWidth());
    }
}
