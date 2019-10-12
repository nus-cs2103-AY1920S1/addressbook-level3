package seedu.jarvis.commons.util.andor;

import java.io.IOException;

import java.util.Collection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.jarvis.commons.exceptions.CourseNotFoundException;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.model.course.Course;


/**
 * A representation of an And-Or Tree. The tree represents the requirements for the root node
 * to be {@code true}, and encapsulates some methods for operations in the tree.
 *
 * The tree is immutable once created.
 *
 * @author ryanYtan
 */
public class AndOrTree {
    private static final String NO_PREREQ_MESSAGE = "%s has no prerequisites!";

    private AndOrNode root;

    private AndOrTree(AndOrNode root) {
        this.root = root;
    }

    /**
     * Get the first key of the given node.
     */
    private static String getKey(JsonNode node) {
        return node.fields().next().getKey();
    }

    /**
     * Builds the And-Or Tree from the given course code.
     *
     * @param courseCode of the course
     * @return a new AndOr tree
     * @throws IOException if the course file could not be found
     */
    public static AndOrTree buildTree(String courseCode)
            throws CourseNotFoundException {
        Course course;
        course = CourseUtil.getCourse(courseCode);
        AndOrNode rootNode = AndOrNode.createLeafNode(course, null);
        JsonNode node;
        try {
            String prereqTree = course.getPrereqTree().toString();
            prereqTree = CourseUtil.addQuotes(prereqTree);
            node = new ObjectMapper().readTree(prereqTree);
            buildTree(node, rootNode);
        } catch (NullPointerException e) {
            // return empty tree
        } catch (IOException e) {
            throw new CourseNotFoundException("course not found");
        }
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
        } catch (CourseNotFoundException e) {
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
        if (root.getChildren().size() <= 0) {
            return String.format(NO_PREREQ_MESSAGE, root.toString());
        }
        return this.root.toTreeString();
    }
}
