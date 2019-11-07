package seedu.address.logic.graphs;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.AutoCompleteResult;
import seedu.address.logic.Edge;
import seedu.address.logic.Graph;
import seedu.address.logic.Node;
import seedu.address.logic.nodes.customer.CustomerContactNumberNode;
import seedu.address.logic.nodes.customer.CustomerEmailNode;
import seedu.address.logic.nodes.customer.CustomerNameNode;
import seedu.address.logic.nodes.customer.CustomerTagNode;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

public class EditCustomerGraph extends Graph {

    private Node<?> startingNode;
    private List<Customer> customerList;

    public EditCustomerGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        customerList = model.getCustomerBook().getList();
        Node<Customer> customerContactNumberNode = new CustomerContactNumberNode(customerList);
        Node<Customer> customerEmailNode = new CustomerEmailNode(customerList);
        Node<Customer> customerNameNode = new CustomerNameNode(customerList);
        Node<Customer> customerTagNode = new CustomerTagNode(customerList);
        Node<?> editCustomerStartNode = Node.emptyNode();
        this.startingNode = editCustomerStartNode;
        edges.addAll(Arrays.asList(
                new Edge(PREFIX_CONTACT, editCustomerStartNode, customerContactNumberNode),
                new Edge(PREFIX_EMAIL, editCustomerStartNode, customerEmailNode),
                new Edge(PREFIX_NAME, editCustomerStartNode, customerNameNode),
                new Edge(PREFIX_TAG, editCustomerStartNode, customerTagNode),
                new Edge(PREFIX_CONTACT, customerContactNumberNode, customerContactNumberNode),
                new Edge(PREFIX_EMAIL, customerContactNumberNode, customerEmailNode),
                new Edge(PREFIX_NAME, customerContactNumberNode, customerNameNode),
                new Edge(PREFIX_TAG, customerContactNumberNode, customerTagNode),
                new Edge(PREFIX_CONTACT, customerEmailNode, customerContactNumberNode),
                new Edge(PREFIX_EMAIL, customerEmailNode, customerEmailNode),
                new Edge(PREFIX_NAME, customerEmailNode, customerNameNode),
                new Edge(PREFIX_TAG, customerEmailNode, customerTagNode),
                new Edge(PREFIX_CONTACT, customerNameNode, customerContactNumberNode),
                new Edge(PREFIX_EMAIL, customerNameNode, customerEmailNode),
                new Edge(PREFIX_NAME, customerNameNode, customerNameNode),
                new Edge(PREFIX_TAG, customerNameNode, customerTagNode),
                new Edge(PREFIX_CONTACT, customerTagNode, customerContactNumberNode),
                new Edge(PREFIX_EMAIL, customerTagNode, customerEmailNode),
                new Edge(PREFIX_NAME, customerTagNode, customerNameNode),
                new Edge(PREFIX_TAG, customerTagNode, customerTagNode)
        ));
    }

    @Override
    protected AutoCompleteResult process(String input) {
        SortedSet<String> values = new TreeSet<>();
        String stringToCompare;

        if (input.isBlank()) { // empty, all whitespaces, or null
            // suggest indexes
            int minIndex = 1;
            int maxIndex = customerList.size();
            values.add(String.valueOf(minIndex));
            values.add(String.valueOf(maxIndex));
            stringToCompare = "";
        } else {
            int secondSpace = input.stripLeading().indexOf(" ");
            if (secondSpace == -1) {
                // user is entering preamble
                // suggest indexes
                int minIndex = 1;
                int maxIndex = customerList.size();
                values.add(String.valueOf(minIndex));
                values.add(String.valueOf(maxIndex));
                stringToCompare = "";
            } else {
                String preamble = input.stripLeading().substring(0, secondSpace);
                try {
                    int index = Integer.parseInt(preamble);
                    String argString = input.substring(preamble.length() + 1);
                    stringToCompare = argString;
                    Pattern prefixPattern = Pattern.compile(" .{1,2}/");
                    Node<?> currentNode = startingNode;
                    Matcher matcher = prefixPattern.matcher(argString);
                    while (matcher.find()) {
                        Prefix prefix = new Prefix(matcher.group().trim());
                        Optional<Node<?>> nextNode = traverse(currentNode, prefix);
                        if (nextNode.isPresent()) {
                            currentNode = nextNode.get();
                        }
                        stringToCompare = argString.substring(matcher.end());
                    }
                    if (input.endsWith("/")) { // fill with possible arguments
                        values.addAll(currentNode.getValues());
                    } else { // fill with possible prefixes
                        List<Prefix> prefixes = getPrefixes(currentNode);
                        prefixes.forEach(prefix -> values.add(prefix.toString()));
                        stringToCompare = stringToCompare.substring(stringToCompare.lastIndexOf(" ") + 1);
                    }
                    return new AutoCompleteResult(values, stringToCompare);
                } catch (NumberFormatException e) {
                    // preamble is not an integer
                    // suggest indexes
                    int minIndex = 1;
                    int maxIndex = customerList.size();
                    values.add(String.valueOf(minIndex));
                    values.add(String.valueOf(maxIndex));
                    stringToCompare = "";
                }
            }
        }
        return new AutoCompleteResult(values, stringToCompare);
    }

}
