package seedu.address.logic.graphs;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.logic.AutoCompleteResult;
import seedu.address.logic.GraphBuiltFromModel;
import seedu.address.model.Model;
import seedu.address.model.phone.Phone;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code FindPhoneCommand}.
 */
public class FindPhoneGraph extends GraphBuiltFromModel {

    private List<Phone> phoneList;

    public FindPhoneGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        phoneList = model.getPhoneBook().getList();
    }

    @Override
    public AutoCompleteResult process(String input) {
        SortedSet<String> values = new TreeSet<>();
        List<String> identityNumberList = phoneList.stream()
                .map(phone -> phone.getIdentityNumber().toString())
                .collect(Collectors.toList());
        List<String> serialNumberList = phoneList.stream()
                .map(phone -> phone.getSerialNumber().toString())
                .collect(Collectors.toList());
        List<String> costList = phoneList.stream()
                .map(phone-> phone.getCost().toString())
                .collect(Collectors.toList());
        List<String> brandList = phoneList.stream()
                .map(phone-> phone.getBrand().toString())
                .collect(Collectors.toList());
        List<String> capacityList = phoneList.stream()
                .map(phone-> phone.getCapacity().toString())
                .collect(Collectors.toList());
        List<String> colourList = phoneList.stream()
                .map(phone-> phone.getColour().toString())
                .collect(Collectors.toList());
        List<String> nameList = phoneList.stream()
                .map(phone-> phone.getPhoneName().toString())
                .collect(Collectors.toList());
        values.addAll(identityNumberList);
        values.addAll(serialNumberList);
        values.addAll(costList);
        values.addAll(brandList);
        values.addAll(colourList);
        values.addAll(nameList);
        values.addAll(capacityList);
        String stringToCompare;
        if (input.endsWith(" ")) {
            stringToCompare = "";
        } else {
            stringToCompare = input.substring(input.lastIndexOf(" ") + 1);
        }
        return new AutoCompleteResult(values, stringToCompare);
    }

}
