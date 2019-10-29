package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.performance.Record;
import seedu.address.model.person.Person;

public class AthleteRecords extends UiPart<Region> {

    private static final String FXML = "AthleteRecords.fxml";

    @FXML
    private Label athleteName;

    public AthleteRecords(Person athlete, List<Record> records) {
        super(FXML);
        this.athleteName.setText(athlete.getName().fullName);
    }
}
