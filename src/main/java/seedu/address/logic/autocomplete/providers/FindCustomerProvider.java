package seedu.address.logic.autocomplete.providers;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.logic.autocomplete.AutoCompleteResult;
import seedu.address.logic.autocomplete.AutoCompleteResultProvider;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

/**
 * Represents a {@code Provider} used to support autocomplete for {@code FindCustomerCommand}.
 */
public class FindCustomerProvider implements AutoCompleteResultProvider {

    private List<Customer> customerList;

    public FindCustomerProvider(Model model) {
        this.customerList = model.getCustomerBook().getList();
    }

    @Override
    public AutoCompleteResult process(String input) {
        SortedSet<String> values = new TreeSet<>();
        List<String> nameList = customerList.stream()
                .map(customer -> customer.getCustomerName().toString())
                .collect(Collectors.toList());
        List<String> contactNumberList = customerList.stream()
                .map(customer -> customer.getContactNumber().toString())
                .collect(Collectors.toList());
        List<String> emailList = customerList.stream()
                .map(customer -> customer.getEmail().toString())
                .collect(Collectors.toList());
        List<String> tagList = customerList.stream()
                .flatMap(customer -> customer.getTags().stream()
                        .map(tag -> tag.toString().replaceAll("\\[|\\]", "")))
                .collect(Collectors.toList());
        values.addAll(nameList);
        values.addAll(contactNumberList);
        values.addAll(emailList);
        values.addAll(tagList);
        String stringToCompare;
        if (input.endsWith(" ")) {
            stringToCompare = "";
        } else {
            stringToCompare = input.substring(input.lastIndexOf(" ") + 1);
        }
        return new AutoCompleteResult(values, stringToCompare);
    }

}
