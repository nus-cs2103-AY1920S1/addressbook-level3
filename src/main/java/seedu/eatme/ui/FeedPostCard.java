package seedu.eatme.ui;

import java.awt.Desktop;
import java.net.URI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.eatme.commons.core.LogsCenter;
import seedu.eatme.model.feed.FeedPost;

/**
 * An UI component that displays information of a {@code FeedPost}.
 */
public class FeedPostCard extends UiPart<Region> {

    private static final String FXML = "FeedPostCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final FeedPost feedPost;

    @FXML
    private Label source;
    @FXML
    private Hyperlink link;

    public FeedPostCard(FeedPost feedPost) {
        super(FXML);
        this.feedPost = feedPost;

        source.setText(feedPost.getSource());

        link.setText(feedPost.getTitle());
        link.setOnAction((ActionEvent e) -> {
            try {
                Desktop.getDesktop().browse(new URI(feedPost.getAddress()));
            } catch (Exception ex) {
                LogsCenter.getLogger(FeedPostCard.class).warning("Unable to open browser");
            }
        });
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FeedPostCard)) {
            return false;
        }

        // state check
        FeedPostCard card = (FeedPostCard) other;
        return feedPost.equals(card.feedPost);
    }
}
