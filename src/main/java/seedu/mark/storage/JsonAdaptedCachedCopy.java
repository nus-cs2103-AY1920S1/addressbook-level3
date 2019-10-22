package seedu.mark.storage;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.bookmark.CachedCopy;

public class JsonAdaptedCachedCopy {

    private final String html;
    //private final List<JsonAdaptedParagraph> offlineDocParagraphs;
    private final JsonAdaptedOfflineDocument offlineDoc;

    /**
     * Constructs a {@code JsonAdaptedCachedCopy} with the given cached copy details.
     */
    @JsonCreator
    public JsonAdaptedCachedCopy(@JsonProperty("html") String html,
                                 @JsonProperty("annotations") JsonAdaptedOfflineDocument doc) {
        this.html = html;
        this.offlineDoc = doc;
    }

    /**
     * Converts a given {@code CachedCopy} into this class for Jackson use.
     */
    public JsonAdaptedCachedCopy(CachedCopy cache) {
        this.html = cache.html;
        this.offlineDoc = new JsonAdaptedOfflineDocument(cache.annotations);
    }


    /**
     * Converts this Jackson-friendly adapted cache object into the model's {@code CachedCopy} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted cache.
     */
    public CachedCopy toModelType() throws IllegalValueException {
        //TODO
        return null;
    }
}
