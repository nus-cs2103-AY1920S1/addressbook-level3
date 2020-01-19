package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import seedu.mark.commons.core.index.Index;

/**
 * Represents a paragraph that exists in the cache.
 */
public class TrueParagraph extends Paragraph {

    /** Paragraph identifier.*/
    private ParagraphIdentifier id;
    /** Paragraph content. */
    private ParagraphContent content;
    /** Annotation for paragraph. */
    private Annotation annotation;

    public TrueParagraph(Index id, ParagraphContent content) {
        requireNonNull(id);
        requireNonNull(content);

        ParagraphIdentifier pi = ParagraphIdentifier.makeExistId(id);
        this.id = pi;
        this.content = content;

        annotation = null;
    }

    private TrueParagraph(ParagraphIdentifier id, ParagraphContent content, Annotation an) {
        this.id = id;
        this.content = content;
        this.annotation = an;
    }

    private TrueParagraph(ParagraphIdentifier id, ParagraphContent content) {
        this.id = id;
        this.content = content;
        this.annotation = null;
    }

    /**
     * Adds an annotation.
     */
    @Override
    public void addAnnotation(Annotation an) {
        requireNonNull(an);
        this.annotation = an;
    }

    /**
     * Removes the annotation note and returns it.
     */
    @Override
    public AnnotationNote removeNote() {
        AnnotationNote note = annotation.getNote();
        annotation.removeNote();
        return note;
    }

    /**
     * Removes the entire annotation and returns it.
     */
    @Override
    public Annotation removeAnnotation() {
        Annotation an = annotation;
        annotation = null;
        return an;
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
    public boolean hasAnnotation() {
        return annotation != null;
    }

    @Override
    public Annotation getAnnotation() {
        return annotation;
    }

    @Override
    public Highlight getHighlight() {
        assert annotation != null : "This paragraph does not have annotations!";
        return annotation.getHighlight();
    }

    @Override
    public boolean hasNote() {
        return annotation != null && annotation.hasNote();
    }

    @Override
    public AnnotationNote getNote() {
        assert hasNote() : "This paragraph does not have notes!";
        return annotation.getNote();
    }

    @Override
    public void updateId(ParagraphIdentifier newId) {
        assert false : "TrueParagraph id should never be updated";
        this.id = newId;
    }

    @Override
    public boolean isTrueParagraph() {
        return true;
    }

    /**
     * Returns a new reference to the copy of the current structure.
     */
    @Override
    public Paragraph copy() {
        if (annotation == null) {
            return new TrueParagraph(id.copy(), content.copy());
        }
        return new TrueParagraph(id.copy(), content.copy(), annotation.copy());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TrueParagraph)) {
            return false;
        }
        return this.id.equals(((TrueParagraph) o).id)
                && this.content.equals(((TrueParagraph) o).content)
                && (this.annotation == ((TrueParagraph) o).annotation
                || this.annotation.equals(((TrueParagraph) o).annotation));
    }
}
