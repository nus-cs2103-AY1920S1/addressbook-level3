package seedu.jarvis.commons.util.andor;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Function;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A representation of an And-Or Tree. The tree represents the requirements for the root node
 * to be {@code true}, and encapsulates some methods for operations in the tree.
 *
 * The tree is immutable once created.
 *
 * @author ryanYtan
 */
public class AndOrTree<T> {
    private AndOrNode<T> root;

    private AndOrTree(AndOrNode<T> root) {
        this.root = root;
    }

    /**
     * Builds a tree of type {@code R} from the given jsonString. The method requires a
     * {@code Function<? super String, ? extends R>} to map from {@code String} nodes in the
     * jsonString to the desired type {@code R}.
     *
     * @param rootData can be any {@code String} representing the root
     * @param jsonString to be read
     * @param function mapper function from {@code String -> R}
     * @param <R> type of data the tree will be storing
     * @return a new {@code AndOrTree} of type {@code R}
     */
    public static <R> AndOrTree<R> buildTree(String rootData, String jsonString,
                                             Function<? super String, ? extends R> function) {
        AndOrNode<R> rootNode = AndOrNode.createNode(function.apply(rootData));
        try {
            JsonNode node = new ObjectMapper().readTree(addQuotes(jsonString));
            buildTree(node, rootNode, function);
        } catch (IOException e) {
            // return empty tree
        }
        return new AndOrTree<>(rootNode);
    }

    /**
     * Builds a tree from the given JsonNode.
     *
     * @param node to build
     * @param curr current node
     * @param function mapper function from {@code String -> R}
     * @param <R> type of data the tree will be storing
     */
    private static <R> void buildTree(JsonNode node, AndOrNode<R> curr,
                                      Function<? super String, ? extends R> function) {
        if (node.isObject()) {
            handleObject(node, curr, function);
        } else if (node.isArray()) {
            handleArray(node, curr, function);
        } else {
            handleLeaf(node, curr, function);
        }
    }

    /**
     * Handles the case where the current JsonNode is an object.
     */
    private static <R> void handleObject(JsonNode node, AndOrNode<R> curr,
                                         Function<? super String, ? extends R> function) {
        AndOrNode<R> newNode = AndOrNode.createNode(null, getKey(node));
        curr.insert(newNode);
        node.fields().forEachRemaining(field -> {
            buildTree(field.getValue(), newNode, function);
        });
    }

    /**
     * Handles the case where the current JsonNode is an array.
     */
    private static <R> void handleArray(JsonNode node, AndOrNode<R> curr,
                                        Function<? super String, ? extends R> function) {
        for (JsonNode children : node) {
            buildTree(children, curr, function);
        }
    }

    /**
     * Handles the case where the current JsonNode is a leaf node.
     */
    private static <R> void handleLeaf(JsonNode node, AndOrNode<R> curr,
                                       Function<? super String, ? extends R> function) {
        R data = function.apply(node.asText());
        AndOrNode<R> newNode = AndOrNode.createNode(data);
        curr.insert(newNode);
    }

    /**
     * Adds quotes around the given {@code String} if it follows the following conditions:
     * 1. The {@code String} is not empty
     * 2. The {@code String} is a json object and has "{".
     *
     * @param jsonString to check
     * @return a new {@code String}
     */
    private static String addQuotes(String jsonString) {
        if (!jsonString.contains("{") && !jsonString.isEmpty()) {
            return "\"" + jsonString + "\"";
        }
        return jsonString;
    }

    /**
     * Get the first key of the given node.
     */
    private static String getKey(JsonNode node) {
        return node.fields().next().getKey();
    }

    /**
     * Given the current And-Or tree, see if it satisfies the root requirement.
     *
     * @param collection of courses to check against the tree
     * @return true if it satisfies the root requirement
     */
    public boolean fulfills(Collection<T> collection) {
        if (root.getChildren().isEmpty()) {
            return true;
        }
        return root.getChildren().get(0).fulfills(collection);
    }
}
