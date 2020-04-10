package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.performance.Record;
import seedu.address.model.person.Person;

/**
 * Constructs the athlete's record component outline.
 */
public class AthleteRecords extends UiPart<Region> {

    private static final String FXML = "AthleteRecords.fxml";
    private final Person person;
    private final List<Record> records;

    @FXML
    private Label athleteName;

    @FXML
    private VBox athleteStatBox;

    public AthleteRecords(Person athlete, List<Record> records) {
        super(FXML);
        person = athlete;
        this.records = records;
        this.athleteName.setText(person.getName().fullName);
        getChart();
    }

    /**
     * Retrieves the chart from RecordDetails.
     */
    private void getChart() {
        RecordLineChart recordLineChart = new RecordLineChart(person.getName().fullName, records);
        athleteStatBox.getChildren().add(recordLineChart.getRoot());
    }
}
