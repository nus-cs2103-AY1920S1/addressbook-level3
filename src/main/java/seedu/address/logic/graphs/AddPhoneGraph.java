package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;

import seedu.address.logic.AutoCompleteEdge;
import seedu.address.logic.AutoCompleteNode;
import seedu.address.logic.GraphWithStartNode;
import seedu.address.logic.nodes.EmptyAutoCompleteNode;
import seedu.address.logic.nodes.phone.PhoneBrandNode;
import seedu.address.logic.nodes.phone.PhoneCapacityNode;
import seedu.address.logic.nodes.phone.PhoneColourNode;
import seedu.address.logic.nodes.phone.PhoneCostNode;
import seedu.address.logic.nodes.phone.PhoneIdentityNumberNode;
import seedu.address.logic.nodes.phone.PhoneNameNode;
import seedu.address.logic.nodes.phone.PhoneSerialNumberNode;
import seedu.address.logic.nodes.phone.PhoneTagNode;
import seedu.address.model.Model;
import seedu.address.model.phone.Phone;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code AddPhoneCommand}.
 */
public class AddPhoneGraph extends GraphWithStartNode {

    public AddPhoneGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Phone> phoneList = model.getPhoneBook().getList();
        AutoCompleteNode<?> addPhoneStartNode = EmptyAutoCompleteNode.getInstance();
        setStartingNode(addPhoneStartNode);
        PhoneIdentityNumberNode phoneIdentityNumberNode = new PhoneIdentityNumberNode(phoneList);
        PhoneSerialNumberNode phoneSerialNumberNode = new PhoneSerialNumberNode(phoneList);
        PhoneNameNode phoneNameNode = new PhoneNameNode(phoneList);
        PhoneBrandNode phoneBrandNode = new PhoneBrandNode(phoneList);
        PhoneCapacityNode phoneCapacityNode = new PhoneCapacityNode(phoneList);
        PhoneColourNode phoneColourNode = new PhoneColourNode(phoneList);
        PhoneCostNode phoneCostNode = new PhoneCostNode(phoneList);
        PhoneTagNode phoneTagNode = new PhoneTagNode(phoneList);
        edgeList.addAll(Arrays.asList(
                new AutoCompleteEdge<>(PREFIX_IDENTITY_NUM, addPhoneStartNode, phoneIdentityNumberNode),
                new AutoCompleteEdge<>(PREFIX_SERIAL_NUM, phoneIdentityNumberNode, phoneSerialNumberNode),
                new AutoCompleteEdge<>(PREFIX_PHONE_NAME, phoneSerialNumberNode, phoneNameNode),
                new AutoCompleteEdge<>(PREFIX_BRAND, phoneNameNode, phoneBrandNode),
                new AutoCompleteEdge<>(PREFIX_CAPACITY, phoneBrandNode, phoneCapacityNode),
                new AutoCompleteEdge<>(PREFIX_COLOUR, phoneCapacityNode, phoneColourNode),
                new AutoCompleteEdge<>(PREFIX_COST, phoneColourNode, phoneCostNode),
                new AutoCompleteEdge<>(PREFIX_TAG, phoneCostNode, phoneTagNode),
                new AutoCompleteEdge<>(PREFIX_TAG, phoneTagNode, phoneTagNode)
        ));
    }

}
