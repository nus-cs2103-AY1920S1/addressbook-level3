package seedu.mark.ui;

import static java.util.Objects.requireNonNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Logger;

import org.controlsfx.control.Notifications;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seedu.mark.commons.core.GuiSettings;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.logic.Logic;
import seedu.mark.logic.commands.TabCommand.Tab;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.reminder.Reminder;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private BookmarkListPanel bookmarkListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private BrowserPanel browserPanel;
    private DashboardPanel dashboardPanel;
    private OfflinePanel offlinePanel;

    @FXML
    private ToggleButton dashboardButton;

    @FXML
    private ToggleButton onlineButton;

    @FXML
    private ToggleButton offlineButton;

    @FXML
    private SplitPane splitPane;

    @FXML
    private StackPane mainViewAreaPlaceholder;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane bookmarkListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane folderStructurePlaceholder;

    private ObservableList<Reminder> reminders;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        reminders = logic.getReminderList();
        displayReminderMessage();
        logic.startMarkTimer(executor);
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
    void fillInnerParts() {
        browserPanel = new BrowserPanel(logic.getCurrentUrlProperty());
        dashboardPanel = new DashboardPanel(logic, getCurrentUrlChangeHandler());
        offlinePanel = new OfflinePanel(logic.getObservableDocument(),
                logic.getObservableOfflineDocNameCurrentlyShowing());
        mainViewAreaPlaceholder.getChildren().add(dashboardPanel.getRoot());

        bookmarkListPanel = new BookmarkListPanel(logic.getFilteredBookmarkList(),
                logic.getCurrentUrlProperty(), logic.getBookmarkDisplayingCacheProperty(),
                getCurrentUrlChangeHandler());
        bookmarkListPanelPlaceholder.getChildren().add(bookmarkListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getMarkFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

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
        executor.shutdownNow();
        Platform.exit();
    }

    /**
     * Switches to dashboard view
     */
    @FXML
    public void handleSwitchToDashboard() {
        dashboardButton.setSelected(true);
        mainViewAreaPlaceholder.getChildren().set(0, dashboardPanel.getRoot());
    }

    /**
     * Switches to online view
     */
    @FXML
    public void handleSwitchToOnline() {
        onlineButton.setSelected(true);
        mainViewAreaPlaceholder.getChildren().set(0, browserPanel.getRoot());
    }

    /**
     * Switches to offline view
     */
    @FXML
    public void handleSwitchToOffline() {
        offlineButton.setSelected(true);
        mainViewAreaPlaceholder.getChildren().set(0, offlinePanel.getRoot());
    }

    /**
     * Directs to the appropriate handler to switch Tab.
     * @param tab The tab to switch to
     */
    private void handleTabSwitchRequestIfAny(Tab tab) {
        requireNonNull(tab);

        switch (tab) {
        case DASHBOARD:
            handleSwitchToDashboard();
            break;
        case ONLINE:
            handleSwitchToOnline();
            break;
        case OFFLINE:
            handleSwitchToOffline();
            break;
        default:
            break;
        }
    }

    private void handleFolderExpand(int levelsToExpand) {
        dashboardPanel.folderStructureTreeView.expand(levelsToExpand);
    }

    public Consumer<Url> getCurrentUrlChangeHandler() {
        return url -> {
            logic.setCurrentUrl(url);
            handleSwitchToOnline();
        };
    }

    public BookmarkListPanel getBookmarkListPanel() {
        return bookmarkListPanel;
    }

    public DashboardPanel getDashboardPanel() {
        return dashboardPanel;
    }

    public OfflinePanel getOfflinePanel() {
        return offlinePanel;
    }

    public BrowserPanel getBrowserPanel() {
        return browserPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.mark.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
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

            if (commandResult.getTab() != null) {
                handleTabSwitchRequestIfAny(commandResult.getTab());
            }

            if (commandResult.getLevelsToExpand() != 0) {
                handleFolderExpand(commandResult.getLevelsToExpand());
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }

    /**
     * Compares two time in hours.
     *
     * @param before the time that is before.
     * @param after the time that is after.
     * @return the difference of two time in hour.
     */
    private long compareHour(LocalDateTime before, LocalDateTime after) {
        return Duration.between(before, after).toHours();
    }

    /**
     * Compares two time in minutes.
     *
     * @param before the time that is before.
     * @param after the time that is after.
     * @return the difference of two time in minute.
     */
    private long compareMinute(LocalDateTime before, LocalDateTime after) {
        return Duration.between(before, after).toMinutes();
    }

    /**
     * Creates a notification for a specific reminder.
     *
     * @param reminder the reminder that is used to create notification.
     * @return the notification for reminder.
     */
    private Notifications getNotification(Reminder reminder) {
        Notifications notif = Notifications.create();
        notif.title("Reminder Notification");


        Bookmark bookmark = logic.getBookmarkFromReminder(reminder);
        String remindMessage = reminder.getNote().toString() + "\n"
                + "Bookmark: " + bookmark.getName().toString() + "\n"
                + "Due at : " + reminder.getFormattedTime();
        notif.text(remindMessage);
        Image image = new Image("/images/bell.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        notif.graphic(imageView);
        notif.hideAfter(javafx.util.Duration.seconds(60));
        notif.position(Pos.BOTTOM_RIGHT);

        return notif;
    }

    /**
     * Displays the reminder notification.
     */
    private void displayReminderMessage() {

        Runnable task = new Runnable() {
            public void run() {

                LocalDateTime now = LocalDateTime.now();

                try {
                    for (int i = 0; i < reminders.size(); i++) {
                        Reminder reminder = reminders.get(i);
                        LocalDateTime remindTime = reminder.getRemindTime();
                        if (now.isBefore(remindTime) && compareHour(now, remindTime) < 5 && !reminder.getShow()) {
                            Notifications notif = getNotification(reminder);

                            Platform.runLater(() -> {
                                notif.show();
                            });

                            reminder.toShow();

                            if (compareMinute(now, remindTime) < 0) {
                                reminder.setDue();
                                continue;
                            }

                        } else if (compareMinute(now, remindTime) == 0 && !reminder.getDue()) {
                            Notifications noti = getNotification(reminder);

                            Platform.runLater(() -> {
                                noti.show();
                            });
                            reminder.setDue();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        executor.scheduleAtFixedRate(task, 10, 10, TimeUnit.SECONDS);
    }
}
