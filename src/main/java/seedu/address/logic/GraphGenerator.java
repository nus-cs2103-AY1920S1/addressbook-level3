package seedu.address.logic;

import seedu.address.logic.commands.statisticcommand.StatisticType;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;
import seedu.address.logic.nodes.stats.StatsDateNode;
import seedu.address.logic.nodes.stats.StatsStartNode;
import seedu.address.logic.nodes.stats.StatsTypeNode;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.logic.nodes.customer.AddCustomerStartNode;
import seedu.address.logic.nodes.customer.CustomerContactNumberNode;
import seedu.address.logic.nodes.customer.CustomerEmailNode;
import seedu.address.logic.nodes.customer.CustomerNameNode;
import seedu.address.logic.nodes.customer.CustomerTagNode;
import seedu.address.logic.nodes.order.AddOrderStartNode;
import seedu.address.logic.nodes.order.OrderCustomerIndexNode;
import seedu.address.logic.nodes.order.OrderPhoneIndexNode;
import seedu.address.logic.nodes.order.OrderPriceNode;
import seedu.address.logic.nodes.order.OrderTagNode;
import seedu.address.logic.nodes.phone.AddPhoneStartNode;
import seedu.address.logic.nodes.phone.PhoneBrandNode;
import seedu.address.logic.nodes.phone.PhoneCapacityNode;
import seedu.address.logic.nodes.phone.PhoneColourNode;
import seedu.address.logic.nodes.phone.PhoneCostNode;
import seedu.address.logic.nodes.phone.PhoneIdentityNumberNode;
import seedu.address.logic.nodes.phone.PhoneNameNode;
import seedu.address.logic.nodes.phone.PhoneSerialNumberNode;
import seedu.address.logic.nodes.phone.PhoneTagNode;
import seedu.address.model.schedule.Schedule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ENDING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STAT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class GraphGenerator {

    private static GraphGenerator theOne;

    private final Model model;

    private Graph addCustomerGraph;
    private Graph addPhoneGraph;
    private Graph addOrderGraph;
    private Graph statisticsGraph;

    public static GraphGenerator getInstance() {
        if (theOne == null) {
            throw new NullPointerException();
        } else {
            return theOne;
        }
    }

    public GraphGenerator(Model model) {
        this.model = model;
        theOne = this;

        setAddCustomerGraph();
        setAddPhoneGraph();
        setAddOrderGraph();
        setStatisticsGraph();
    }

    private void setAddCustomerGraph() {
        List<Customer> customerList = model.getCustomerBook().getList();
        Node<Customer> addCustomerStartNode = new AddCustomerStartNode(Collections.emptyList());
        Node<Customer> customerContactNumberNode = new CustomerContactNumberNode(customerList);
        Node<Customer> customerEmailNode = new CustomerEmailNode(customerList);
        Node<Customer> customerNameNode = new CustomerNameNode(customerList);
        Node<Customer> customerTagNode = new CustomerTagNode(customerList);
        addCustomerGraph = new Graph(addCustomerStartNode, Arrays.asList(
                new Edge(PREFIX_NAME, addCustomerStartNode, customerNameNode),
                new Edge(PREFIX_CONTACT, customerNameNode, customerContactNumberNode),
                new Edge(PREFIX_EMAIL, customerContactNumberNode, customerEmailNode),
                new Edge(PREFIX_TAG, customerEmailNode, customerTagNode),
                new Edge(PREFIX_TAG, customerTagNode, customerTagNode)
        ));
    }

    private void setAddPhoneGraph() {
        List<Phone> phoneList = model.getPhoneBook().getList();
        Node<Phone> addPhoneStartNode = new AddPhoneStartNode(phoneList);
        Node<Phone> phoneIdentityNumberNode = new PhoneIdentityNumberNode(phoneList);
        Node<Phone> phoneSerialNumberNode = new PhoneSerialNumberNode(phoneList);
        Node<Phone> phoneNameNode = new PhoneNameNode(phoneList);
        Node<Phone> phoneBrandNode = new PhoneBrandNode(phoneList);
        Node<Phone> phoneCapacityNode = new PhoneCapacityNode(phoneList);
        Node<Phone> phoneColourNode = new PhoneColourNode(phoneList);
        Node<Phone> phoneCostNode = new PhoneCostNode(phoneList);
        Node<Phone> phoneTagNode = new PhoneTagNode(phoneList);
        addPhoneGraph = new Graph(addPhoneStartNode, Arrays.asList(
                new Edge(PREFIX_IDENTITY_NUM, addPhoneStartNode, phoneIdentityNumberNode),
                new Edge(PREFIX_SERIAL_NUM, phoneIdentityNumberNode, phoneSerialNumberNode),
                new Edge(PREFIX_PHONE_NAME, phoneSerialNumberNode, phoneNameNode),
                new Edge(PREFIX_BRAND, phoneNameNode, phoneBrandNode),
                new Edge(PREFIX_CAPACITY, phoneBrandNode, phoneCapacityNode),
                new Edge(PREFIX_COLOUR, phoneCapacityNode, phoneColourNode),
                new Edge(PREFIX_COST, phoneColourNode, phoneCostNode),
                new Edge(PREFIX_TAG, phoneCostNode, phoneTagNode),
                new Edge(PREFIX_TAG, phoneTagNode, phoneTagNode)
        ));
    }

    private void setAddOrderGraph() {
        List<Order> orderList = model.getOrderBook().getList();
        Node<Order> addOrderStartNode = new AddOrderStartNode(orderList);
        Node<Order> orderCustomerIndexNode = new OrderCustomerIndexNode(orderList);
        Node<Order> orderPhoneIndexNode = new OrderPhoneIndexNode(orderList);
        Node<Order> orderPriceNode = new OrderPriceNode(orderList);
        Node<Order> orderTagNode = new OrderTagNode(orderList);
        addOrderGraph = new Graph(addOrderStartNode, Arrays.asList(
                new Edge(PREFIX_PHONE, addOrderStartNode, orderCustomerIndexNode),
                new Edge(PREFIX_CUSTOMER, orderCustomerIndexNode, orderPhoneIndexNode),
                new Edge(PREFIX_PRICE, orderPhoneIndexNode, orderPriceNode),
                new Edge(PREFIX_TAG, orderPriceNode, orderTagNode),
                new Edge(PREFIX_TAG, orderTagNode, orderTagNode)
        ));
    }

    private void setStatisticsGraph() {
        List<Schedule> scheduleList = model.getScheduleBook().getList();
        Node<StatisticType> statsTypeNode = new StatsTypeNode(Arrays.asList(StatisticType.values()));
        Node<Schedule> statsDateNode = new StatsDateNode(scheduleList);
        Node<Schedule> statsStartNode = new StatsStartNode(scheduleList);
        statisticsGraph = new Graph(statsStartNode, Arrays.asList(
            new Edge(PREFIX_STAT_TYPE, statsStartNode, statsTypeNode),
            new Edge(PREFIX_STARTING_DATE, statsTypeNode, statsDateNode),
            new Edge(PREFIX_ENDING_DATE, statsDateNode, statsDateNode)
        ));
    }

    public Optional<Graph> getGraph(String commandWord) {
        switch (commandWord) {
        case "add-c":
            return Optional.of(addCustomerGraph);
        case "add-p":
            return Optional.of(addPhoneGraph);
        case "add-o":
            return Optional.of(addOrderGraph);
        case "generate-s":
            return Optional.of(statisticsGraph);
        default:
            return Optional.empty();
        }
    }

    public AutoCompleteResult process(String input) {
        int firstSpace = input.indexOf(" ");
        if (firstSpace == -1) { // there is no space, indicating still typing command word
            SortedSet<String> values = new TreeSet<>(CommandSuggestions.getSuggestions());
            return new AutoCompleteResult(values, input);
        } else { // there is at least one space, suggesting command word is present
            String commandWord = input.substring(0, firstSpace);
            Optional<Graph> graph = getGraph(commandWord);
            if (graph.isPresent()) {
                String args = input.substring(firstSpace);
                AutoCompleteResult result = graph.get().process(args);
                if (args.endsWith(" ")) {
                    return new AutoCompleteResult(result.getValues(), "");
                } else {
                    return result;
                }
            } else {
                return new AutoCompleteResult(new TreeSet<>(), input);
            }
        }
    }

}
