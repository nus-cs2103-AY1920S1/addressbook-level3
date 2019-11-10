package io.xpire.ui;

import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.xpire.commons.core.LogsCenter;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Panel containing the list of items.
 */
public class ViewPanel extends UiPart<VBox> {
    private static final String FXML = "ViewPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ViewPanel.class);

    @FXML
    private VBox card;
    @FXML
    private Label view;

    public ViewPanel(ObservableList<? extends Item> xpireItemList) {
        super(FXML);
        displayItems(xpireItemList);
    }


    /**
     * Renders items in the {@Code xpireItemList}.
     */
    void displayItems(ObservableList<? extends Item> itemList) {
        card.getChildren().clear();
        Collection<ItemCard> cardList;
        //@@author febee99 xiaoyu-nus
        if (!itemList.isEmpty() && itemList.get(0) instanceof XpireItem) {
            cardList = IntStream.range(0, itemList.size())
                                .mapToObj(i -> new ItemCard((XpireItem) itemList.get(i), i + 1))
                                .collect(Collectors.toList());
        } else {
            cardList = IntStream.range(0, itemList.size())
                                .mapToObj(i -> new ItemCard(itemList.get(i), i + 1))
                                .collect(Collectors.toList());
        }
        for (ItemCard itemCard : cardList) {
            card.getChildren().add(itemCard.getRoot());
        }
    }
}
