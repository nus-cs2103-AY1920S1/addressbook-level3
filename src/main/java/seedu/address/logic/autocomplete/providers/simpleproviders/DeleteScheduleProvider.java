package seedu.address.logic.autocomplete.providers.simpleproviders;

import seedu.address.logic.autocomplete.providers.SimpleProvider;
import seedu.address.model.Model;

/**
 * Represents a {@code Provider} used to support autocomplete for {@code DeleteScheduleCommand}.
 */
public class DeleteScheduleProvider extends SimpleProvider {

    public DeleteScheduleProvider(Model model) {
        super(model.getFilteredScheduleList());
    }

}
