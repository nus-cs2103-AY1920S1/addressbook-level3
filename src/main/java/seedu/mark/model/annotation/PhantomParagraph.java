package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import seedu.mark.commons.core.index.Index;

public class PhantomParagraph implements Paragraph {

    /** Paragraph identifier.*/
    private ParagraphIdentifier id;
    /** Paragraph notes, if any.*/
    private AnnotationNote note;

    public PhantomParagraph(Index id) {
        requireNonNull(id);

        ParagraphIdentifier pi = new ParagraphIdentifier(id, ParagraphIdentifier.ParagraphType.STRAY);
        this.id = pi;
    }

    public void addAnnotation(Highlight colour) {
        return;
    }

    public void addAnnotation(Highlight colour, AnnotationNote note) {
        return;
    }

    @Override
    public ParagraphIdentifier getId() {
        return this.id;
    }

    @Override
    public ParagraphContent getParagraphContent() {
        return new ParagraphContent("");
    }

    @Override
    public Highlight getHighlight() {
        return null;
    }

    @Override
    public AnnotationNote getNote() {
        return this.note;
    }
}
