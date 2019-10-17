package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import seedu.mark.commons.core.index.Index;

/**
 * Represents a paragraph that exists in the cache.
 * TODO: make paragraphs immutable (so observable list does not break)
 */
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

        colour = null;
        note = null;
    }

    /**
     * Adds an annotation with only the highlight.
     * @param colour The higlight to colour the paragraph
     */
    public void addAnnotation(Highlight colour) {
        requireNonNull(colour);
        this.colour = colour;
    }

    /**
     * Adds an annotation with both highlight and note.
     * @param colour The highlight to colour the paragraph
     * @param note The note to annotate the paragraph with
     */
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
    public boolean hasHighlight() {
        return colour != null;
    }

    @Override
    public Highlight getHighlight() {
        return this.colour;
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
