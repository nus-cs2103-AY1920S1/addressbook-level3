package seedu.mark.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.bookmark.Bookmark;

/**
 * Panel containing the list of bookmarks.
 */
public class BookmarkListPanel extends UiPart<Region> {
    private static final String FXML = "BookmarkListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(BookmarkListPanel.class);

    @FXML
    private ListView<Bookmark> bookmarkListView;

    public BookmarkListPanel(ObservableList<Bookmark> bookmarkList) {
        super(FXML);
        bookmarkListView.setItems(bookmarkList);
        bookmarkListView.setCellFactory(listView -> new BookmarkListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Bookmark} using a {@code BookmarkCard}.
     */
    class BookmarkListViewCell extends ListCell<Bookmark> {
        @Override
        protected void updateItem(Bookmark bookmark, boolean empty) {
            super.updateItem(bookmark, empty);

            if (empty || bookmark == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BookmarkCard(bookmark, getIndex() + 1).getRoot());
            }
        }
    }

}
