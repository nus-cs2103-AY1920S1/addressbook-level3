package seedu.mark.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Url;

/**
 * Panel containing the list of bookmarks.
 */
public class BookmarkListPanel extends UiPart<Region> {
    private static final String FXML = "BookmarkListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    @org.jetbrains.annotations.NotNull
    private final ObservableValue<Bookmark> bookmarkToDisplayCache;

    @FXML
    private ListView<Bookmark> bookmarkListView;

    public BookmarkListPanel(ObservableList<Bookmark> bookmarkList, ObservableValue<Url> currentBookmarkUrl,
                             ObservableValue<Bookmark> bookmarkToDisplayCache,
                             Consumer<Url> currentUrlChangeHandler) {
        super(FXML);
        this.bookmarkToDisplayCache = bookmarkToDisplayCache;
        bookmarkListView.setItems(bookmarkList);
        bookmarkListView.setCellFactory(listView -> new BookmarkListViewCell());

        // Whenever selection changes, update the current bookmark url
        bookmarkListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            // Do nothing when selection is cleared
            if (newValue == null) {
                return;
            }
            logger.info("Selection in bookmark list panel changed to: " + newValue);
            currentUrlChangeHandler.accept(newValue.getUrl());
        });

        // Whenever current bookmark url changes, update the selection
        currentBookmarkUrl.addListener((observable, oldValue, newValue) -> {
            Bookmark selectedBookmark = bookmarkListView.getSelectionModel().getSelectedItem();
            // Early return if the url change is due to change of selection
            if (selectedBookmark != null && selectedBookmark.getUrl().equals(newValue)) {
                return;
            }
            // Clear the selection when current bookmark url is set to null
            if (newValue == null) {
                bookmarkListView.getSelectionModel().clearSelection();
            } else { // Update the selection to the corresponding bookmark
                int index = 0;
                ObservableList<Bookmark> currentBookmarkList = bookmarkListView.getItems();
                for (Bookmark bookmark : currentBookmarkList) {
                    if (bookmark.getUrl().equals(newValue)) {
                        break;
                    }
                    index++;
                }
                // If the current url does not correspond to any bookmark, clear the selection and return
                if (index == currentBookmarkList.size()) {
                    bookmarkListView.getSelectionModel().clearSelection();
                    return;
                }
                bookmarkListView.scrollTo(index);
                bookmarkListView.getSelectionModel().clearAndSelect(index);
            }
        });

        bookmarkToDisplayCache.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }
            bookmarkListView.refresh();
            bookmarkListView.scrollTo(newValue);
        });
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
                BookmarkCard card = new BookmarkCard(bookmark, getIndex() + 1);
                setGraphic(card.getRoot());
            }
        }
    }

}
