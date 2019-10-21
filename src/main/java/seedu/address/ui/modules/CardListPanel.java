package seedu.address.ui.modules;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.Card;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of cards.
 */
public class CardListPanel extends UiPart<Region> {
    private static final String FXML = "CardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CardListPanel.class);

    @FXML
    private ListView<Card> cardListView;

    public CardListPanel(ObservableList<Card> cardList) {
        super(FXML);
        cardListView.setItems(cardList);
        cardListView.setCellFactory(listView -> new CardListPanel.CardListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Card} using a {@code CardCard}.
     */
    class CardListViewCell extends ListCell<Card> {
        @Override
        protected void updateItem(Card card, boolean empty) {
            super.updateItem(card, empty);

            if (empty || card == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CardCard(card, getIndex() + 1).getRoot());
            }
        }
    }
}
