package seedu.address.ui.itinerary;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import seedu.address.commons.core.index.Index;
import seedu.address.model.itinerary.day.Day;
import seedu.address.ui.UiPart;

public class DayThumbnail extends UiPart<AnchorPane> {
    private static final String FXML = "itinerary/days/DayThumbnail.fxml";

    @FXML
    private Label nameLabel;

    @FXML
    private Label destinationLabel;

    @FXML Label indexLabel;

    private Day day;
    private Index displayedIndex;

    public DayThumbnail(Day day, Index displayedIndex){
        super(FXML);
        this.day = day;
        this.displayedIndex = displayedIndex;
        fillDayThumbnailLabels();
    }

    private void fillDayThumbnailLabels() {
        indexLabel.setText("DAY " + displayedIndex.getOneBased() + "");
        nameLabel.setText(day.getName().toString());
        destinationLabel.setText(day.getDestination().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DayThumbnail)) {
            return false;
        }

        //state check
        DayThumbnail otherThumbnail = (DayThumbnail) other;
        return day.equals(otherThumbnail.day)
                && this.displayedIndex.equals(otherThumbnail.displayedIndex);
    }


}
