package seedu.mark.model.annotation;

/**
 * An interface for Paragraphs of offline documents.
 * Paragraphs are to support annotations: highlights and notes.
 */
public abstract class Paragraph {

    public abstract ParagraphIdentifier getId();

    public abstract ParagraphContent getParagraphContent();

    public abstract boolean hasAnnotation();
    public abstract Annotation getAnnotation();
    public abstract Highlight getHighlight();

    public abstract boolean hasNote();
    public abstract AnnotationNote getNote();

    public abstract void addAnnotation(Annotation annotation);
    public abstract AnnotationNote removeNote();
    public abstract Annotation removeAnnotation();

    public abstract void updateId(ParagraphIdentifier newId);
    public abstract boolean isTrueParagraph();

    public abstract Paragraph copy();

}
