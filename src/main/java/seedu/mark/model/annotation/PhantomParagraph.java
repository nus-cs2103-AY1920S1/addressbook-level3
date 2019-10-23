package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import seedu.mark.commons.core.index.Index;

/**
 * Represents a paragraph that does not exist in the original website.
 * This paragraph adopts stray notes for the time that they remain stray.
 */
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
        //TODO: phantom paragraph implmentation
    }

    public void addAnnotation(Highlight colour, AnnotationNote note) {
        //TODO:
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
     * Vacuously true; phantom paragraph has empty string as content so highlight does not matter.
     * TODO: rethink bout this
     */
    @Override
    public boolean hasHighlight() {
        return true;
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
}
