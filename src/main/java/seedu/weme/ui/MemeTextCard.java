package seedu.weme.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.weme.model.template.MemeText;

/**
 * An UI component that displays information of a {@code MemeText}.
 */
public class MemeTextCard extends UiPart<Region> {
    private static final String FXML = "MemeTextCard.fxml";

    private final MemeText memeText;

    @FXML
    private Label id;
    @FXML
    private Label text;
    @FXML
    private Label position;

    public MemeTextCard(MemeText memeText, int displayedIndex) {
        super(FXML);
        this.memeText = memeText;
        id.setText(displayedIndex + "");
        text.setText(memeText.getText());
        position.setText(memeText.getCoordinates().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MemeTextCard)) {
            return false;
        }

        // state check
        MemeTextCard card = (MemeTextCard) other;
        return id.getText().equals(card.id.getText())
            && memeText.equals(card.memeText);
    }
}
