package seedu.address.logic.autocomplete.providers.simpleproviders;

import seedu.address.logic.autocomplete.providers.SimpleProvider;
import seedu.address.model.Model;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code DeleteCustomerCommand}.
 */
public class DeleteCustomerProvider extends SimpleProvider {

    public DeleteCustomerProvider(Model model) {
        super(model.getFilteredCustomerList());
    }

}
