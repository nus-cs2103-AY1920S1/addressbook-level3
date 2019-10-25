package seedu.mark.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.annotation.Annotation;
import seedu.mark.model.annotation.AnnotationNote;
import seedu.mark.model.annotation.Highlight;

/**
 * Jackson-friendly version of {@link Annotation}.
 */
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
        if (an == null) {
            this.highlight = null;
            this.note = null;
        } else {
            this.highlight = an.getHighlight().toString();
            if (an.hasNote()) {
                this.note = an.getNote().toString();
            } else {
                this.note = null;
            }
        }
    }

    public boolean isNonExistent() {
        return highlight == null;
    }

    /**
     * Converts this Jackson-friendly adapted annotation object into the model's {@code Annotation} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted paragraph.
     */
    public Annotation toModelType() throws IllegalValueException {
        if (highlight == null) {
            return null; //paragraph has no annotations
        }

        Highlight h;
        try {
            h = Highlight.strToHighlight(highlight);
        } catch (ParseException e) {
            throw new IllegalValueException(e.getMessage());
        }
        AnnotationNote n;
        Annotation an;
        try {
            n = AnnotationNote.makeNote(this.note);
            an = new Annotation(h, n);
        } catch (Exception e) {
            an = new Annotation(h);
        }
        return an;
    }
}
