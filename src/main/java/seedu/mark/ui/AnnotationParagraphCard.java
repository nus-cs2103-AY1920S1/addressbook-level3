package seedu.mark.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.mark.model.annotation.Paragraph;

/**
 * An UI component that displays information of a {@code Paragraph}, which includes its annotations.
 */
public class AnnotationParagraphCard extends UiPart<Region> {

    private static final String FXML = "AnnotationParagraphCard.fxml";

    public final Paragraph paragraph;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label para;
    @FXML
    private Label note;

    public AnnotationParagraphCard(Paragraph paragraph) {
        super(FXML);
        this.paragraph = paragraph;

        id.setText(paragraph.getId().toString());
        para.setText(paragraph.getParagraphContent().toString());
        if (paragraph.hasNote()) {
            note.setText(paragraph.getNote().toString());
        }
        //TODO: set para background colour
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (!(other instanceof AnnotationParagraphCard)) {
            return false;
        }

        AnnotationParagraphCard card = (AnnotationParagraphCard) other;
        return id.getText().equals(card.id.getText())
                && para.getText().equals(card.para.getText())
                //TODO: check highlight colour same
                && note.getText().equals(card.note.getText());
    }
}
