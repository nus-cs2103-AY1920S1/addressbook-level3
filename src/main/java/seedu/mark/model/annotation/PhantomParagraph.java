package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.mark.commons.core.LogsCenter;
import seedu.mark.commons.core.index.Index;

/**
 * Represents a paragraph that does not exist in the original website.
 * This paragraph adopts stray notes for the time that they remain stray.
 */
public class PhantomParagraph extends Paragraph {

    private final Logger logger = LogsCenter.getLogger(PhantomParagraph.class);

    /** Paragraph identifier.*/
    private ParagraphIdentifier id;
    /** Paragraph notes, if any.*/
    private Annotation note;

    public PhantomParagraph(Index id, Annotation note) {
        requireNonNull(id);
        assert note.hasNote() : "You can't create PhantomParagraph without a note.";

        ParagraphIdentifier pi = new ParagraphIdentifier(id, ParagraphIdentifier.ParagraphType.STRAY);
        this.id = pi;
        this.note = note;
    }

    @Override
    public void addAnnotation(Annotation an) {
        assert false : "This method should not be called for phantom paragraphs.";
    }

    @Override
    public AnnotationNote removeNote() {
        logger.info("This phantom paragraph shall be deleted soon.");
        return note.getNote();
    }

    @Override
    public Annotation removeAnnotation() {
        logger.info("This phantom paragraph shall be deleted soon.");
        return note;
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
     * Returns true. The only key part of annotation is a note. (Highlight is ignored)
     */
    @Override
    public boolean hasAnnotation() {
        return true;
    }

    @Override
    public Annotation getAnnotation() {
        assert false : "Phantom getAnnotation() shouldn't be called.";
        return null;
    }

    @Override
    public Highlight getHighlight() {
        return null;
    }

    @Override
    public boolean hasNote() {
        return true;
    }

    @Override
    public AnnotationNote getNote() {
        return this.note.getNote();
    }

    @Override
    public void updateId(ParagraphIdentifier newId) {
        assert newId.isStray() : "PhantomParagraph should never have EXIST id.";
        this.id = newId;
    }

    @Override
    public boolean isTrueParagraph() {
        return false;
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
