package seedu.address.ui;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.ReadOnlyFeedList;
import seedu.address.model.feed.Feed;
import seedu.address.model.feed.FeedPost;

/**
 * Panel containing the list of feed posts.
 */
public class FeedPostListPanel extends UiPart<Region> {
    private static final String FXML = "FeedPostListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(FeedPostListPanel.class);
    private Logic logic;

    @FXML
    private ListView<FeedPost> feedPostListView;

    public FeedPostListPanel(ReadOnlyFeedList feedList, Logic logic) {
        super(FXML);
        this.logic = logic;

        ObservableList<FeedPost> feedPostList = FXCollections.observableArrayList();
        feedPostListView.setItems(feedPostList);
        feedPostListView.setCellFactory(listView -> new FeedPostListViewCell());

        fetchPosts(feedPostList, feedList.getFeedList());

        ObservableList<Feed> observableFeedList = feedList.getFeedList();
        ListChangeListener<Feed> listener = change -> updatePosts(feedPostList, change);
        observableFeedList.addListener(listener);
    }

    /**
     * Detects the changes made in a `ListChangeListener.Change` object and updates the post list accordingly.
     * If feeds are added, trigger a thread to fetch its posts. If feeds are removed, remove all its posts from the
     * feed list.
     * @param feedPostList Observable list of feed posts
     * @param change Object representing a change in the feed list
     */
    private void updatePosts(ObservableList<FeedPost> feedPostList, ListChangeListener.Change<? extends Feed> change) {
        change.next();
        List<Feed> added = change.getAddedSubList().stream().filter(Objects::nonNull).collect(Collectors.toList());
        List<Feed> removed = change.getRemoved().stream().filter(Objects::nonNull).collect(Collectors.toList());

        if (added.size() > 0) {
            fetchPosts(feedPostList, added);
        }
        if (removed.size() > 0) {
            for (Feed f : removed) {
                feedPostList.removeIf(feedPost -> feedPost.getSource().equals(f.getName()));
            }
        }
    }

    /**
     * Fires off a thread that fetches posts from a list of feeds and adds them to the post list.
     * @param feedPostList Observable list of feed posts.
     * @param feedList List of input feeds.
     */
    private void fetchPosts(ObservableList<FeedPost> feedPostList, List<Feed> feedList) {
        Runnable feedPostFetch = () -> {
            for (Feed feed : feedList) {
                Set<FeedPost> feedPosts = feed.fetchPosts();
                this.logic.saveFeedList();

                Platform.runLater(() -> {
                    feedPostList.addAll(feedPosts);
                });
            }
        };

        new Thread(feedPostFetch).start();
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
