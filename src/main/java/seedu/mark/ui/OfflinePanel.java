package seedu.mark.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.annotation.Paragraph;

/**
 * The Offline panel of Mark.
 */
public class OfflinePanel extends UiPart<Region> {

    private static final String FXML = "OfflinePanel.fxml";

    private AnnotationListPanel annotationListPanel;

    @FXML
    private TextField dummyField;
    @FXML
    private StackPane annotationListPanelPlaceholder;

    public OfflinePanel(ObservableList<Paragraph> paragraphs) {
        //TODO: init any necessary construct
        super(FXML);

        annotationListPanel = new AnnotationListPanel(paragraphs); //TODO: get logic to handle paragraphs
        annotationListPanelPlaceholder.getChildren().add(annotationListPanel.getRoot());

        dummyField.setText("This is a dummy offline tab. Please change me /. .\\|||");
    }
}
