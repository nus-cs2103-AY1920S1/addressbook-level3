package seedu.eatme.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.eatme.commons.exceptions.IllegalValueException;
import seedu.eatme.model.FeedList;
import seedu.eatme.model.ReadOnlyFeedList;
import seedu.eatme.model.feed.Feed;

/**
 * An Immutable FeedList that is serializable to JSON format.
 */
@JsonRootName(value = "feeds")
class JsonSerializableFeedList {

    public static final String MESSAGE_DUPLICATE_FEED = "Feed list contains duplicate feed(s).";

    private final List<JsonAdaptedFeed> feeds = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableFeedList} with the given feeds.
     */
    @JsonCreator
    public JsonSerializableFeedList(@JsonProperty("feeds") List<JsonAdaptedFeed> feeds) {
        this.feeds.addAll(feeds);
    }

    /**
     * Converts a given {@code ReadOnlyFeedList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableFeedList}.
     */
    public JsonSerializableFeedList(ReadOnlyFeedList source) {
        feeds.addAll(source.getFeedList().stream().map(JsonAdaptedFeed::new).collect(Collectors.toList()));
    }

    /**
     * Converts this feed list into the model's {@code FeedList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public FeedList toModelType() throws IllegalValueException {
        FeedList feedList = new FeedList();
        for (JsonAdaptedFeed jsonAdaptedFeed : feeds) {
            Feed feed = jsonAdaptedFeed.toModelType();

            if (feedList.hasFeed(feed)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FEED);
            }
            feedList.addFeed(feed);
        }
        return feedList;
    }
}
