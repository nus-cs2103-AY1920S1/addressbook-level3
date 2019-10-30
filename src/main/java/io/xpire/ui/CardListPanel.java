package io.xpire.ui;

import java.util.ArrayList;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Panel containing the list of items.
 */
public class CardListPanel extends UiPart<AnchorPane> {
    private static final String FXML = "CardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CardListPanel.class);

    private Collection<ItemCard> oldCardList = new ArrayList<>();

    @FXML
    private VBox card;
    @FXML
    private Label view;

    public CardListPanel(ObservableList<? extends Item> xpireItemList) {
        super(FXML);
        displayItem(xpireItemList);
    }

    /**
     * Renders items in the {@Code xpireItemList}.
     */
    void displayItem(ObservableList<? extends Item> itemList) {
        card.getChildren().clear();
        Collection<ItemCard> cardList;
        if (!itemList.isEmpty() && itemList.get(0) instanceof XpireItem) {
            ObservableList<XpireItem> xpireItemList = (ObservableList<XpireItem>) itemList;
            cardList = IntStream.range(0, xpireItemList.size())
                                .mapToObj(i -> new ItemCard(xpireItemList.get(i), i + 1))
                                .collect(Collectors.toList());
        } else {
            cardList = IntStream.range(0, itemList.size())
                                .mapToObj(i -> new ItemCard(itemList.get(i), i + 1))
                                .collect(Collectors.toList());
        }
        for (ItemCard itemCard : cardList) {
            card.getChildren().add(itemCard.getRoot());
        }
        oldCardList = cardList;
    }
}
