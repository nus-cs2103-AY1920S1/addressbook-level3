package seedu.jarvis.commons.util.andor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.model.course.Course;

import java.io.IOException;
import java.util.Collection;

/**
 * A representation of an And-Or Tree. The tree represents the requirements for the root node
 * to be {@code true}, and encapsulates some methods for operations in the tree.
 *
 * The tree is immutable once created.
 */
public class AndOrTree {
    private AndOrNode root;

    private AndOrTree(AndOrNode root) {
        this.root = root;
    }

    private static String getKey(JsonNode node) {
        return node.fields().next().getKey();
    }

    public static AndOrTree buildTree(String courseCode)
            throws IOException {
        Course course = CourseUtil.getCourse(courseCode);
        JsonNode node = new ObjectMapper().readTree(course.getPrereqTree().toString());

        AndOrNode rootNode = AndOrNode.createLeafNode(course, null);
        buildTree(node, rootNode);
        return new AndOrTree(rootNode);
    }

    /**
     * Helper method to build the tree from a JSON file.
     */
    private static void buildTree(JsonNode node, AndOrNode curr) {
        if (node.isObject()) {
            handleObject(node, curr);
        } else if (node.isArray()) {
            handleArray(node, curr);
        } else {
            handleLeaf(node, curr);
        }
    }

    /**
     * Handles the case where the current JsonNode is an object.
     */
    private static void handleObject(JsonNode node, AndOrNode curr) {
        AndOrNode newNode = AndOrNode.createAndOrNode(curr, getKey(node));
        curr.insert(newNode);
        node.fields().forEachRemaining(field -> {
            buildTree(field.getValue(), newNode);
        });
    }

    /**
     * Handles the case where the current JsonNode is an array.
     */
    private static void handleArray(JsonNode node, AndOrNode curr) {
        for (JsonNode el : node) {
            buildTree(el, curr);
        }
    }

    /**
     * Handles the case where the current JsonNode is a leaf node.
     */
    private static void handleLeaf(JsonNode node, AndOrNode curr) {
        Course leaf;
        try {
            leaf = CourseUtil.getCourse(node.asText());
        } catch (IOException e) {
            // do not create the node -> if the file does not exist, means the
            // course is likely no longer being offered
            return;
        }
        AndOrNode newNode = AndOrNode.createLeafNode(leaf, curr);
        curr.insert(newNode);
    }

    /**
     * Given the current And-Or tree, see if it satisfies the root requirement.
     *
     * @param collection of courses to check against the tree
     * @return true if it satisfies the root requirement
     */
    public boolean fulfillsCondition(Collection<Course> collection) {
        return root.getChildren().get(0).hasFulfilledCondition(collection);
    }

    @Override
    public String toString() {
        return this.root.toTreeString();
    }
}
