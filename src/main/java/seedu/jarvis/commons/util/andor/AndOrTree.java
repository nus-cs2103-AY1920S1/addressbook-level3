package seedu.jarvis.commons.util.andor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class AndOrTree<T> {
    private String name;
    private AndOrNode<T> root;

    private AndOrTree(String name, AndOrNode root) {
        this.name = name;
        this.root = root;
        traverse();
    }

    public static AndOrTree buildTree(String name, String jsonTreeFormat)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonTreeFormat);
        // build the root
        AndOrNode newRoot = new AndOrNode<>("CS3244", getKey(node), null);
        System.out.println(newRoot);

        iterateAndBuildTree(node, newRoot);

        return new AndOrTree("name", newRoot);
    }

    private static String getKey(JsonNode node) {
        return node.fields().next().getKey();
    }

    /**
     * Adapted from https://stackoverflow.com/a/48645692.
     */
    private static void iterateAndBuildTree(JsonNode node, AndOrNode current) {
        System.out.println(node);
        AndOrNode newNode;
        if (node.isArray()) {
            for (JsonNode el : node) {
                // current.insert(new AndOrNode());
                iterateAndBuildTree(el, current);
            }
        } else if (node.isObject()) {
            newNode = new AndOrNode<>("", getKey(node), current);
            current.insert(newNode);
            node.fields().forEachRemaining(entry -> {
                // either "and" or "or"
                iterateAndBuildTree(entry.getValue(), newNode);
            });
        } else {
            newNode = new AndOrNode<>(node.toString(), "leaf", current);
            current.insert(newNode);
            // leaf
            // current.insert(new AndOrNode());
        }
    }

    public void traverse() {
        traverse(root);
    }

    private void traverse(AndOrNode<T> curr) {
        System.out.println(curr.getData());
        System.out.println(curr.getChildren().toString());
        List<AndOrNode> elements = curr.getChildren();
        for (AndOrNode el : elements) {
            traverse(el);
        }
    }
}
