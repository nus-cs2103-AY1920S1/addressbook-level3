package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.model.card.Card;

/**
 * A Ui for the displaying password that is displayed when read command is called.
 */
public class ReadDisplayCard extends UiPart<Region> {
    private static final String FXML = "ReadDisplayCard.fxml";
    private Logic logic;

    @FXML
    private Label description;
    @FXML
    private TextArea cardNumber;
    @FXML
    private TextArea cvc;
    @FXML
    private TextArea expiryDate;
    @FXML
    private FlowPane tags;
    @FXML
    private TextArea cardType;

    public ReadDisplayCard() {
        super(FXML);
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    /**
     *
     * @param card
     */
    public void setFeedbackToUser(Card card) {
        requireNonNull(card);
        description.setText(card.getDescription().value);
        cardNumber.setText(card.getNonEncryptedCardNumber());
        cvc.setText(card.getNonEncryptedCvc());
        expiryDate.setText(card.getExpiryDate().value);
        cardType.setText(card.getCardNumber().value.charAt(0) == '4' ? "Visa" : "MasterCard");
        card.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
