package seedu.mark.storage;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.model.annotation.Paragraph;

public class JsonAdaptedParagraph {

    private final String pid;
    private final String content;
    private final JsonAdaptedAnnotation annotation;

    /**
     * Constructs a {@code JsonAdaptedParagraph} with the given paragraph details.
     */
    @JsonCreator
    public JsonAdaptedParagraph(@JsonProperty("pid") String pid,
                                @JsonProperty("content") String content,
                                @JsonProperty("annotation") JsonAdaptedAnnotation annotation) {
        this.pid = pid;
        this.content = content;
        this.annotation = annotation;
    }

    /**
     * Converts a given {@code Paragraph} into this class for Jackson use.
     */
    public JsonAdaptedParagraph(Paragraph p) {
        this.pid = p.getId().toString();
        this.content = p.getParagraphContent().toString();
        this.annotation = new JsonAdaptedAnnotation(p.getAnnotation()); //note: can be totally null or note null
    }

    /**
     * Converts this Jackson-friendly adapted paragraph object into the model's {@code Paragraph} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted paragraph.
     */
    public Paragraph toModelType() throws IllegalValueException {
        //TODO
        return null;
    }

}
