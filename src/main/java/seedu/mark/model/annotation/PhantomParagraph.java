package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import seedu.mark.commons.core.index.Index;

/**
 * Represents a paragraph that does not exist in the original website.
 * This paragraph adopts stray notes for the time that they remain stray.
 */
public class PhantomParagraph extends Paragraph {

    /** Paragraph identifier.*/
    private ParagraphIdentifier id;
    /** Paragraph notes, if any.*/
    private AnnotationNote note;

    public PhantomParagraph(Index id, AnnotationNote note) {
        requireNonNull(id);

        ParagraphIdentifier pi = new ParagraphIdentifier(id, ParagraphIdentifier.ParagraphType.STRAY);
        this.id = pi;
        this.note = note;
    }

    public void addAnnotation(Annotation an) {
        assert false : "This method should not be called for phantom paragraphs.";
    }

    @Override
    public ParagraphIdentifier getId() {
        return this.id;
    }

    @Override
    public ParagraphContent getParagraphContent() {
        return new ParagraphContent("");
    }

    /**
     * Returns false. Phantom paragraphs can never have highlights. The only possible annotation is a note.
     */
    @Override
    public boolean hasHighlight() {
        return false;
    }

    @Override
    public Highlight getHighlight() {
        return null;
    }

    @Override
    public boolean hasNote() {
        return note != null;
    }

    @Override
    public AnnotationNote getNote() {
        return this.note;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PhantomParagraph)) {
            return false;
        } else {
            return this.id.equals(((PhantomParagraph) o).id)
                    && this.note.equals(((PhantomParagraph) o).note);
        }
    }
}
