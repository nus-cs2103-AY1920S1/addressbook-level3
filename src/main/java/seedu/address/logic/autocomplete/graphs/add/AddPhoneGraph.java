package seedu.address.logic.autocomplete.graphs.add;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.autocomplete.graphs.Edge;
import seedu.address.logic.autocomplete.graphs.GraphWithStartNode;
import seedu.address.logic.autocomplete.nodes.phone.PhoneBrandNode;
import seedu.address.logic.autocomplete.nodes.phone.PhoneCapacityNode;
import seedu.address.logic.autocomplete.nodes.phone.PhoneColourNode;
import seedu.address.logic.autocomplete.nodes.phone.PhoneCostNode;
import seedu.address.logic.autocomplete.nodes.phone.PhoneIdentityNumberNode;
import seedu.address.logic.autocomplete.nodes.phone.PhoneNameNode;
import seedu.address.logic.autocomplete.nodes.phone.PhoneSerialNumberNode;
import seedu.address.logic.autocomplete.nodes.phone.PhoneTagNode;
import seedu.address.model.Model;
import seedu.address.model.phone.Phone;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code AddPhoneCommand}.
 */
public class AddPhoneGraph extends GraphWithStartNode {

    public AddPhoneGraph(Model model) {
        super();
        initialise(model);
    }

    /**
     * Initialises this graph's {@code Node}s.
     */
    private void initialise(Model model) {
        List<Phone> phoneList = model.getPhoneBook().getList();
        PhoneIdentityNumberNode phoneIdentityNumberNode = new PhoneIdentityNumberNode(phoneList);
        PhoneSerialNumberNode phoneSerialNumberNode = new PhoneSerialNumberNode(phoneList);
        PhoneNameNode phoneNameNode = new PhoneNameNode(phoneList);
        PhoneBrandNode phoneBrandNode = new PhoneBrandNode(phoneList);
        PhoneCapacityNode phoneCapacityNode = new PhoneCapacityNode(phoneList);
        PhoneColourNode phoneColourNode = new PhoneColourNode(phoneList);
        PhoneCostNode phoneCostNode = new PhoneCostNode(phoneList);
        PhoneTagNode phoneTagNode = new PhoneTagNode(phoneList);
        addEdges(
                new Edge<>(PREFIX_IDENTITY_NUM, startingNode, phoneIdentityNumberNode),
                new Edge<>(PREFIX_SERIAL_NUM, phoneIdentityNumberNode, phoneSerialNumberNode),
                new Edge<>(PREFIX_PHONE_NAME, phoneSerialNumberNode, phoneNameNode),
                new Edge<>(PREFIX_BRAND, phoneNameNode, phoneBrandNode),
                new Edge<>(PREFIX_CAPACITY, phoneBrandNode, phoneCapacityNode),
                new Edge<>(PREFIX_COLOUR, phoneCapacityNode, phoneColourNode),
                new Edge<>(PREFIX_COST, phoneColourNode, phoneCostNode),
                new Edge<>(PREFIX_TAG, phoneCostNode, phoneTagNode),
                new Edge<>(PREFIX_TAG, phoneTagNode, phoneTagNode)
        );
    }

}
