package seedu.address.logic.autocomplete.graphs.copy;

import java.util.List;

import seedu.address.logic.autocomplete.graphs.GraphWithPreamble;
import seedu.address.model.Model;
import seedu.address.model.phone.Phone;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code CopyPhoneCommand}.
 */
public class CopyPhoneGraph extends GraphWithPreamble {

    public CopyPhoneGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Phone> phoneList = model.getFilteredPhoneList();
        setDataList(phoneList);
    }

}
