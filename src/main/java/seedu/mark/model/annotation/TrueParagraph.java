package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import seedu.mark.commons.core.index.Index;

/**
 * Represents a paragraph that exists in the cache.
 * TODO: make paragraphs immutable (so observable list does not break)
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

        ParagraphIdentifier pi = new ParagraphIdentifier(id, ParagraphIdentifier.ParagraphType.EXIST);
        this.id = pi;
        this.content = content;

        annotation = null;
    }

    /**
     * Adds an annotation.
     */
    @Override
    public void addAnnotation(Annotation an) {
        requireNonNull(an);
        this.annotation = an;
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
        return annotation != null;
    }

    @Override
    public Highlight getHighlight() {
        return annotation.getHighlight();
    }

    @Override
    public boolean hasNote() {
        return annotation != null && annotation.hasNote();
    }

    @Override
    public AnnotationNote getNote() {
        return annotation.getNote();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof TrueParagraph)) {
            return false;
        } else {
            return this.id.equals(((TrueParagraph) o).id)
                    && this.content.equals(((TrueParagraph) o).content)
                    && this.annotation.equals(((TrueParagraph) o).annotation);
        }
    }
}
