package seedu.address.logic.graphs;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.logic.AutoCompleteResult;
import seedu.address.logic.Graph;
import seedu.address.model.Model;
import seedu.address.model.phone.Phone;

public class FindPhoneGraph extends Graph {

    private List<String> identityNumberList;
    private List<String> serialNumberList;
    private List<String> costList;
    private List<String> brandList;
    private List<String> capacityList;
    private List<String> colourList;
    private List<String> nameList;

    public FindPhoneGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Phone> phoneList = model.getPhoneBook().getList();
        identityNumberList = phoneList.stream()
                .map(phone -> phone.getIdentityNumber().toString())
                .collect(Collectors.toList());
        serialNumberList = phoneList.stream()
                .map(phone -> phone.getSerialNumber().toString())
                .collect(Collectors.toList());
        costList = phoneList.stream()
                .map(phone-> phone.getCost().toString())
                .collect(Collectors.toList());
        brandList = phoneList.stream()
                .map(phone-> phone.getBrand().toString())
                .collect(Collectors.toList());
        capacityList = phoneList.stream()
                .map(phone-> phone.getCapacity().toString())
                .collect(Collectors.toList());
        colourList = phoneList.stream()
                .map(phone-> phone.getColour().toString())
                .collect(Collectors.toList());
        nameList = phoneList.stream()
                .map(phone-> phone.getPhoneName().toString())
                .collect(Collectors.toList());
    }

    @Override
    protected AutoCompleteResult process(String input) {
        SortedSet<String> values = new TreeSet<>();
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
