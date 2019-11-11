package seedu.module.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.module.commons.exceptions.IllegalValueException;
import seedu.module.model.module.Link;

/**
 * Jackson-friendly version of {@link Link}.
 */

public class JsonAdaptedLink {
    private final String linkTitle;
    private final String url;
    private final boolean marked;

    /**
     * Constructs a {@code JsonAdaptedLink} with the given parameters.
     */
    @JsonCreator
    public JsonAdaptedLink(@JsonProperty("linkTitle") String linkTitle, @JsonProperty("url") String url,
                           @JsonProperty("marked") boolean marked) {
        this.linkTitle = linkTitle;
        this.url = url;
        this.marked = marked;
    }

    /**
     * Converts a given {@code Link} into this class for Jackson use.
     */
    public JsonAdaptedLink(Link source) {
        this.linkTitle = source.name;
        this.url = source.url;
        this.marked = source.isMarked();
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Link} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Link.
     */
    public Link toModelType() throws IllegalValueException {
        return new Link(linkTitle, url, marked);
    }


}
