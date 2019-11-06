package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * The Ui component for SelectCommand that displays the performance of the selected person. The performance include
 */
public class PersonPerformanceDisplay extends UiPart<Region> {

    private static final String FXML = "PersonPerformanceDisplay.fxml";

    @FXML
    private Label bestPerformance;
    @FXML
    private Label bestPerformanceDate;
    @FXML
    private Label recentPerformanceDate;
    @FXML
    private Label event;
    @FXML
    private Label mostRecent;

    public PersonPerformanceDisplay(String eventName, String bestDate, String performance, String recentDate,
                                    String recent) {
        super(FXML);
        event.setText(eventName);
        bestPerformanceDate.setText(bestDate);
        bestPerformance.setText(performance);
        recentPerformanceDate.setText(recentDate);
        mostRecent.setText(recent);
    }
}
