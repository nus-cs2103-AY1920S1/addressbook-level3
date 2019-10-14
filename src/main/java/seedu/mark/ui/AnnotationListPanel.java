package seedu.mark.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.annotation.Paragraph;

public class AnnotationListPanel extends UiPart<Region> {
    private static final String FXML = "AnnotationListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AnnotationListPanel.class);

    @FXML
    private ListView<Paragraph> annotationListView;

    public AnnotationListPanel(ObservableList<Paragraph> paragraphList) {
        super(FXML);
        annotationListView.setItems(paragraphList);
        annotationListView.setCellFactory(listView -> new AnnotationListViewCell());
    }

    class AnnotationListViewCell extends ListCell<Paragraph> {
        @Override
        protected void updateItem(Paragraph paragraph, boolean empty) {
            super.updateItem(paragraph, empty);

            if (empty || paragraph == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AnnotationParagraphCard(paragraph).getRoot());
            }
        }
    }
}
