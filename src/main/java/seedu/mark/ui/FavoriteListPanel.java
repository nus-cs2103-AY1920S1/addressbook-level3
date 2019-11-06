package seedu.mark.ui;

import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Url;

/**
 * Panel containing the list of favorite bookmarks
 */
public class FavoriteListPanel extends UiPart<Region> {

    private static final String FXML = "FavoriteListPanel.fxml";

    @FXML
    private FlowPane favoriteBookmarkList;

    private Consumer<Url> currentUrlChangeHandler;
    private ObservableList<Bookmark> favoriteBookmarks;
    private ObservableList<Node> bookmarkItems;

    public FavoriteListPanel(ObservableList<Bookmark> favoriteBookmarks,
                             Consumer<Url> currentUrlChangeHandler) {
        super(FXML);

        this.currentUrlChangeHandler = currentUrlChangeHandler;
        this.favoriteBookmarks = favoriteBookmarks;
        this.bookmarkItems = FXCollections.observableArrayList();

        setBookmarkItems();
        favoriteBookmarkList.getChildren().setAll(bookmarkItems);

        favoriteBookmarks.addListener((ListChangeListener<? super Bookmark>) change -> {
            while (change.next()) {
                setBookmarkItems();
                favoriteBookmarkList.getChildren().setAll(bookmarkItems);
            }
        });
    }

    private void setBookmarkItems() {
        bookmarkItems.clear();
        for (Bookmark bookmark : favoriteBookmarks) {
            Label bookmarkLabel = new Label(bookmark.getName().value);
            bookmarkLabel.setOnMouseClicked(unused -> currentUrlChangeHandler.accept(bookmark.getUrl()));
            bookmarkItems.add(bookmarkLabel);
        }
    }
}
