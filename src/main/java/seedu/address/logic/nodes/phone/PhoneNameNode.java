package seedu.address.logic.nodes.phone;

import seedu.address.model.phone.Phone;
import seedu.address.logic.Node;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class PhoneNameNode extends Node<Phone> {

    public PhoneNameNode(List<Phone> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(phone -> values.add(phone.getPhoneName().toString()));
        return values;
    }

}
