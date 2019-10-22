package seedu.mark.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.model.annotation.Annotation;

public class JsonAdaptedAnnotation {

    private final String highlight;
    private final String note;

    /**
     * Constructs a {@code JsonAdaptedAnnotation} with the given annotation details.
     */
    @JsonCreator
    public JsonAdaptedAnnotation(@JsonProperty("highlight") String h,
                                 @JsonProperty("note") String note) {
        this.highlight = h;
        this.note = note;
    }

    /**
     * Converts a given {@code Annotation} into this class for Jackson use.
     */
    public JsonAdaptedAnnotation(Annotation an) {
        this.highlight = an.getHighlight().toString();
        if (an.hasNote()) {
            this.note = an.getNote().toString();
        } else {
            this.note = null;
        }
    }

    /**
     * Converts this Jackson-friendly adapted annotation object into the model's {@code Annotation} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted paragraph.
     */
    public Annotation toModelType() throws IllegalValueException {
        //TODO
        return null;
    }
}
