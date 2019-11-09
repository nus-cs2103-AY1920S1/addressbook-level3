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
import seedu.address.logic.GraphWithStartNodeAndPreamble;
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
 * Represents a {@code Graph} used to support autocomplete for {@code EditPhoneCommand}.
 */
public class EditPhoneGraph extends GraphWithStartNodeAndPreamble {

    public EditPhoneGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Phone> phoneList = model.getFilteredPhoneList();
        setDataList(phoneList);
        AutoCompleteNode<?> editPhoneStartNode = EmptyAutoCompleteNode.getInstance();
        setStartingNode(editPhoneStartNode);
        PhoneNameNode phoneNameNode = new PhoneNameNode(phoneList);
        PhoneIdentityNumberNode phoneIdentityNumberNode = new PhoneIdentityNumberNode(phoneList);
        PhoneSerialNumberNode phoneSerialNumberNode = new PhoneSerialNumberNode(phoneList);
        PhoneCostNode phoneCostNode = new PhoneCostNode(phoneList);
        PhoneColourNode phoneColourNode = new PhoneColourNode(phoneList);
        PhoneBrandNode phoneBrandNode = new PhoneBrandNode(phoneList);
        PhoneCapacityNode phoneCapacityNode = new PhoneCapacityNode(phoneList);
        PhoneTagNode phoneTagNode = new PhoneTagNode(phoneList);
        edgeList.addAll(Arrays.asList(
                new AutoCompleteEdge<>(PREFIX_IDENTITY_NUM, editPhoneStartNode, phoneIdentityNumberNode),
                new AutoCompleteEdge<>(PREFIX_SERIAL_NUM, editPhoneStartNode, phoneSerialNumberNode),
                new AutoCompleteEdge<>(PREFIX_COST, editPhoneStartNode, phoneCostNode),
                new AutoCompleteEdge<>(PREFIX_CAPACITY, editPhoneStartNode, phoneCapacityNode),
                new AutoCompleteEdge<>(PREFIX_BRAND, editPhoneStartNode, phoneBrandNode),
                new AutoCompleteEdge<>(PREFIX_COLOUR, editPhoneStartNode, phoneColourNode),
                new AutoCompleteEdge<>(PREFIX_TAG, editPhoneStartNode, phoneTagNode),
                new AutoCompleteEdge<>(PREFIX_PHONE_NAME, editPhoneStartNode, phoneNameNode),
                new AutoCompleteEdge<>(PREFIX_PHONE_NAME, editPhoneStartNode, phoneNameNode),
                new AutoCompleteEdge<>(PREFIX_IDENTITY_NUM, phoneIdentityNumberNode, phoneIdentityNumberNode),
                new AutoCompleteEdge<>(PREFIX_SERIAL_NUM, phoneIdentityNumberNode, phoneSerialNumberNode),
                new AutoCompleteEdge<>(PREFIX_COST, phoneIdentityNumberNode, phoneCostNode),
                new AutoCompleteEdge<>(PREFIX_CAPACITY, phoneIdentityNumberNode, phoneCapacityNode),
                new AutoCompleteEdge<>(PREFIX_BRAND, phoneIdentityNumberNode, phoneBrandNode),
                new AutoCompleteEdge<>(PREFIX_COLOUR, phoneIdentityNumberNode, phoneColourNode),
                new AutoCompleteEdge<>(PREFIX_TAG, phoneIdentityNumberNode, phoneTagNode),
                new AutoCompleteEdge<>(PREFIX_PHONE_NAME, phoneIdentityNumberNode, phoneNameNode),
                new AutoCompleteEdge<>(PREFIX_IDENTITY_NUM, phoneSerialNumberNode, phoneIdentityNumberNode),
                new AutoCompleteEdge<>(PREFIX_SERIAL_NUM, phoneSerialNumberNode, phoneSerialNumberNode),
                new AutoCompleteEdge<>(PREFIX_COST, phoneSerialNumberNode, phoneCostNode),
                new AutoCompleteEdge<>(PREFIX_CAPACITY, phoneSerialNumberNode, phoneCapacityNode)
        ));
        edgeList.addAll(Arrays.asList(
                new AutoCompleteEdge<>(PREFIX_BRAND, phoneSerialNumberNode, phoneBrandNode),
                new AutoCompleteEdge<>(PREFIX_COLOUR, phoneSerialNumberNode, phoneColourNode),
                new AutoCompleteEdge<>(PREFIX_TAG, phoneSerialNumberNode, phoneTagNode),
                new AutoCompleteEdge<>(PREFIX_PHONE_NAME, phoneSerialNumberNode, phoneNameNode),
                new AutoCompleteEdge<>(PREFIX_IDENTITY_NUM, phoneCostNode, phoneIdentityNumberNode),
                new AutoCompleteEdge<>(PREFIX_SERIAL_NUM, phoneCostNode, phoneSerialNumberNode),
                new AutoCompleteEdge<>(PREFIX_COST, phoneCostNode, phoneCostNode),
                new AutoCompleteEdge<>(PREFIX_CAPACITY, phoneCostNode, phoneCapacityNode),
                new AutoCompleteEdge<>(PREFIX_BRAND, phoneCostNode, phoneBrandNode),
                new AutoCompleteEdge<>(PREFIX_COLOUR, phoneCostNode, phoneColourNode),
                new AutoCompleteEdge<>(PREFIX_TAG, phoneCostNode, phoneTagNode),
                new AutoCompleteEdge<>(PREFIX_PHONE_NAME, phoneCostNode, phoneNameNode),
                new AutoCompleteEdge<>(PREFIX_IDENTITY_NUM, phoneCapacityNode, phoneIdentityNumberNode),
                new AutoCompleteEdge<>(PREFIX_SERIAL_NUM, phoneCapacityNode, phoneSerialNumberNode),
                new AutoCompleteEdge<>(PREFIX_COST, phoneCapacityNode, phoneCostNode),
                new AutoCompleteEdge<>(PREFIX_CAPACITY, phoneCapacityNode, phoneCapacityNode),
                new AutoCompleteEdge<>(PREFIX_BRAND, phoneCapacityNode, phoneBrandNode),
                new AutoCompleteEdge<>(PREFIX_COLOUR, phoneCapacityNode, phoneColourNode),
                new AutoCompleteEdge<>(PREFIX_TAG, phoneCapacityNode, phoneTagNode),
                new AutoCompleteEdge<>(PREFIX_PHONE_NAME, phoneCapacityNode, phoneNameNode),
                new AutoCompleteEdge<>(PREFIX_IDENTITY_NUM, phoneBrandNode, phoneIdentityNumberNode)
        ));
        edgeList.addAll(Arrays.asList(
                new AutoCompleteEdge<>(PREFIX_SERIAL_NUM, phoneBrandNode, phoneSerialNumberNode),
                new AutoCompleteEdge<>(PREFIX_COST, phoneBrandNode, phoneCostNode),
                new AutoCompleteEdge<>(PREFIX_CAPACITY, phoneBrandNode, phoneCapacityNode),
                new AutoCompleteEdge<>(PREFIX_BRAND, phoneBrandNode, phoneBrandNode),
                new AutoCompleteEdge<>(PREFIX_COLOUR, phoneBrandNode, phoneColourNode),
                new AutoCompleteEdge<>(PREFIX_TAG, phoneBrandNode, phoneTagNode),
                new AutoCompleteEdge<>(PREFIX_PHONE_NAME, phoneBrandNode, phoneNameNode),
                new AutoCompleteEdge<>(PREFIX_IDENTITY_NUM, phoneColourNode, phoneIdentityNumberNode),
                new AutoCompleteEdge<>(PREFIX_SERIAL_NUM, phoneColourNode, phoneSerialNumberNode),
                new AutoCompleteEdge<>(PREFIX_COST, phoneColourNode, phoneCostNode),
                new AutoCompleteEdge<>(PREFIX_CAPACITY, phoneColourNode, phoneCapacityNode),
                new AutoCompleteEdge<>(PREFIX_BRAND, phoneColourNode, phoneBrandNode),
                new AutoCompleteEdge<>(PREFIX_COLOUR, phoneColourNode, phoneColourNode),
                new AutoCompleteEdge<>(PREFIX_TAG, phoneColourNode, phoneTagNode),
                new AutoCompleteEdge<>(PREFIX_PHONE_NAME, phoneColourNode, phoneNameNode),
                new AutoCompleteEdge<>(PREFIX_IDENTITY_NUM, phoneTagNode, phoneIdentityNumberNode),
                new AutoCompleteEdge<>(PREFIX_SERIAL_NUM, phoneTagNode, phoneSerialNumberNode)
        ));
        edgeList.addAll(Arrays.asList(
                new AutoCompleteEdge<>(PREFIX_COST, phoneTagNode, phoneCostNode),
                new AutoCompleteEdge<>(PREFIX_CAPACITY, phoneTagNode, phoneCapacityNode),
                new AutoCompleteEdge<>(PREFIX_BRAND, phoneTagNode, phoneBrandNode),
                new AutoCompleteEdge<>(PREFIX_COLOUR, phoneTagNode, phoneColourNode),
                new AutoCompleteEdge<>(PREFIX_TAG, phoneTagNode, phoneTagNode),
                new AutoCompleteEdge<>(PREFIX_PHONE_NAME, phoneTagNode, phoneNameNode),
                new AutoCompleteEdge<>(PREFIX_IDENTITY_NUM, phoneNameNode, phoneIdentityNumberNode),
                new AutoCompleteEdge<>(PREFIX_SERIAL_NUM, phoneNameNode, phoneSerialNumberNode),
                new AutoCompleteEdge<>(PREFIX_COST, phoneNameNode, phoneCostNode),
                new AutoCompleteEdge<>(PREFIX_CAPACITY, phoneNameNode, phoneCapacityNode),
                new AutoCompleteEdge<>(PREFIX_BRAND, phoneNameNode, phoneBrandNode),
                new AutoCompleteEdge<>(PREFIX_COLOUR, phoneNameNode, phoneColourNode),
                new AutoCompleteEdge<>(PREFIX_TAG, phoneNameNode, phoneTagNode),
                new AutoCompleteEdge<>(PREFIX_PHONE_NAME, phoneNameNode, phoneNameNode)
        ));
    }

}
