package seedu.ifridge.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.ifridge.commons.core.LogsCenter;
import seedu.ifridge.model.food.UniqueTemplateItems;


/**
 * Panel containing the list of template items.
 */
public class TemplateListPanel extends UiPart<Region> {
    private static final String FXML = "TemplateListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TemplateListPanel.class);

    @FXML
    private ListView<UniqueTemplateItems> templateListView;

    public TemplateListPanel(ObservableList<UniqueTemplateItems> templateList) {
        super(FXML);
        templateListView.setItems(templateList);
        templateListView.setCellFactory(listView -> new TemplateListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Template} using a {@code TemplateListCard}.
     */
    class TemplateListViewCell extends ListCell<UniqueTemplateItems> {
        @Override
        protected void updateItem(UniqueTemplateItems template, boolean empty) {
            super.updateItem(template, empty);

            if (empty || template == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TemplateListCard(template, getIndex() + 1).getRoot());
            }
        }
    }

}
