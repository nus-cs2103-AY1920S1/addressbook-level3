package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.scene.control.agenda.Agenda;
import seedu.address.commons.core.LogsCenter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class SchedulerPanel extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(SlideshowWindow.class);
    private static final String FXML = "ResultDisplay.fxml";

    private Agenda agenda;
    private ObservableList<Agenda.AppointmentImpl> appointmentList;

    /**
     * Creates SchedulerPanel
     *
     * @param root Stage to use as the root of the SchedulerPanel.
     */
    public SchedulerPanel(Stage root, ObservableList<Agenda.AppointmentImpl> appointmentList) {
        super(FXML);
        agenda = new Agenda();
        agenda.setMouseTransparent(true);
        this.appointmentList = appointmentList;

        agenda.appointments().addAll(appointmentList);
    }

    /**
     * Shows the help window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        getRoot().setScene(new Scene(agenda, super.getRoot().getWidth(), super.getRoot().getHeight()));
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the slideshow window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the slideshow window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
