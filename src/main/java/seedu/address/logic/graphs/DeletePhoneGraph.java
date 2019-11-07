package seedu.address.logic.graphs;

import seedu.address.logic.GraphWithPreamble;
import seedu.address.model.Model;
import seedu.address.model.phone.Phone;

import java.util.List;

public class DeletePhoneGraph extends GraphWithPreamble {

    public DeletePhoneGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Phone> phoneList = model.getFilteredPhoneList();
        setDataList(phoneList);
    }

}
