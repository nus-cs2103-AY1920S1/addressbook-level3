package seedu.address.logic.autocomplete.providers.simpleproviders;

import seedu.address.logic.autocomplete.providers.SimpleProvider;
import seedu.address.model.Model;

/**
 * Represents a {@code Provider} used to support autocomplete for {@code CopyOrderCommand}.
 */
public class CopyOrderProvider extends SimpleProvider {

    public CopyOrderProvider(Model model) {
        super(model.getFilteredOrderList());
    }

}
