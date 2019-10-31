package seedu.sugarmummy.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * A ui for the motivational quotes at the bottom of hte window to display motivational quotes to the user.
 */
public class MotivationalQuotesLabel extends UiPart<Region> {

    private static final String FXML = "MotivationalQuotesLabel.fxml";

    @FXML
    private Label motivationalQuotesLabel;

    public MotivationalQuotesLabel(List<String> motivationalQuotesList, Stage primaryStage) {
        super(FXML);
        motivationalQuotesLabel.setText(getRandomElement(motivationalQuotesList));
        setLabelFont();
    }

    private static <T> T getRandomElement(List<T> list) {
        int randomIndex = (int) (list.size() * Math.random());
        return list.get(randomIndex);
    }

    private void setLabelFont() {
        Font currFont = motivationalQuotesLabel.getFont();
        String fontFamily = currFont.getFamily();
        motivationalQuotesLabel.setStyle("-fx-font-family: 'Arial' !important; -fx-font-style: italic !important; "
                + "-fx-font-size: 15 "
                + "!important");
    }
}
