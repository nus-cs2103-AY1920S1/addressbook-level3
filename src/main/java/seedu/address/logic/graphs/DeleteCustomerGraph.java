package seedu.address.logic.graphs;

import java.util.List;

import seedu.address.logic.GraphWithPreamble;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

public class DeleteCustomerGraph extends GraphWithPreamble {

    public DeleteCustomerGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Customer> customerList = model.getFilteredCustomerList();
        setDataList(customerList);
    }

}
