package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;

/**
* A Ui for the displaying password analysis report that is displayed when read command is called.
*/
public class ReadDisplayPasswordReport extends UiPart<Region> {
    private static final String FXML = "ReadDisplayPasswordReport.fxml";

    @FXML
    private TextArea resultDisplay;

    public ReadDisplayPasswordReport() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        Font f = Font.loadFont(getClass().getResource("/fonts/COURIER.TTF").toExternalForm(), 10);
        resultDisplay.setFont(f);
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }
}
