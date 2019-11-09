package seedu.address.logic.graphs;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.Edge;
import seedu.address.logic.GraphWithStartNode;
import seedu.address.logic.Node;
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
        Node<?> addPhoneStartNode = Node.emptyNode();
        setStartingNode(addPhoneStartNode);
        Node<Phone> phoneIdentityNumberNode = new PhoneIdentityNumberNode(phoneList);
        Node<Phone> phoneSerialNumberNode = new PhoneSerialNumberNode(phoneList);
        Node<Phone> phoneNameNode = new PhoneNameNode(phoneList);
        Node<Phone> phoneBrandNode = new PhoneBrandNode(phoneList);
        Node<Phone> phoneCapacityNode = new PhoneCapacityNode(phoneList);
        Node<Phone> phoneColourNode = new PhoneColourNode(phoneList);
        Node<Phone> phoneCostNode = new PhoneCostNode(phoneList);
        Node<Phone> phoneTagNode = new PhoneTagNode(phoneList);
        edges.addAll(
                new Edge(PREFIX_IDENTITY_NUM, addPhoneStartNode, phoneIdentityNumberNode),
                new Edge(PREFIX_SERIAL_NUM, phoneIdentityNumberNode, phoneSerialNumberNode),
                new Edge(PREFIX_PHONE_NAME, phoneSerialNumberNode, phoneNameNode),
                new Edge(PREFIX_BRAND, phoneNameNode, phoneBrandNode),
                new Edge(PREFIX_CAPACITY, phoneBrandNode, phoneCapacityNode),
                new Edge(PREFIX_COLOUR, phoneCapacityNode, phoneColourNode),
                new Edge(PREFIX_COST, phoneColourNode, phoneCostNode),
                new Edge(PREFIX_TAG, phoneCostNode, phoneTagNode),
                new Edge(PREFIX_TAG, phoneTagNode, phoneTagNode)
        );
    }

}
