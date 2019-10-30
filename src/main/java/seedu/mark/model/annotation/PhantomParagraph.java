package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.mark.commons.core.LogsCenter;
import seedu.mark.commons.core.index.Index;

/**
 * Represents a paragraph that does not exist in the original website.
 * This paragraph adopts stray notes for the time that they remain stray.
 * Any highlight of a phantom paragraph is not reflected in Gui.
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

        logger.info("New phantom is created with index " + id.getOneBased());
        ParagraphIdentifier pi = ParagraphIdentifier.makeStrayId(id);
        this.id = pi;
        this.note = note;
    }

    private PhantomParagraph(ParagraphIdentifier id, Annotation an) {
        this.id = id;
        this.note = an;
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
        assert note != null : "Phantom has no annotations...";
        return true;
    }

    @Override
    public Annotation getAnnotation() {
        //assert false : "Phantom getAnnotation() shouldn't be called.";
        if (note == null) {
            logger.log(Level.SEVERE, "PHANTOM PARA HAS NO NOTES???");
        }
        return note;
    }

    @Override
    public Highlight getHighlight() {
        return null;
    }

    @Override
    public boolean hasNote() {
        assert note != null && note.hasNote() : "Phantom does not have note...";
        return true;
    }

    @Override
    public AnnotationNote getNote() {
        assert note != null : "no annotations?";
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

    /**
     * Returns a new reference to the copy of the current structure.
     */
    @Override
    public Paragraph copy() {
        return new PhantomParagraph(id.copy(), note.copy());
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
