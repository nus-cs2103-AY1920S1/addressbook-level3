package seedu.exercise.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.exercise.model.regime.Regime;

/**
 *  An UI component that displays informatino of a {@code Regime}.
 */
public class RegimeCard extends UiPart<Region> {
    private static final String FXML = "RegimeCard.fxml";

    private final Regime regime;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label exercises;

    public RegimeCard(Regime regime, int displayedIndex) {
        super(FXML);
        this.regime = regime;
        id.setText(displayedIndex + ". ");
        name.setText(regime.getRegimeName().toString());
        exercises.setText(regime.toString());
    }
}
