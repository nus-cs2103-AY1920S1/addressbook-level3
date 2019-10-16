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
    private final Logger logger = LogsCenter.getLogger(BookmarkListPanel.class);

    @FXML
    private ListView<Bookmark> bookmarkListView;

    public BookmarkListPanel(ObservableList<Bookmark> bookmarkList, ObservableValue<Url> currentBookmarkUrl,
                             Consumer<Url> currentBookmarkUrlChangeHandler, MainWindow mainWindow) {
        super(FXML);
        bookmarkListView.setItems(bookmarkList);
        bookmarkListView.setCellFactory(listView -> new BookmarkListViewCell());

        // Whenever selection changes, update the current bookmark url
        bookmarkListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.info("Selection in bookmark list panel changed to: " + newValue);
            currentBookmarkUrlChangeHandler.accept(newValue.getUrl());
            mainWindow.handleSwitchToOnline();
        });

        // Whenever current bookmark url changes, update the selection
        currentBookmarkUrl.addListener((observable, oldValue, newValue) -> {
            logger.info("Current bookmark url changed to: " + newValue);
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
                bookmarkListView.scrollTo(index);
                bookmarkListView.getSelectionModel().clearAndSelect(index);
            }
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
                setGraphic(new BookmarkCard(bookmark, getIndex() + 1).getRoot());
            }
        }
    }

}
