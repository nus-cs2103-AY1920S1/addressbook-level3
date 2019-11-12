package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;

/**
 * An UI component that displays information of a {@code Card}.
 */
public class CardCard extends UiPart<Region> {

    private static final String FXML = "CardListCard.fxml";

    public final Card c;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label description;
    @FXML
    private Label cardNumber;
    @FXML
    private FlowPane tags;

    public CardCard(Card card, int displayedIndex) {
        super(FXML);
        this.c = card;
        id.setText(displayedIndex + ". ");
        description.setText(card.getDescription().value);
        cardNumber.setText(card.getCardNumber().toString());
        c.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        Label l = null;
        if (c.hasExpired()) {
            l = new Label("expired");
            l.setStyle("-fx-background-color: #dc143c");
        } else if (c.isExpiring()) {
            l = new Label("expiring");
            l.setStyle("-fx-background-color: #ce8500");
        }

        if (l != null) {
            tags.getChildren().add(l);
        }
    }

    @Override
    public Region getRoot() {
        return super.getRoot();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CardCard)) {
            return false;
        }

        // state check
        CardCard card = (CardCard) other;
        return id.getText().equals(card.id.getText())
                && c.equals(card.c);
    }
}
