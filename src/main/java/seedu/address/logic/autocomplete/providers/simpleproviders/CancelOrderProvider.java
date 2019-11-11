package seedu.address.logic.autocomplete.providers.simpleproviders;

import seedu.address.logic.autocomplete.providers.SimpleProvider;
import seedu.address.model.Model;

/**
 * Represents a {@code Provider} used to support autocomplete for {@code CancelOrderCommand}.
 */
public class CancelOrderProvider extends SimpleProvider {

    public CancelOrderProvider(Model model) {
        super(model.getFilteredOrderList());
    }

}
