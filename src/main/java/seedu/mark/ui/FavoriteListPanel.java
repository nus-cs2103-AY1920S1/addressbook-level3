package seedu.mark.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Url;

public class FavoriteListPanel extends UiPart<Region> {

    private static final String FXML = "FavoriteListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @javafx.fxml.FXML
    private FlowPane favoriteBookmarkList;

    private Consumer<Url> currentUrlChangeHandler;

    public FavoriteListPanel(ObservableList<Bookmark> favoriteBookmarks,
                             Consumer<Url> currentUrlChangeHandler) {
        super(FXML);

        this.currentUrlChangeHandler = currentUrlChangeHandler;

        favoriteBookmarks.forEach(this::setBookmarkLabel);

//        favoriteBookmarks.addListener((observable, oldValue, newValue) -> {
//            favoriteBookmarks.forEach(this::setBookmarkLabel);
//        });
    }

    private void setBookmarkLabel(Bookmark bookmark) {
        Label bookmarkLabel = new Label(bookmark.getName().value);
        bookmarkLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                currentUrlChangeHandler.accept(bookmark.getUrl());
            }
        });
        favoriteBookmarkList.getChildren().add(bookmarkLabel);
    }
}
