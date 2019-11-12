package seedu.eatme.ui;

import java.text.SimpleDateFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.eatme.model.eatery.Review;

/**
 * An UI component that displays information of a {@code Review}.
 */
public class ReviewCard extends UiPart<Region> {

    private static final String FXML = "ReviewListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Review review;

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label description;
    @FXML
    private Label cost;
    @FXML
    private Label ratingActive;
    @FXML
    private Label rating;


    public ReviewCard(Review review, int displayedIndex) {
        super(FXML);
        this.review = review;
        date.setText(String.format("%d. %s",
                displayedIndex, new SimpleDateFormat("dd/MM/yyyy").format(review.getDate())));
        description.setText(review.getDescription());

        cost.setText(String.format("$%.2f", review.getCost()));

        ratingActive.setText("★".repeat(review.getRating()));
        rating.setText("★".repeat(5 - review.getRating()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReviewCard)) {
            return false;
        }

        // state check
        ReviewCard card = (ReviewCard) other;
        return date.getText().equals(card.date.getText())
                && review.equals(card.review);
    }
}
