package seedu.address.logic.autocomplete.providers.simpleproviders;

import seedu.address.logic.autocomplete.providers.SimpleProvider;
import seedu.address.model.Model;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code CompleteOrderCommand}.
 */
public class CompleteOrderProvider extends SimpleProvider {

    public CompleteOrderProvider(Model model) {
        super(model.getFilteredOrderList());
    }

}
