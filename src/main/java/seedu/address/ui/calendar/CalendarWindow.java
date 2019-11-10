package seedu.address.ui.calendar;

import java.io.IOException;
import java.time.YearMonth;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.ui.UiPart;

//@author dalsontws
/**
 * Controller for a calender page
 */
public class CalendarWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(CalendarWindow.class);
    private static final String FXML = "fullCalendar.fxml";
    @FXML
    private Pane calendarPane;

    private Model model;
    /**
     * Creates a new calendar Window.
     */
    public CalendarWindow(Stage primaryStage) throws IOException {
        super(FXML, primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fullCalendar.fxml"));
        primaryStage.setTitle("KeyboardFlashCards Calendar");
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add("view/DarkTheme.css");
        primaryStage.setScene(scene);

        // Get the controller and add the calendar view to it
        calendarPane.getChildren().add(new FullCalendarView(YearMonth.now(), model).getView());
        primaryStage.show();
    }
    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
