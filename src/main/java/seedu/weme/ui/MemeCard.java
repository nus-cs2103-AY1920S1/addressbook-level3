package seedu.weme.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.weme.model.meme.Meme;

/**
 * An UI component that displays information of a {@code Meme}.
 */
public class MemeCard extends UiPart<Region> {

    private static final String FXML = "MemeGridCard.fxml";
    private static final String TAG_TRUNCATE_TEXT = "...";
    private static final int IMAGE_MAX_HEIGHT = 200;
    private static final int IMAGE_MAX_WIDTH = 200;
    private static final int TAGS_HEIGHT = 25;
    private static final int TAGS_GAP_BY_CHAR = 2;
    private static final int MAX_CHAR_PER_LINE = 35;
    // a line can accommodate about 38 characters. In the case of a long string of tags followed by \"...\" case,
    // we use 35 characters per line for display to prevent overflow.

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Weme level 4</a>
     */

    public final Meme meme;

    @FXML
    private HBox cardPane;
    @FXML
    private ImageView display;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private FlowPane tags;
    @FXML
    private HBox likeBox;
    @FXML
    private Label likes;
    @FXML
    private Label dislikes;

    public MemeCard(Meme meme,
                    int displayedIndex,
                    SimpleIntegerProperty numOfLikes,
                    SimpleIntegerProperty numOfDislikes) {
        super(FXML);
        this.meme = meme;
        id.setText(displayedIndex + "");
        Image image = new Image(meme.getImagePath().toUrl().toString(), IMAGE_MAX_WIDTH, IMAGE_MAX_HEIGHT,
                true, true, true);
        int limit = getTagLimit(meme);
        display.setImage(image);
        description.setText(meme.getDescription().value);
        meme.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .limit(limit)
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        if (limit < meme.getTags().size()) {
            tags.getChildren().add(new Label(TAG_TRUNCATE_TEXT));
        }
        likes.setText(" " + numOfLikes.get() + " ");
        dislikes.setText(" " + numOfDislikes.get() + " ");
        numOfLikes.addListener((observable, oldValue, newValue) ->
                likes.setText(Integer.toString((int) newValue)));
        numOfDislikes.addListener((observable, oldValue, newValue) ->
                dislikes.setText(Integer.toString((int) newValue)));
    }

    /**
     * Updates the card content except for the meme image.
     *
     * @param meme     the meme this card is for
     * @param newIndex the new index of the this card
     */
    public void update(Meme meme, int newIndex, SimpleIntegerProperty numOfLikes, SimpleIntegerProperty numOfDislikes) {
        id.setText(newIndex + "");
        description.setText(meme.getDescription().value);
        tags.getChildren().clear();
        int limit = getTagLimit(meme);
        meme.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .limit(limit)
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        if (limit < meme.getTags().size()) {
            Label truncatedText = new Label(TAG_TRUNCATE_TEXT);
            truncatedText.setStyle("-fx-background-color: transparent");
            tags.getChildren().add(truncatedText);
        }
        likes.setText(" " + numOfLikes.get() + " ");
        dislikes.setText(" " + numOfDislikes.get() + " ");
        numOfLikes.addListener((observable, oldValue, newValue) ->
                likes.setText(Integer.toString((int) newValue)));
        numOfDislikes.addListener((observable, oldValue, newValue) ->
                dislikes.setText(Integer.toString((int) newValue)));
    }

    /**
     * Returns the limit on the number of tags a meme card can contain such that there is no overflow of content.
     */
    private int getTagLimit(Meme meme) {
        Image imageCopy = new Image(meme.getImagePath().toUrl().toString());
        int limit = 0;

        // get the number of rows for tag display.
        double height = imageCopy.getHeight();
        double width = imageCopy.getWidth();
        double aspectRatio = height / width;
        double imageHeight = height > width ? IMAGE_MAX_HEIGHT : aspectRatio * IMAGE_MAX_HEIGHT;
        int rowsForTags = 1 + (int) Math.round(Math.floor((IMAGE_MAX_HEIGHT - imageHeight) / TAGS_HEIGHT));

        // get the lengths of the tags for a meme.
        List<Integer> lengths = meme.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .map(tag -> tag.getTagName().length())
                .collect(Collectors.toCollection(ArrayList::new));

        // calculate the number of tags that can fit into the FlowPane.
        int numOfCharInCurrLine = 0;
        int row = 1;
        for (int i = 0; i < meme.getTags().size(); i++) {
            numOfCharInCurrLine += lengths.get(i) + TAGS_GAP_BY_CHAR;
            if (numOfCharInCurrLine > MAX_CHAR_PER_LINE) {
                if (++row > rowsForTags) {
                    break;
                }
                numOfCharInCurrLine = 0;
            }
            limit++;
        }
        return limit;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MemeCard)) {
            return false;
        }

        // state check
        MemeCard card = (MemeCard) other;
        return id.getText().equals(card.id.getText())
                && meme.equals(card.meme)
                && likes.equals(card.likes)
                && dislikes.equals(card.dislikes);
    }
}
