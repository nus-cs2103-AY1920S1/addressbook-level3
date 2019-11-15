package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.achievements.ui.AchievementsPage;
import seedu.address.address.ui.AddressBookPage;
import seedu.address.calendar.ui.CalendarPage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.diaryfeature.ui.DiaryPage;
import seedu.address.financialtracker.ui.FinancialTrackerPage;
import seedu.address.itinerary.ui.ItineraryPage;
import seedu.address.logic.Logic;

//import seedu.address.address.ui.AddressBookPage;

/**
 * The Main Window. Provides the basic application layout containing a menu bar
 * and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    private FinancialTrackerPage financialTrackerPage;
    private CalendarPage calendarPage;
    private ItineraryPage itineraryPage;
    private DiaryPage diaryPage;
    private AchievementsPage achievementsPage;
    private AddressBookPage addressBookPage;
    private MainPage mainPage;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private Scene commonScene;

    @FXML
    private VBox backgroundPlaceholder;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        calendarPage = new CalendarPage(logic.getCalendarLogic());
        achievementsPage = new AchievementsPage(logic.getAchievementsLogic());
        addressBookPage = new AddressBookPage(logic.getAddressBookLogic());
        diaryPage = new DiaryPage(logic.getDiaryLogic());
        financialTrackerPage = new FinancialTrackerPage(logic.getFinancialTrackerLogic());
        itineraryPage = new ItineraryPage(logic.getItineraryLogic());
        mainPage = new MainPage(logic.getMainLogic());

        commonScene.setRoot(mainPage.getParent());
        mainPage.setBackgroundImage();

        PageManager.getInstance(primaryStage, commonScene, logic, calendarPage, itineraryPage,
                financialTrackerPage, diaryPage, achievementsPage, addressBookPage, mainPage);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
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
        PageManager.closeWindows();
    }
}
