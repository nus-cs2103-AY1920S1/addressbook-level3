package seedu.address.logic.nodes.phone;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.Node;
import seedu.address.model.phone.Phone;

/**
 * Represents a {@code Node} tracking {@code Phone} {@code Tag} for autocompletion.
 */
public class PhoneTagNode extends Node<Phone> {

    public PhoneTagNode(List<Phone> backingList) {
        super(backingList);
    }

    @Override
    public SortedSet<String> getValues() {
        SortedSet<String> values = new TreeSet<>();
        backingList.forEach(phone ->
                phone.getTags().forEach(tag ->
                        values.add(tag.toString().replaceAll("\\[|\\]", ""))));
        return values;
    }

}
