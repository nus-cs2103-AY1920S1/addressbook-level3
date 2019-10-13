package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

import seedu.mark.commons.core.index.Index;

public interface Paragraph {

    public ParagraphIdentifier getId();

    public ParagraphContent getParagraphContent();

    public Highlight getHighlight();

    public AnnotationNote getNote();

    public void addAnnotation(Highlight colour);
    public void addAnnotation(Highlight colour, AnnotationNote note);

}
