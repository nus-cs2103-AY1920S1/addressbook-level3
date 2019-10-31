package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
        try {
            // load a custom font from a specific location (change path!)
            // 12 is the size to use
            final Font f = Font.loadFont(new FileInputStream(
                    new File("/SecureIT/src/main/java/seedu/address/ui/COURIER.TTF")), 12);
            resultDisplay.setFont(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }
}
