package seedu.address.logic.nodes.phone;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.Node;
import seedu.address.model.phone.Phone;

/**
 * Represents a {@code Node} tracking {@code Phone} {@code IdentityNumber} for autocompletion.
 */
public class PhoneIdentityNumberNode extends Node<Phone> {

    public PhoneIdentityNumberNode(List<Phone> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(phone -> values.add(phone.getIdentityNumber().toString()));
        return values;
    }

}
