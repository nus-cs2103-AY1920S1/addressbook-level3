package seedu.address.logic;

import static seedu.address.logic.parser.CliSyntax.PREFIX_BRAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAPACITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COLOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
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
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import seedu.address.logic.commands.statisticcommand.StatisticType;
import seedu.address.logic.nodes.customer.CustomerContactNumberNode;
import seedu.address.logic.nodes.customer.CustomerEmailNode;
import seedu.address.logic.nodes.customer.CustomerNameNode;
import seedu.address.logic.nodes.customer.CustomerTagNode;
import seedu.address.logic.nodes.order.OrderCustomerIndexNode;
import seedu.address.logic.nodes.order.OrderPhoneIndexNode;
import seedu.address.logic.nodes.order.OrderPriceNode;
import seedu.address.logic.nodes.order.OrderTagNode;
import seedu.address.logic.nodes.phone.PhoneBrandNode;
import seedu.address.logic.nodes.phone.PhoneCapacityNode;
import seedu.address.logic.nodes.phone.PhoneColourNode;
import seedu.address.logic.nodes.phone.PhoneCostNode;
import seedu.address.logic.nodes.phone.PhoneIdentityNumberNode;
import seedu.address.logic.nodes.phone.PhoneNameNode;
import seedu.address.logic.nodes.phone.PhoneSerialNumberNode;
import seedu.address.logic.nodes.phone.PhoneTagNode;
import seedu.address.logic.nodes.schedule.ScheduleDateNode;
import seedu.address.logic.nodes.schedule.ScheduleTagNode;
import seedu.address.logic.nodes.schedule.ScheduleTimeNode;
import seedu.address.logic.nodes.schedule.ScheduleVenueNode;
import seedu.address.logic.nodes.stats.StatsTypeNode;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;

/**
 * Represents a generator of graphs for autocomplete support.
 */
class GraphGenerator {

    private final Model model;
    private final Map<String, Graph> graphs;

    GraphGenerator(Model model) {
        this.model = model;
        graphs = new HashMap<>();

        buildGraphs();
    }

    /**
     * Instantiates supporting graphs.
     */
    private void buildGraphs() {
        // Customer commands
        graphs.put("switch-c", Graph.emptyGraph());
        graphs.put("add-c", generateAddCustomerGraph());
        graphs.put("delete-c", Graph.emptyGraph());
        graphs.put("find-c", Graph.emptyGraph());
        graphs.put("list-c", Graph.emptyGraph());
        graphs.put("clear-c", Graph.emptyGraph());
        graphs.put("edit-c", Graph.emptyGraph());
        graphs.put("copy-c", Graph.emptyGraph());

        // Phone commands
        graphs.put("switch-p", Graph.emptyGraph());
        graphs.put("add-p", generateAddPhoneGraph());
        graphs.put("delete-p", Graph.emptyGraph());
        graphs.put("find-p", Graph.emptyGraph());
        graphs.put("list-p", Graph.emptyGraph());
        graphs.put("clear-p", Graph.emptyGraph());
        graphs.put("edit-p", Graph.emptyGraph());
        graphs.put("copy-p", Graph.emptyGraph());

        // Order commands
        graphs.put("switch-o", Graph.emptyGraph());
        graphs.put("add-o", generateAddOrderGraph());
        graphs.put("find-o", Graph.emptyGraph());
        graphs.put("complete", Graph.emptyGraph());
        graphs.put("cancel", Graph.emptyGraph());
        graphs.put("list-o", Graph.emptyGraph());
        graphs.put("clear-o", Graph.emptyGraph());
        graphs.put("edit-o", Graph.emptyGraph());
        graphs.put("copy-o", Graph.emptyGraph());

        // Schedule commands
        graphs.put("switch-s", Graph.emptyGraph());
        graphs.put("schedule", Graph.emptyGraph());
        graphs.put("add-s", generateAddScheduleGraph());
        graphs.put("delete-s", Graph.emptyGraph());
        graphs.put("edit-s", Graph.emptyGraph());

        // General commands
        graphs.put("undo", Graph.emptyGraph());
        graphs.put("redo", Graph.emptyGraph());
        graphs.put("history", Graph.emptyGraph());
        graphs.put("generate-s", generateStatisticsGraph());
        graphs.put("exit", Graph.emptyGraph());
        graphs.put("help", Graph.emptyGraph());
        graphs.put("export", Graph.emptyGraph());
    }

    private Optional<Graph> getGraph(String commandWord) {
        return Optional.ofNullable(graphs.get(commandWord));
    }

    private Set<String> getCommandWords() {
        return graphs.keySet();
    }

    /**
     * Processes an input string and returns possible autocomplete results.
     * @param input A user input string.
     * @return An {@code AutoCompleteResult} containing possible autocomplete values.
     */
    AutoCompleteResult process(String input) {
        int firstSpace = input.indexOf(" ");
        if (firstSpace == -1) { // there is no space, indicating still typing command word
            SortedSet<String> values = new TreeSet<>(getCommandWords());
            return new AutoCompleteResult(values, input);
        } else { // there is at least one space, suggesting command word is present
            String commandWord = input.substring(0, firstSpace);
            Optional<Graph> graph = getGraph(commandWord);
            if (graph.isPresent()) { // command word is supported
                String args = input.substring(firstSpace);
                return graph.get().process(args);
            } else { // command word not supported, return empty set
                return new AutoCompleteResult(Collections.emptySortedSet(), input);
            }
        }
    }

