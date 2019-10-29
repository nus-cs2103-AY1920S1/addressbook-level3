package seedu.address.ui.currency;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.index.Index;
import seedu.address.model.currency.CustomisedCurrency;


/**
 * TODO: Implement display for inventory and booking labels.
 */
public class SelectedCurrencyCard extends CurrencyCard {
    private static final String FXML = "currency/SelectedCurrencyCard.fxml";

    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label rateLabel;
    @FXML
    private Label heading;
    @FXML
    private VBox propertiesContainer;

    private CustomisedCurrency currency;
    private Index displayedIndex;

    public SelectedCurrencyCard(CustomisedCurrency currency, Index displayedIndex) {
        super(FXML, currency, displayedIndex);
        heading.setText("Currency in use");

    }
}
