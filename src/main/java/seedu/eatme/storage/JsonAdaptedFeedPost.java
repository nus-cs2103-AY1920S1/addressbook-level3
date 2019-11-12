package seedu.eatme.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.eatme.commons.exceptions.IllegalValueException;
import seedu.eatme.model.feed.FeedPost;

/**
 * Jackson-friendly version of {@link FeedPost}.
 */
class JsonAdaptedFeedPost {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "FeedPost's %s field is missing!";

    private final String source;
    private final String title;
    private final String address;

    /**
     * Constructs a {@code JsonAdaptedFeedPost} with the given feed post details.
     */
    @JsonCreator
    public JsonAdaptedFeedPost(@JsonProperty("source") String source,
                               @JsonProperty("title") String title,
                               @JsonProperty("address") String address) {
        this.source = source;
        this.title = title;
        this.address = address;
    }

    /**
     * Converts a given {@code FeedPost} into this class for Jackson use.
     */
    public JsonAdaptedFeedPost(FeedPost source) {
        this.source = source.getSource();
        this.title = source.getTitle();
        this.address = source.getAddress();
    }

    /**
     * Converts this Jackson-friendly adapted feed post object into the model's {@code FeedPost} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted feed post.
     */
    public FeedPost toModelType() throws IllegalValueException {
        if (source == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "source"));
        }
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "title"));
        }
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "address"));
        }

        return new FeedPost(source, title, address);
    }

}
