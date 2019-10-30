package seedu.mark.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.mark.commons.exceptions.IllegalValueException;
import seedu.mark.logic.parser.ParserUtil;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.annotation.Annotation;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.annotation.ParagraphContent;
import seedu.mark.model.annotation.ParagraphIdentifier;
import seedu.mark.model.annotation.PhantomParagraph;
import seedu.mark.model.annotation.TrueParagraph;

/**
 * Jackson-friendly version of {@link Paragraph}.
 */
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
        if (content == null) {
            this.content = "";
        } else {
            this.content = content;
        }
        this.annotation = annotation;
    }

    /**
     * Converts a given {@code Paragraph} into this class for Jackson use.
     */
    public JsonAdaptedParagraph(Paragraph p) {
        this.pid = p.getId().toString();
        this.content = p.getParagraphContent().toString();
        if (!p.isTrueParagraph()) {
            // System.out.println(p.getAnnotation());
        }
        this.annotation = new JsonAdaptedAnnotation(p.getAnnotation()); //note: can be totally null or note null
    }

    /**
     * Converts this Jackson-friendly adapted paragraph object into the model's {@code Paragraph} object.
     * @throws IllegalValueException if there were any data constraints violated in the adapted paragraph.
     */
    public Paragraph toModelType() throws IllegalValueException {
        ParagraphIdentifier id;
        try {
            id = ParserUtil.parseParagraphIdentifier(pid);
        } catch (ParseException e) {
            throw new IllegalValueException(e.getMessage());
        }

        Paragraph p;
        Annotation an = annotation.toModelType();
        if (id.isStray()) {
            //TODO: guard against null note (assert -- this should never happen)
            if (an == null) {
                throw new IllegalValueException("No annotation was found in storage for phantom paragraph;\n"
                        + "Phantom disappears.");
            } else if (!an.hasNote()) {
                throw new IllegalValueException("No note was found in storage for phantom paragraph;\n"
                        + "Phantom disappears.");
            }
            p = new PhantomParagraph(id.getIndex(), an);
        } else {
            p = new TrueParagraph(id.getIndex(), new ParagraphContent(content));
            if (an != null) {
                p.addAnnotation(an);
            }
        }
        return p;
    }

}
