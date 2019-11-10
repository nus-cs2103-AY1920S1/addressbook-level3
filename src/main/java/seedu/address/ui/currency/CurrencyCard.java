package seedu.address.ui.currency;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.model.currency.CustomisedCurrency;
import seedu.address.ui.UiPart;


/**
 * A component for displaying the details of a singular {@code CustomisedCurrency}.
 */
public abstract class CurrencyCard extends UiPart<HBox> {

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label rateLabel;
    @FXML
    private VBox propertiesContainer;

    private CustomisedCurrency currency;
    private Index displayedIndex;

    public CurrencyCard(String fxmlFileName, CustomisedCurrency currency, Index displayedIndex) {
        super(fxmlFileName);
        this.currency = currency;
        this.displayedIndex = displayedIndex;
        fillExpenseCardLabels();
    }

    /**
     * Fills the labels of this currency card.
     */
    private void fillExpenseCardLabels() {
        idLabel.setText(displayedIndex.getOneBased() + ".");
        nameLabel.setText(currency.getName().toString() + " (" + currency.getSymbol().toString() + ")");
        rateLabel.setText(String.format("%.2f", currency.getRate().getValue()));
    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CurrencyCard)) {
            return false;
        }

        // state check
        CurrencyCard otherCard = (CurrencyCard) other;
        return currency.equals(otherCard.currency)
                && this.displayedIndex.equals(otherCard.displayedIndex);
    }

    public CustomisedCurrency getCurrency() {
        return currency;
    }
}
