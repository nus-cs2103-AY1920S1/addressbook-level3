package seedu.mark.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
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

        // Update current bookmark url when a bookmark is clicked
        bookmarkListView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                setNewUrl(currentUrlChangeHandler);
            }
        });

        // Update current bookmark url when a bookmark is clicked
        bookmarkListView.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                setNewUrl(currentUrlChangeHandler);
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

    private void setNewUrl(Consumer<Url> currentUrlChangeHandler) {
        Bookmark bookmark = bookmarkListView.getSelectionModel().getSelectedItem();
        // Do nothing when selection is cleared
        if (bookmark == null) {
            return;
        }
        logger.info("Selection in bookmark list panel changed to: " + bookmark);
        currentUrlChangeHandler.accept(bookmark.getUrl());
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
