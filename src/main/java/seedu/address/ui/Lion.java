package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class Lion extends UiPart<Region> {

    @FXML
    private Label response;

    private static final String FXML = "Lion.fxml";

    public Lion () {
        super(FXML);
    }

    public void setResponse(String response) {
        this.response.setText(response);
    }
}
