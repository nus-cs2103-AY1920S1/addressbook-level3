package seedu.mark.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.collections.FXCollections;
import seedu.mark.model.autotag.AutotagController;

/**
 * Jackson-friendly version of {@link AutotagController}.
 */
public class JsonAdaptedAutotagController {

    private List<JsonAdaptedSelectiveBookmarkTagger> taggers = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedAutotagController} with the given taggers.
     *
     * @param taggers List of {@code SelectiveBookmarkTagger}s.
     */
    @JsonCreator
    public JsonAdaptedAutotagController(@JsonProperty("taggers") List<JsonAdaptedSelectiveBookmarkTagger> taggers) {
        if (taggers != null) {
            this.taggers.addAll(taggers);
        }
    }

    /**
     * Instantiates a new Json adapted autotag controller.
     *
     * @param source the source
     */
    public JsonAdaptedAutotagController(AutotagController source) {
        this.taggers = source.getTaggers().stream()
                .map(JsonAdaptedSelectiveBookmarkTagger::new).collect(Collectors.toList());
    }

    /**
     * Converts this Jackson-friendly adapted autotag controller object
     * into the model's {@code AutotagController} object.
     */
    public AutotagController toModelType() {
        return new AutotagController(
                taggers.stream().map(JsonAdaptedSelectiveBookmarkTagger::toModelType)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList)));
    }

    @Override
    public String toString() {
        return taggers.toString();
    }
}
