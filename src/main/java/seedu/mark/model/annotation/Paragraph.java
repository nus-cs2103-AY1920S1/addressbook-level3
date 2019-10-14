package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import seedu.mark.commons.core.index.Index;

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
