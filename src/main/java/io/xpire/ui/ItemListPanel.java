package io.xpire.ui;

import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;
import io.xpire.model.item.XpireItem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of items.
 */
public class ItemListPanel extends UiPart<Region> {
    private static final String FXML = "ItemListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ItemListPanel.class);

    @FXML
    private ListView<XpireItem> itemListView;

    public ItemListPanel(ObservableList<XpireItem> xpireItemList) {
        super(FXML);
        itemListView.setItems(xpireItemList);
        itemListView.setCellFactory(listView -> new ItemListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code XpireItem} using a {@code ItemCard}.
     */
    class ItemListViewCell extends ListCell<XpireItem> {
        @Override
        protected void updateItem(XpireItem xpireItem, boolean empty) {
            super.updateItem(xpireItem, empty);

            if (empty || xpireItem == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ItemCard(xpireItem, getIndex() + 1).getRoot());
            }
        }
    }

}
