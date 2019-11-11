package seedu.address.logic.autocomplete.providers.simpleproviders;

import seedu.address.logic.autocomplete.providers.SimpleProvider;
import seedu.address.model.Model;

/**
 * Represents a {@code Provider} used to support autocomplete for {@code DeletePhoneCommand}.
 */
public class DeletePhoneProvider extends SimpleProvider {

    public DeletePhoneProvider(Model model) {
        super(model.getFilteredPhoneList());
    }

}
