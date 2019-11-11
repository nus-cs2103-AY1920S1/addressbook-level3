package seedu.address.logic.autocomplete.providers.simpleproviders;

import seedu.address.logic.autocomplete.providers.SimpleProvider;
import seedu.address.model.Model;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code CopyPhoneCommand}.
 */
public class CopyPhoneProvider extends SimpleProvider {

    public CopyPhoneProvider(Model model) {
        super(model.getFilteredPhoneList());
    }

}
