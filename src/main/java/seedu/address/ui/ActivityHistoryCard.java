package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.activity.Activity;
import seedu.address.ui.util.UiUtil;

/**
 * An UI component that displays contact-specific information of an {@code Activity}.
 */
public class ActivityHistoryCard extends UiPart<Region> {
    private static final String FXML = "ActivityHistoryCard.fxml";

    private static final String ACTIVITY_OWED_CLASS = "activity-owed";
    private static final String ACTIVITY_OWING_CLASS = "activity-owing";
    private static final String ACTIVITY_NEUTRAL_CLASS = "activity-neutral";

    private final Activity activity;
    private final double transferAmt;

    @FXML
    private Label title;
    @FXML
    private Label netTransfer;

    public ActivityHistoryCard(Activity activity, double transferAmt) {
        super(FXML);
        this.activity = activity;
        this.transferAmt = transferAmt;

        title.setText(activity.getTitle().toString());

        if (transferAmt == 0) {
            netTransfer.getStyleClass().add(ACTIVITY_NEUTRAL_CLASS);
            netTransfer.setText("No outstanding debt to or from this activity.");
        } else if (transferAmt < 0) {
            netTransfer.getStyleClass().add(ACTIVITY_OWING_CLASS);
            netTransfer.setText("Owes " + UiUtil.formatAmount(-transferAmt));
        } else {
            netTransfer.getStyleClass().add(ACTIVITY_OWED_CLASS);
            netTransfer.setText("Owed " + UiUtil.formatAmount(transferAmt));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ActivityHistoryCard)) {
            return false;
        }

        // state check
        ActivityHistoryCard card = (ActivityHistoryCard) other;
        return activity.equals(card.activity)
            && transferAmt == card.transferAmt;
    }
}
