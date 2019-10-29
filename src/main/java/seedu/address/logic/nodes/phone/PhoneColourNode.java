package seedu.address.logic.nodes.phone;

import seedu.address.model.phone.Phone;
import seedu.address.logic.Node;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class PhoneColourNode extends Node<Phone> {

    public PhoneColourNode(List<Phone> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(phone -> values.add(phone.getColour().toString()));
        return values;
    }

}