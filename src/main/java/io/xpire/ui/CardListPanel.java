package io.xpire.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.xpire.commons.core.LogsCenter;
import io.xpire.model.item.Item;
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

    public CardListPanel(ObservableList<Item> itemList) {
        super(FXML);
        displayItem(itemList);
    }

    /**
     * Renders items in the {@Code itemList}.
     */
    void displayItem(ObservableList<Item> itemList) {
        card.getChildren().clear();
        Collection<ItemCard> cardList = IntStream.range(0, itemList.size())
                .mapToObj(i -> new ItemCard(itemList.get(i), i + 1))
                .collect(Collectors.toList());
        for (ItemCard itemCard : cardList) {
            card.getChildren().add(itemCard.getRoot());
        }
        oldCardList = cardList;
    }

}
