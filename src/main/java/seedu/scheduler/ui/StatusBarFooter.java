package seedu.scheduler.ui;

import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the footer of the application.
 */
public class StatusBarFooter extends UiPart<Region> {

    private static final String FXML = "StatusBarFooter.fxml";

    @FXML
    private Label saveLocationStatus;


    public StatusBarFooter(Path intervieweeSaveLocation, Path interviewerSaveLocation) {
        super(FXML);
        StringBuilder sb = new StringBuilder();
        sb.append("Interviewees list: ");
        sb.append(Paths.get(".").resolve(intervieweeSaveLocation));
        sb.append(" | Interviewers list: ");
        sb.append(Paths.get(".").resolve(interviewerSaveLocation));
        saveLocationStatus.setText(sb.toString());
    }

}
