package seedu.address.logic.autocomplete.providers.simpleproviders;

import seedu.address.logic.autocomplete.providers.SimpleProvider;
import seedu.address.model.Model;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code CopyCustomerCommand}.
 */
public class CopyCustomerProvider extends SimpleProvider {

    public CopyCustomerProvider(Model model) {
        super(model.getFilteredCustomerList());
    }

}
