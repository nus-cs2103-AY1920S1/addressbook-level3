package seedu.address.ui;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.Card;

/**
 * Panel containing the list of cards.
 */
public class CardListPanel <T extends Card> extends UiPart<Region> {
    private static final String FXML = "CardListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CardListPanel.class);

    @FXML
    private ListView<T> cardListView;

    public CardListPanel(ObservableList<T> cardList) {
        this(cardList, CardCard.class, Card.class);
    }

    CardListPanel(ObservableList<T> cardList, Class<CardCard> cardCardClass, Class<Card> cardClass) {
        super(FXML);
        cardListView.setItems(cardList);
        cardListView.setCellFactory(listView -> new CardListViewCell(cardCardClass, cardClass));
    }

    @Override
    public Region getRoot() {
        return super.getRoot();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Card} using a {@code CardCard}.
     */
    class CardListViewCell<R extends CardCard> extends ListCell<T> {

        private Constructor<R> constructor;

        CardListViewCell(Class<R> cardCardClass, Class<T> cardClass) {
            try {
                this.constructor = cardCardClass.getConstructor(cardClass, int.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void updateItem(T card, boolean empty) {
            super.updateItem(card, empty);

            if (empty || card == null) {
                setGraphic(null);
                setText(null);
            } else {
                try {
                    setGraphic(constructor.newInstance(card, getIndex() + 1).getRoot());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
