package seedu.mark.model.annotation;

/**
 * An interface for Paragraphs of offline documents.
 * Paragraphs are to support annotations: highlights and notes.
 */
public interface Paragraph {

    public ParagraphIdentifier getId();

    public ParagraphContent getParagraphContent();

    public boolean hasHighlight();
    public Highlight getHighlight();

    public boolean hasNote();
    public AnnotationNote getNote();

    public void addAnnotation(Highlight colour);
    public void addAnnotation(Highlight colour, AnnotationNote note);

}
