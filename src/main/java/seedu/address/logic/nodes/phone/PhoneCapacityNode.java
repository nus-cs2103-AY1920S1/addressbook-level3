package seedu.address.logic.nodes.phone;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.AutoCompleteNode;
import seedu.address.model.phone.Phone;

/**
 * Represents a {@code Node} tracking {@code Phone} {@code Capacity} for autocompletion.
 */
public class PhoneCapacityNode extends AutoCompleteNode<List<Phone>> {

    public PhoneCapacityNode(List<Phone> pointer) {
        super(pointer);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        pointer.forEach(phone -> values.add(phone.getCapacity().toString().replaceAll("GB", "")));
        return values;
    }

}
