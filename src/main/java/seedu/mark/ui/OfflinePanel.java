package seedu.mark.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.mark.model.annotation.Paragraph;

/**
 * The Offline panel of Mark.
 */
public class OfflinePanel extends UiPart<Region> {

    private static final String FXML = "OfflinePanel.fxml";
    private static final String CURRENTLY_SHOWING = "Currently showing offline document of: ";

    private AnnotationListPanel annotationListPanel;

    @FXML
    private StackPane annotationListPanelPlaceholder;
    @FXML
    private Label docLabel;

    public OfflinePanel(ObservableList<Paragraph> paragraphs, ObservableValue<String> docName) {
        //TODO: init any necessary construct
        super(FXML);

        annotationListPanel = new AnnotationListPanel(paragraphs); //TODO: get logic to handle paragraphs
        annotationListPanelPlaceholder.getChildren().add(annotationListPanel.getRoot());

        docLabel.setText(CURRENTLY_SHOWING + docName.getValue());
        docName.addListener(((observable, oldValue, newValue) ->
                docLabel.setText(CURRENTLY_SHOWING + newValue)));

    }

}