    /**
     * Returns a graph supporting AddCustomer parser/command.
     */
    private Graph generateAddCustomerGraph() {
        List<Customer> customerList = model.getCustomerBook().getList();
        Node<?> addCustomerStartNode = Node.emptyNode();
        Node<Customer> customerContactNumberNode = new CustomerContactNumberNode(customerList);
        Node<Customer> customerEmailNode = new CustomerEmailNode(customerList);
        Node<Customer> customerNameNode = new CustomerNameNode(customerList);
        Node<Customer> customerTagNode = new CustomerTagNode(customerList);
        return new Graph(addCustomerStartNode, Arrays.asList(
                new Edge(PREFIX_NAME, addCustomerStartNode, customerNameNode),
                new Edge(PREFIX_CONTACT, customerNameNode, customerContactNumberNode),
                new Edge(PREFIX_EMAIL, customerContactNumberNode, customerEmailNode),
                new Edge(PREFIX_TAG, customerEmailNode, customerTagNode),
                new Edge(PREFIX_TAG, customerTagNode, customerTagNode)
        ));
    }

    /**
     * Returns a graph supporting AddPhone parser/command.
     */
    private Graph generateAddPhoneGraph() {
        List<Phone> phoneList = model.getPhoneBook().getList();
        Node<?> addPhoneStartNode = Node.emptyNode();
        Node<Phone> phoneIdentityNumberNode = new PhoneIdentityNumberNode(phoneList);
        Node<Phone> phoneSerialNumberNode = new PhoneSerialNumberNode(phoneList);
        Node<Phone> phoneNameNode = new PhoneNameNode(phoneList);
        Node<Phone> phoneBrandNode = new PhoneBrandNode(phoneList);
        Node<Phone> phoneCapacityNode = new PhoneCapacityNode(phoneList);
        Node<Phone> phoneColourNode = new PhoneColourNode(phoneList);
        Node<Phone> phoneCostNode = new PhoneCostNode(phoneList);
        Node<Phone> phoneTagNode = new PhoneTagNode(phoneList);
        return new Graph(addPhoneStartNode, Arrays.asList(
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

    /**
     * Returns a graph supporting AddOrder parser/command.
     */
    private Graph generateAddOrderGraph() {
        List<Order> orderList = model.getOrderBook().getList();
        Node<?> addOrderStartNode = Node.emptyNode();
        Node<Order> orderCustomerIndexNode = new OrderCustomerIndexNode(orderList);
        Node<Order> orderPhoneIndexNode = new OrderPhoneIndexNode(orderList);
        Node<Order> orderPriceNode = new OrderPriceNode(orderList);
        Node<Order> orderTagNode = new OrderTagNode(orderList);
        return new Graph(addOrderStartNode, Arrays.asList(
                new Edge(PREFIX_PHONE, addOrderStartNode, orderCustomerIndexNode),
                new Edge(PREFIX_CUSTOMER, orderCustomerIndexNode, orderPhoneIndexNode),
                new Edge(PREFIX_PRICE, orderPhoneIndexNode, orderPriceNode),
                new Edge(PREFIX_TAG, orderPriceNode, orderTagNode),
                new Edge(PREFIX_TAG, orderTagNode, orderTagNode)
        ));
    }

    /**
     * Returns a graph supporting AddSchedule parser/command.
     */
    private Graph generateAddScheduleGraph() {
        List<Schedule> scheduleList = model.getScheduleBook().getList();
        Node<?> addScheduleStartNode = Node.emptyNode();
        Node<Schedule> scheduleDateNode = new ScheduleDateNode(scheduleList);
        Node<Schedule> scheduleTimeNode = new ScheduleTimeNode(scheduleList);
        Node<Schedule> scheduleVenueNode = new ScheduleVenueNode(scheduleList);
        Node<Schedule> scheduleTagNode = new ScheduleTagNode(scheduleList);
        return new Graph(addScheduleStartNode, Arrays.asList(
                new Edge(PREFIX_DATE, addScheduleStartNode, scheduleDateNode),
                new Edge(PREFIX_TIME, scheduleDateNode, scheduleTimeNode),
                new Edge(PREFIX_VENUE, scheduleTimeNode, scheduleVenueNode),
                new Edge(PREFIX_TAG, scheduleVenueNode, scheduleTagNode),
                new Edge(PREFIX_TAG, scheduleTagNode, scheduleTagNode)
        ));
    }

    /**
     * Returns a graph supporting generate stats parser/command.
     */
    private Graph generateStatisticsGraph() {
        List<Schedule> scheduleList = model.getScheduleBook().getList();
        Node<?> statsStartNode = Node.emptyNode();
        Node<StatisticType> statsTypeNode = new StatsTypeNode(Arrays.asList(StatisticType.values()));
        Node<Schedule> statsDateNode = new ScheduleDateNode(scheduleList);
        return new Graph(statsStartNode, Arrays.asList(
            new Edge(PREFIX_STAT_TYPE, statsStartNode, statsTypeNode),
            new Edge(PREFIX_STARTING_DATE, statsTypeNode, statsDateNode),
            new Edge(PREFIX_ENDING_DATE, statsDateNode, statsDateNode)
        ));
    }

}
