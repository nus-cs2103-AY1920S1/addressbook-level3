package seedu.mark.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.mark.model.bookmark.Bookmark;

/**
 * An UI component that displays information of a {@code Bookmark}.
 */
public class BookmarkCard extends UiPart<Region> {

    private static final String FXML = "BookmarkListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Bookmark bookmark;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label url;
    @FXML
    private Label remark;
    @FXML
    private Label folder;
    @FXML
    private FlowPane tags;

    public BookmarkCard(Bookmark bookmark, int displayedIndex) {
        super(FXML);
        this.bookmark = bookmark;
        id.setText(displayedIndex + ". ");
        name.setText(bookmark.getName().value);
        url.setText(bookmark.getUrl().value);
        remark.setText(bookmark.getRemark().value);
        folder.setText(bookmark.getFolder().folderName);
        bookmark.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookmarkCard)) {
            return false;
        }

        // state check
        BookmarkCard card = (BookmarkCard) other;
        return id.getText().equals(card.id.getText())
                && bookmark.equals(card.bookmark);
    }
}
