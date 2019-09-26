package seedu.jarvis.commons.util.andor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class AndOrTree<T> {
    private String name;
    private AndOrNode<T> root;

    private AndOrTree(String name, AndOrNode root) {
        this.name = name;
        this.root = root;
    }

    /**
     * Returns a new AndOr Tree with the given name, using a tree in json format.
     *
     * @param name of the tree
     * @param jsonTreeFormat
     * @return a new {@code AndOrTree} built with the given tree format
     * @throws IOException if an I/O Exception occurs
     */
    public static AndOrTree buildTree(String name, String jsonTreeFormat) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonTreeFormat);

        // build the root
        AndOrNode newRoot = new AndOrNode<>("CS3244", getKey(node), null);

        iterateAndBuildTree(node, newRoot);

        return new AndOrTree("name", newRoot);
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
            newNode = new AndOrNode<>("", getKey(node), current);
            current.insert(newNode);
            node.fields().forEachRemaining(entry -> {
                iterateAndBuildTree(entry.getValue(), newNode);
            });
        } else {
            newNode = new AndOrNode<>(node.toString(), "leaf", current);
            current.insert(newNode);
        }
    }

    @Override
    public String toString() {
        return this.root.toString();
    }
}
