package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.food.TemplateItem;


/**
 * Panel containing the list of template items.
 */
public class TemplateListPanel extends UiPart<Region> {
    private static final String FXML = "TemplateListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TemplateListPanel.class);

    @FXML
    private ListView<TemplateItem> templateListView;

    public TemplateListPanel(ObservableList<TemplateItem> templateItemList) {
        super(FXML);
        templateListView.setItems(templateItemList);
        templateListView.setCellFactory(listView -> new TemplateListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TemplateItem} using a {@code TemplateItemCard}.
     */
    class TemplateListViewCell extends ListCell<TemplateItem> {
        @Override
        protected void updateItem(TemplateItem templateItem, boolean empty) {
            super.updateItem(templateItem, empty);

            if (empty || templateItem == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TemplateItemCard(templateItem, getIndex() + 1).getRoot());
            }
        }
    }

}
