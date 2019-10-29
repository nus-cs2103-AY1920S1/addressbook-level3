package seedu.address.ui;

import javafx.collections.ObservableList;
import seedu.address.model.card.ExpiringCard;

/**
 * Panel containing the list of expiring cards.
 */
public class ExpiringCardListPanel extends CardListPanel {

    public ExpiringCardListPanel(ObservableList<ExpiringCard> cardList) {
        super(cardList, ExpiringCardCard.class, ExpiringCard.class);
    }
}
