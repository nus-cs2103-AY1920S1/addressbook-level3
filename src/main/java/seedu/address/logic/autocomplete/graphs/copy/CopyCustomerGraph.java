package seedu.address.logic.autocomplete.graphs.copy;

import java.util.List;

import seedu.address.logic.autocomplete.graphs.GraphWithPreamble;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code CopyCustomerCommand}.
 */
public class CopyCustomerGraph extends GraphWithPreamble {

    public CopyCustomerGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Customer> customerList = model.getFilteredCustomerList();
        setDataList(customerList);
    }

}
