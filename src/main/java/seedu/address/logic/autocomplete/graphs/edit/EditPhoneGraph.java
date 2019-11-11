package seedu.address.logic.autocomplete.graphs.edit;

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
import seedu.address.logic.autocomplete.graphs.GraphWithStartNodeAndPreamble;
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
 * Represents a {@code Graph} used to support autocomplete for {@code EditPhoneCommand}.
 */
public class EditPhoneGraph extends GraphWithStartNodeAndPreamble {

    public EditPhoneGraph(Model model) {
        super(model.getFilteredPhoneList());
        initialise(model);
    }

    private void initialise(Model model) {
        List<Phone> phoneList = model.getFilteredPhoneList();
        PhoneNameNode phoneNameNode = new PhoneNameNode(phoneList);
        PhoneIdentityNumberNode phoneIdentityNumberNode = new PhoneIdentityNumberNode(phoneList);
        PhoneSerialNumberNode phoneSerialNumberNode = new PhoneSerialNumberNode(phoneList);
        PhoneCostNode phoneCostNode = new PhoneCostNode(phoneList);
        PhoneColourNode phoneColourNode = new PhoneColourNode(phoneList);
        PhoneBrandNode phoneBrandNode = new PhoneBrandNode(phoneList);
        PhoneCapacityNode phoneCapacityNode = new PhoneCapacityNode(phoneList);
        PhoneTagNode phoneTagNode = new PhoneTagNode(phoneList);
        addEdges(
                new Edge<>(PREFIX_IDENTITY_NUM, startingNode, phoneIdentityNumberNode),
                new Edge<>(PREFIX_SERIAL_NUM, startingNode, phoneSerialNumberNode),
                new Edge<>(PREFIX_COST, startingNode, phoneCostNode),
                new Edge<>(PREFIX_CAPACITY, startingNode, phoneCapacityNode),
                new Edge<>(PREFIX_BRAND, startingNode, phoneBrandNode),
                new Edge<>(PREFIX_COLOUR, startingNode, phoneColourNode),
                new Edge<>(PREFIX_TAG, startingNode, phoneTagNode),
                new Edge<>(PREFIX_PHONE_NAME, startingNode, phoneNameNode),
                new Edge<>(PREFIX_PHONE_NAME, startingNode, phoneNameNode)
        );
        addEdges(
                new Edge<>(PREFIX_IDENTITY_NUM, phoneIdentityNumberNode, phoneIdentityNumberNode),
                new Edge<>(PREFIX_SERIAL_NUM, phoneIdentityNumberNode, phoneSerialNumberNode),
                new Edge<>(PREFIX_COST, phoneIdentityNumberNode, phoneCostNode),
                new Edge<>(PREFIX_CAPACITY, phoneIdentityNumberNode, phoneCapacityNode),
                new Edge<>(PREFIX_BRAND, phoneIdentityNumberNode, phoneBrandNode),
                new Edge<>(PREFIX_COLOUR, phoneIdentityNumberNode, phoneColourNode),
                new Edge<>(PREFIX_TAG, phoneIdentityNumberNode, phoneTagNode),
                new Edge<>(PREFIX_PHONE_NAME, phoneIdentityNumberNode, phoneNameNode),
                new Edge<>(PREFIX_IDENTITY_NUM, phoneSerialNumberNode, phoneIdentityNumberNode),
                new Edge<>(PREFIX_SERIAL_NUM, phoneSerialNumberNode, phoneSerialNumberNode),
                new Edge<>(PREFIX_COST, phoneSerialNumberNode, phoneCostNode),
                new Edge<>(PREFIX_CAPACITY, phoneSerialNumberNode, phoneCapacityNode),
                new Edge<>(PREFIX_BRAND, phoneSerialNumberNode, phoneBrandNode),
                new Edge<>(PREFIX_COLOUR, phoneSerialNumberNode, phoneColourNode),
                new Edge<>(PREFIX_TAG, phoneSerialNumberNode, phoneTagNode),
                new Edge<>(PREFIX_PHONE_NAME, phoneSerialNumberNode, phoneNameNode)
        );
        addEdges(
                new Edge<>(PREFIX_IDENTITY_NUM, phoneCostNode, phoneIdentityNumberNode),
                new Edge<>(PREFIX_SERIAL_NUM, phoneCostNode, phoneSerialNumberNode),
                new Edge<>(PREFIX_COST, phoneCostNode, phoneCostNode),
                new Edge<>(PREFIX_CAPACITY, phoneCostNode, phoneCapacityNode),
                new Edge<>(PREFIX_BRAND, phoneCostNode, phoneBrandNode),
                new Edge<>(PREFIX_COLOUR, phoneCostNode, phoneColourNode),
                new Edge<>(PREFIX_TAG, phoneCostNode, phoneTagNode),
                new Edge<>(PREFIX_PHONE_NAME, phoneCostNode, phoneNameNode),
                new Edge<>(PREFIX_IDENTITY_NUM, phoneCapacityNode, phoneIdentityNumberNode),
                new Edge<>(PREFIX_SERIAL_NUM, phoneCapacityNode, phoneSerialNumberNode),
                new Edge<>(PREFIX_COST, phoneCapacityNode, phoneCostNode),
                new Edge<>(PREFIX_CAPACITY, phoneCapacityNode, phoneCapacityNode),
                new Edge<>(PREFIX_BRAND, phoneCapacityNode, phoneBrandNode),
                new Edge<>(PREFIX_COLOUR, phoneCapacityNode, phoneColourNode),
                new Edge<>(PREFIX_TAG, phoneCapacityNode, phoneTagNode),
                new Edge<>(PREFIX_PHONE_NAME, phoneCapacityNode, phoneNameNode)
        );
        addEdges(
                new Edge<>(PREFIX_IDENTITY_NUM, phoneBrandNode, phoneIdentityNumberNode),
                new Edge<>(PREFIX_SERIAL_NUM, phoneBrandNode, phoneSerialNumberNode),
                new Edge<>(PREFIX_COST, phoneBrandNode, phoneCostNode),
                new Edge<>(PREFIX_CAPACITY, phoneBrandNode, phoneCapacityNode),
                new Edge<>(PREFIX_BRAND, phoneBrandNode, phoneBrandNode),
                new Edge<>(PREFIX_COLOUR, phoneBrandNode, phoneColourNode),
                new Edge<>(PREFIX_TAG, phoneBrandNode, phoneTagNode),
                new Edge<>(PREFIX_PHONE_NAME, phoneBrandNode, phoneNameNode),
                new Edge<>(PREFIX_IDENTITY_NUM, phoneColourNode, phoneIdentityNumberNode),
                new Edge<>(PREFIX_SERIAL_NUM, phoneColourNode, phoneSerialNumberNode),
                new Edge<>(PREFIX_COST, phoneColourNode, phoneCostNode),
                new Edge<>(PREFIX_CAPACITY, phoneColourNode, phoneCapacityNode),
                new Edge<>(PREFIX_BRAND, phoneColourNode, phoneBrandNode),
                new Edge<>(PREFIX_COLOUR, phoneColourNode, phoneColourNode),
                new Edge<>(PREFIX_TAG, phoneColourNode, phoneTagNode),
                new Edge<>(PREFIX_PHONE_NAME, phoneColourNode, phoneNameNode)
        );
        addEdges(
                new Edge<>(PREFIX_IDENTITY_NUM, phoneTagNode, phoneIdentityNumberNode),
                new Edge<>(PREFIX_SERIAL_NUM, phoneTagNode, phoneSerialNumberNode),
                new Edge<>(PREFIX_COST, phoneTagNode, phoneCostNode),
                new Edge<>(PREFIX_CAPACITY, phoneTagNode, phoneCapacityNode),
                new Edge<>(PREFIX_BRAND, phoneTagNode, phoneBrandNode),
                new Edge<>(PREFIX_COLOUR, phoneTagNode, phoneColourNode),
                new Edge<>(PREFIX_TAG, phoneTagNode, phoneTagNode),
                new Edge<>(PREFIX_PHONE_NAME, phoneTagNode, phoneNameNode),
                new Edge<>(PREFIX_IDENTITY_NUM, phoneNameNode, phoneIdentityNumberNode),
                new Edge<>(PREFIX_SERIAL_NUM, phoneNameNode, phoneSerialNumberNode),
                new Edge<>(PREFIX_COST, phoneNameNode, phoneCostNode),
                new Edge<>(PREFIX_CAPACITY, phoneNameNode, phoneCapacityNode),
                new Edge<>(PREFIX_BRAND, phoneNameNode, phoneBrandNode),
                new Edge<>(PREFIX_COLOUR, phoneNameNode, phoneColourNode),
                new Edge<>(PREFIX_TAG, phoneNameNode, phoneTagNode),
                new Edge<>(PREFIX_PHONE_NAME, phoneNameNode, phoneNameNode)
        );
    }

}
