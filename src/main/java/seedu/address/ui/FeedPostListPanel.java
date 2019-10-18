package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.feed.FeedPost;

/**
 * Panel containing the list of feed posts.
 */
public class FeedPostListPanel extends UiPart<Region> {
    private static final String FXML = "FeedPostListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FeedPostListPanel.class);

    @FXML
    private ListView<FeedPost> feedPostListView;

    public FeedPostListPanel(ObservableList<FeedPost> feedPostList) {
        super(FXML);
        feedPostListView.setItems(feedPostList);
        feedPostListView.setCellFactory(listView -> new FeedPostListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code FeedPost} using a {@code FeedPostCard}.
     */
    class FeedPostListViewCell extends ListCell<FeedPost> {
        @Override
        protected void updateItem(FeedPost feedPost, boolean empty) {
            super.updateItem(feedPost, empty);

            if (empty || feedPost == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new FeedPostCard(feedPost).getRoot());
            }
        }
    }

}
