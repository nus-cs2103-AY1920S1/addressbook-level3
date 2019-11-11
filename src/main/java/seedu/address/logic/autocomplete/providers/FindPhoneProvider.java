package seedu.address.logic.autocomplete.providers;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import seedu.address.logic.autocomplete.AutoCompleteResult;
import seedu.address.logic.autocomplete.AutoCompleteResultProvider;
import seedu.address.model.Model;
import seedu.address.model.phone.Phone;

/**
 * Represents a {@code Provider} used to support autocomplete for {@code FindPhoneCommand}.
 */
public class FindPhoneProvider implements AutoCompleteResultProvider {

    private List<Phone> phoneList;

    public FindPhoneProvider(Model model) {
        this.phoneList = model.getPhoneBook().getList();
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
        List<String> tagList = phoneList.stream()
                .flatMap(phone -> phone.getTags().stream()
                        .map(tag -> tag.toString().replaceAll("\\[|\\]", "")))
                .collect(Collectors.toList());
        values.addAll(identityNumberList);
        values.addAll(serialNumberList);
        values.addAll(costList);
        values.addAll(brandList);
        values.addAll(colourList);
        values.addAll(nameList);
        values.addAll(capacityList);
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
