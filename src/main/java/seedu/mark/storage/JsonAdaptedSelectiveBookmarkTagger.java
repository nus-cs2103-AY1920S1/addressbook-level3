package seedu.mark.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.tag.Tag;

/**
 * Jackson-friendly version of {@link SelectiveBookmarkTagger}.
 */
public class JsonAdaptedSelectiveBookmarkTagger {

    private final String tag;
    private final JsonAdaptedBookmarkPredicate predicate;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code conditions}.
     */
    @JsonCreator
    public JsonAdaptedSelectiveBookmarkTagger(@JsonProperty("tag") String tag,
                                              @JsonProperty("predicate") JsonAdaptedBookmarkPredicate predicate) {
        this.tag = tag;
        this.predicate = predicate;
    }

    /**
     * Converts a given {@code SelectiveBookmarkTagger} into this class for Jackson use.
     */
    public JsonAdaptedSelectiveBookmarkTagger(SelectiveBookmarkTagger source) {
        tag = source.getTagToApply().tagName;
        predicate = new JsonAdaptedBookmarkPredicate(source.getPredicate());
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     */
    public SelectiveBookmarkTagger toModelType() {
        // TODO: disallow empty predicates?
        return new SelectiveBookmarkTagger(new Tag(tag), predicate.toModelType());
    }

}
