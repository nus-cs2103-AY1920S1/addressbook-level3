package seedu.weme.ui;

import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.weme.model.template.Template;

/**
 * Panel containing the list of templates.
 */
public class TemplateGridPanel extends UiPart<Region> {
    private static final String FXML = "TemplateGridPanel.fxml";

    @FXML
    private GridView<Template> templateGridView;

    public TemplateGridPanel(ObservableList<Template> templateList) {
        super(FXML);
        templateGridView.setItems(templateList);
        templateGridView.setCellFactory(gridView -> new TemplateGridViewCell());
    }

    /**
     * Custom {@code GridCell} that displays the graphics of a {@code Template} using a {@code TemplateCard}.
     */
    class TemplateGridViewCell extends GridCell<Template> {
        @Override
        protected void updateItem(Template template, boolean empty) {
            super.updateItem(template, empty);

            if (empty || template == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TemplateCard(template, getIndex() + 1).getRoot());
            }
        }
    }

}

