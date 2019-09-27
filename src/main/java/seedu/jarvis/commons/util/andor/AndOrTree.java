package seedu.jarvis.commons.util.andor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * A representation of an And-Or Tree. The tree represents the requirements for the root node
 * to be {@code true}, and encapsulates some methods for operations in the tree.
 *
 * The tree is immutable once created.
 *
 * @param <T> generic type held by each node
 */
public class AndOrTree<T> {
    private AndOrNode<T> root;

    private AndOrTree(AndOrNode<T> root) {
        this.root = root;
    }

    /**
     * Returns a new AndOr Tree with the given name, using a tree in json format.
     *
     * @param rootData of the tree
     * @param jsonTreeFormat the tree in json format
     * @return a new {@code AndOrTree} built with the given tree format
     * @throws IOException if an I/O Exception occurs
     */
    public static <T> AndOrTree<T> buildTree(T rootData, String jsonTreeFormat)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonTreeFormat);

        // build the root
        AndOrNode<T> newRoot = AndOrNode.createAndOrNode(rootData, null,  "");

        iterateAndBuildTree(node, newRoot);

        return new AndOrTree<>(newRoot);
    }

    private static String getKey(JsonNode node) {
        return node.fields().next().getKey();
    }

    /**
     * Helper method to build the tree from a JSON file.
     */
    private static void iterateAndBuildTree(JsonNode node, AndOrNode current) {
        AndOrNode newNode;
        if (node.isArray()) {
            for (JsonNode el : node) {
                iterateAndBuildTree(el, current);
            }
        } else if (node.isObject()) {
            newNode = AndOrNode.createAndOrNode("", current, getKey(node));
            current.insert(newNode);
            node.fields().forEachRemaining(entry -> {
                iterateAndBuildTree(entry.getValue(), newNode);
            });
        } else {
            newNode = AndOrNode.createAndOrNode(node.toString(), current);
            current.insert(newNode);
        }
    }

    @Override
    public String toString() {
        return this.root.toTreeString();
    }
}
