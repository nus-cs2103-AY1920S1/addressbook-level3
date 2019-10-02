package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.Logic;
import seedu.address.model.Model;
import seedu.address.ui.itinerary.DaysPage;
import seedu.address.ui.itinerary.EditDayPage;
import seedu.address.ui.itinerary.EditEventPage;
import seedu.address.ui.itinerary.EventsPage;
import seedu.address.ui.trips.EditTripPage;
import seedu.address.ui.trips.TripsPage;
import seedu.address.ui.utility.PreferencesPage;

/**
 * The manager of the UI component.
 */
public class UiManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/dummytrip.jpeg";

    private Logic logic;
    private Stage primaryStage;
    private MainWindow mainWindow;
    private Model model;

    public UiManager(Logic logic, Model model) {
        super();
        this.logic = logic;
        this.model = model;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        this.primaryStage = primaryStage;
        //primaryStage.setMaximized(true);
        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));
        setWindowDefaultSize(model.getGuiSettings());

        try {
            mainWindow = new TripsPage(primaryStage, logic, model);
            primaryStage.show();
        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(primaryStage, type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private static void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add("view/DarkTheme.css");
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    public void switchWindow(Class<? extends MainWindow> mainWindowClass) {
        WindowNavigation navigation = null;
        if (TripsPage.class.equals(mainWindowClass)) {
            navigation = TripsPage::switchTo;
        } else if (EditTripPage.class.equals(mainWindowClass)) {
            navigation = EditTripPage::switchTo;
        } else if (DaysPage.class.equals(mainWindowClass)) {
            navigation = DaysPage::switchTo;
        } else if (EditDayPage.class.equals(mainWindowClass)) {
            navigation = EditDayPage::switchTo;
        } else if (EventsPage.class.equals(mainWindowClass)) {
            navigation = EventsPage::switchTo;
        } else if (EditEventPage.class.equals(mainWindowClass)) {
            navigation = EditEventPage::switchTo;
        } else if (PreferencesPage.class.equals(mainWindowClass)) {
            navigation = PreferencesPage::switchTo;
        }
        if (navigation != null) {
            navigation.switchToThisWindow(primaryStage, logic, model);
        }
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

}
