package seedu.address.ui;

import seedu.address.logic.Logic;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.ui.nodes.customer.AddCustomerStartNode;
import seedu.address.ui.nodes.customer.CustomerContactNumberNode;
import seedu.address.ui.nodes.customer.CustomerEmailNode;
import seedu.address.ui.nodes.customer.CustomerNameNode;
import seedu.address.ui.nodes.customer.CustomerTagNode;
import seedu.address.ui.nodes.order.AddOrderStartNode;
import seedu.address.ui.nodes.order.OrderCustomerIndexNode;
import seedu.address.ui.nodes.order.OrderPhoneIndexNode;
import seedu.address.ui.nodes.order.OrderPriceNode;
import seedu.address.ui.nodes.order.OrderTagNode;
import seedu.address.ui.nodes.phone.AddPhoneStartNode;
import seedu.address.ui.nodes.phone.PhoneBrandNode;
import seedu.address.ui.nodes.phone.PhoneCapacityNode;
import seedu.address.ui.nodes.phone.PhoneColourNode;
import seedu.address.ui.nodes.phone.PhoneCostNode;
import seedu.address.ui.nodes.phone.PhoneIdentityNumberNode;
import seedu.address.ui.nodes.phone.PhoneNameNode;
import seedu.address.ui.nodes.phone.PhoneSerialNumberNode;
import seedu.address.ui.nodes.phone.PhoneTagNode;

import java.util.Arrays;
import java.util.Optional;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITYNUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIALNUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class GraphGenerator {

    private static GraphGenerator theOne;

    private final Logic logic;

    private Graph<Customer> addCustomerGraph;
    private Graph<Phone> addPhoneGraph;
    private Graph<Order> addOrderGraph;

    public static GraphGenerator getInstance() {
        if (theOne == null) {
            throw new NullPointerException();
        } else {
            return theOne;
        }
    }

    public GraphGenerator(Logic logic) {
        this.logic = logic;
        theOne = this;

        setAddCustomerGraph();
        setAddPhoneGraph();
        setAddOrderGraph();
    }

    private void setAddCustomerGraph() {
        Node<Customer> addCustomerStartNode = new AddCustomerStartNode(logic.getFilteredCustomerList());
        Node<Customer> customerContactNumberNode = new CustomerContactNumberNode(logic.getFilteredCustomerList());
        Node<Customer> customerEmailNode = new CustomerEmailNode(logic.getFilteredCustomerList());
        Node<Customer> customerNameNode = new CustomerNameNode(logic.getFilteredCustomerList());
        Node<Customer> customerTagNode = new CustomerTagNode(logic.getFilteredCustomerList());
        addCustomerGraph = new Graph<>(addCustomerStartNode, Arrays.asList(
                new Edge<>(PREFIX_NAME, addCustomerStartNode, customerNameNode),
                new Edge<>(PREFIX_CONTACT, customerNameNode, customerContactNumberNode),
                new Edge<>(PREFIX_EMAIL, customerContactNumberNode, customerEmailNode),
                new Edge<>(PREFIX_TAG, customerEmailNode, customerTagNode),
                new Edge<>(PREFIX_TAG, customerTagNode, customerTagNode)
        ));
    }

    private void setAddPhoneGraph() {
        Node<Phone> addPhoneStartNode = new AddPhoneStartNode(logic.getFilteredPhoneList());
        Node<Phone> phoneIdentityNumberNode = new PhoneIdentityNumberNode(logic.getFilteredPhoneList());
        Node<Phone> phoneSerialNumberNode = new PhoneSerialNumberNode(logic.getFilteredPhoneList());
        Node<Phone> phoneNameNode = new PhoneNameNode(logic.getFilteredPhoneList());
        Node<Phone> phoneBrandNode = new PhoneBrandNode(logic.getFilteredPhoneList());
        Node<Phone> phoneCapacityNode = new PhoneCapacityNode(logic.getFilteredPhoneList());
        Node<Phone> phoneColourNode = new PhoneColourNode(logic.getFilteredPhoneList());
        Node<Phone> phoneCostNode = new PhoneCostNode(logic.getFilteredPhoneList());
        Node<Phone> phoneTagNode = new PhoneTagNode(logic.getFilteredPhoneList());
        addPhoneGraph = new Graph<>(addPhoneStartNode, Arrays.asList(
                new Edge<>(PREFIX_IDENTITYNUM, addPhoneStartNode, phoneIdentityNumberNode),
                new Edge<>(PREFIX_SERIALNUM, phoneIdentityNumberNode, phoneSerialNumberNode),
                new Edge<>(PREFIX_PHONENAME, phoneSerialNumberNode, phoneNameNode),
                new Edge<>(PREFIX_BRAND, phoneNameNode, phoneBrandNode),
                new Edge<>(PREFIX_CAPACITY, phoneBrandNode, phoneCapacityNode),
                new Edge<>(PREFIX_COLOUR, phoneCapacityNode, phoneColourNode),
                new Edge<>(PREFIX_COST, phoneColourNode, phoneCostNode),
                new Edge<>(PREFIX_TAG, phoneCostNode, phoneTagNode),
                new Edge<>(PREFIX_TAG, phoneTagNode, phoneTagNode)
        ));
    }

    private void setAddOrderGraph() {
        Node<Order> addOrderStartNode = new AddOrderStartNode(logic.getFilteredOrderList());
        Node<Order> orderCustomerIndexNode = new OrderCustomerIndexNode(logic.getFilteredOrderList());
        Node<Order> orderPhoneIndexNode = new OrderPhoneIndexNode(logic.getFilteredOrderList());
        Node<Order> orderPriceNode = new OrderPriceNode(logic.getFilteredOrderList());
        Node<Order> orderTagNode = new OrderTagNode(logic.getFilteredOrderList());
        addOrderGraph = new Graph<>(addOrderStartNode, Arrays.asList(
                new Edge<>(PREFIX_PHONE, addOrderStartNode, orderCustomerIndexNode),
                new Edge<>(PREFIX_CUSTOMER, orderCustomerIndexNode, orderPhoneIndexNode),
                new Edge<>(PREFIX_PRICE, orderPhoneIndexNode, orderPriceNode),
                new Edge<>(PREFIX_TAG, orderPriceNode, orderTagNode),
                new Edge<>(PREFIX_TAG, orderTagNode, orderTagNode)
        ));
    }


    public Optional<Graph<?>> getGraph(String commandWord) {
        switch (commandWord) {
        case "add-c":
            return Optional.of(addCustomerGraph);
        case "add-p":
            return Optional.of(addPhoneGraph);
        case "add-o":
            return Optional.of(addOrderGraph);
        default:
            return Optional.empty();
        }
    }

}
