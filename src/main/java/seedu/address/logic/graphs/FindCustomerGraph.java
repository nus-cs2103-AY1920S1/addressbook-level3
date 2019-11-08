package seedu.address.logic.graphs;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.logic.AutoCompleteResult;
import seedu.address.logic.Graph;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

public class FindCustomerGraph extends Graph {

    private List<String> contactNumberList;
    private List<String> emailList;

    public FindCustomerGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Customer> customerList = model.getCustomerBook().getList();
        contactNumberList = customerList.stream()
                .map(customer -> customer.getContactNumber().toString())
                .collect(Collectors.toList());
        emailList = customerList.stream()
                .map(customer -> customer.getEmail().toString())
                .collect(Collectors.toList());
    }

    @Override
    protected AutoCompleteResult process(String input) {
        SortedSet<String> values = new TreeSet<>();
        values.addAll(contactNumberList);
        values.addAll(emailList);
        String stringToCompare;
        if (input.endsWith(" ")) {
            stringToCompare = "";
        } else {
            stringToCompare = input.substring(input.lastIndexOf(" ") + 1);
        }
        return new AutoCompleteResult(values, stringToCompare);
    }

}
