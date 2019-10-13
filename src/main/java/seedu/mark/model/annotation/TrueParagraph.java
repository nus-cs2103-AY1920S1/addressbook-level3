package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import seedu.mark.commons.core.index.Index;

public class TrueParagraph implements Paragraph {

    /** Paragraph identifier.*/
    private ParagraphIdentifier id;
    /** Paragraph content. */
    private ParagraphContent content;
    /** Paragraph highlight. */
    private Highlight colour;
    /** Paragraph notes, if any.*/
    private AnnotationNote note;

    public TrueParagraph(Index id, ParagraphContent content) {
        requireNonNull(id);
        requireNonNull(content);

        ParagraphIdentifier pi = new ParagraphIdentifier(id, ParagraphIdentifier.ParagraphType.EXIST);
        this.id = pi;
        this.content = content;
    }

    public void addAnnotation(Highlight colour) {
        requireNonNull(colour);
        this.colour = colour;
    }

    public void addAnnotation(Highlight colour, AnnotationNote note) {
        requireNonNull(note);
        addAnnotation(colour);
        this.note = note;
    }

    @Override
    public ParagraphIdentifier getId() {
        return this.id;
    }

    @Override
    public ParagraphContent getParagraphContent() {
        return this.content;
    }

    @Override
    public Highlight getHighlight() {
        return this.colour;
    }

    @Override
    public AnnotationNote getNote() {
        return this.note;
    }
}
