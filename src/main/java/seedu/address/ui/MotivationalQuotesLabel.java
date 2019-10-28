package seedu.address.ui;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * A ui for a single ImageView used to contain a tile representing the user's achievements
 */
public class MotivationalQuotesLabel extends UiPart<Region> {

    private static final String FXML = "MotivationalQuotesLabel.fxml";

    @FXML
    private Label motivationalQuotesLabel;

    public MotivationalQuotesLabel(List<String> motivationalQuotesList) {
        super(FXML);
        motivationalQuotesLabel.setText("Never continue in a job you don’t enjoy. If you’re happy in what you’re "
                + "doing, you’ll like yourself, you’ll have inner peace. And if you have that, along with physical "
                + "health, you will have had more success than you could possibly have imagined. -Johnny Carson");
        //        motivationalQuotesLabel.setText(getRandomElement(motivationalQuotesList));
    }

    public static <T> T getRandomElement(List<T> list) {
        int randomIndex = (int) (list.size() * Math.random());
        return list.get(randomIndex);
    }

}
