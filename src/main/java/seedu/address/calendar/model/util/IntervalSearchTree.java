package seedu.address.calendar.model.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents an interval search tree which is specifically used to reduce the time complexity involved in searching
 * for collisions between intervals.
 *
 * <p>This search tree is implemented using a AVL tree to ensure that the time complexity is minimised and does
 * not degrade.</p>
 *
 * @param <S> The type of interval
 * @param <T> The object which contains the interval specified above
 */
public class IntervalSearchTree<S extends IntervalPart<S>, T extends Interval<S, T>> {
    private Node root = null;
    private HashMap<Interval, Integer> intervalTracker = new HashMap<>();

    /* Methods that perform basic operations. */

    /**
     * Inserts an interval into the tree.
     *
     * @param interval The interval to be added
     * @return The relevant node
     */
    public Node insert(T interval) {
        root = insert(interval, root);
        return root;
    }

    /**
     * Inserts an interval into the tree.
     *
     * @param interval The interval to be added
     * @param root The root to add the interval to
     * @return The relevant node
     */
    private Node insert(T interval, Node root) {
        if (root == null) {
            incrementInterval(interval);
            return new Node(interval);
        }

        int compare = interval.compareTo(root.interval);
        Node rightSubtree = root.rightNode;
        Node leftSubtree = root.leftNode;

        if (compare == 0) {
            incrementInterval(interval);
            return root;
        } else if (compare > 0) {
            root.rightNode = insert(interval, rightSubtree);
        } else {
            root.leftNode = insert(interval, leftSubtree);
        }

        updateRootMaxVal(root.leftNode, root, root.rightNode);
        root.height = computeHeight(root.leftNode, root.rightNode);
        return avlBalance(root);
    }

    /**
     * Increments the number of intervals with specified interval.
     *
     * @param interval The specified interval
     */
    private void incrementInterval(Interval interval) {
        if (!intervalTracker.containsKey(interval)) {
            intervalTracker.put(interval, 1);
            return;
        }
        int updatedNumberOfIntervals = intervalTracker.get(interval) + 1;
        intervalTracker.replace(interval, updatedNumberOfIntervals);
        return;
    }

    /**
     * Removes an interval from the tree.
     *
     * @param interval The interval to be removed
     * @throws NoSuchElementException If the interval cannot be removed
     */
    public void remove(T interval) throws NoSuchElementException {
        if (!intervalTracker.containsKey(interval)) {
            throw new NoSuchElementException("Unable to remove a non-existent interval");
        }

        if (intervalTracker.get(interval) > 1) {
            decrementInterval(interval); // we do not need to actually remove the node since there are copies of it
            return;
        }

        root = remove(interval, root);
    }

    /**
     * Removes an interval from the tree.
     *
     * @param interval The interval to be removed
     * @param root The relevant root to remove the node from
     * @throws NoSuchElementException If the interval cannot be removed
     */
    private Node remove(T interval, Node root) {
        if (root == null) {
            return null;
        }

        T currentInterval = root.interval;
        Node rightSubtree = root.rightNode;
        Node leftSubtree = root.leftNode;

        int compare = interval.compareTo(currentInterval);

        if (compare == 0) {
            decrementInterval(interval);
            if (leftSubtree == null) {
                return rightSubtree;
            } else if (rightSubtree == null) {
                return leftSubtree;
            } else {
                Node rootPointer = root;
                root = findMin(rootPointer.rightNode);
                root.rightNode = deleteMin(rootPointer.rightNode);
                root.leftNode = rootPointer.leftNode;
            }
        } else if (compare > 0) {
            root.rightNode = remove(interval, rightSubtree);
        } else {
            root.leftNode = remove(interval, leftSubtree);
        }

        updateRootMaxVal(root.leftNode, root, root.rightNode);
        root.height = computeHeight(root.leftNode, root.rightNode);
        return avlBalance(root);
    }

    /**
     * Reduces the number of intervals specified.
     *
     * @param interval The specified interval to be removed
     */
    private void decrementInterval(Interval interval) {
        if (!intervalTracker.containsKey(interval)) {
            assert false : "Deleting of non-existent value is not allowed";
            return;
        }
        int updatedNumberOfIntervals = intervalTracker.get(interval) - 1;
        if (updatedNumberOfIntervals == 0) {
            intervalTracker.remove(interval);
            return;
        }
        intervalTracker.replace(interval, updatedNumberOfIntervals);
    }

    public boolean hasCollision(T interval) {
        return getCollision(interval, root) != null;
    }

    private T getCollision(T newInterval, Node subtreeRoot) {
        if (subtreeRoot == null || newInterval.isStartsAfter(subtreeRoot.max)) {
            return null;
        }

        T currentInterval = subtreeRoot.interval;

        if (currentInterval.isOverlap(newInterval)) {
            return currentInterval;
        }

        Node leftSubtree = subtreeRoot.leftNode;
        Node rightSubtree = subtreeRoot.rightNode;

        if (leftSubtree != null && !newInterval.isStartsAfter(leftSubtree.max)) {
            return getCollision(newInterval, leftSubtree);
        } else {
            return getCollision(newInterval, rightSubtree);
        }
    }
/*
    public void traverse(Node node) {
        if (node == null) {
            return;
        }
        traverse(node.leftNode);
        System.out.println(node.interval);
        System.out.println(node.max);
        System.out.println(" height: " + node.height);
        traverse(node.rightNode);
    }

    public void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.interval);
        System.out.println(node.max);
        System.out.println(" height: " + node.height);
        preOrder(node.leftNode);
        preOrder(node.rightNode);
    }*/

    /**
     * Gets intervals that collide with that specified.
     *
     * @param newInterval The specified interval
     * @return The colliding intervals
     */
    public List<T> getCollisions(Interval<S, ?> newInterval) {
        List<T> collisions = new ArrayList<>();
        getCollisions(newInterval, root, collisions);
        return collisions;
    }

    private void getCollisions(Interval<S, ?> newInterval, Node subtreeRoot, List<T> collisions) {
        if (subtreeRoot == null || newInterval.isStartsAfter(subtreeRoot.max)) {
            return;
        }

        T currentInterval = subtreeRoot.interval;

        if (currentInterval.isOverlap(newInterval)) {
            collisions.add(currentInterval);
        }

        Node leftSubtree = subtreeRoot.leftNode;
        Node rightSubtree = subtreeRoot.rightNode;

        if (leftSubtree != null && !newInterval.isStartsAfter(leftSubtree.max)) {
            getCollisions(newInterval, leftSubtree, collisions);
        }

        if (currentInterval.isStartsAfter(newInterval.getEnd())) {
            return;
        }

        getCollisions(newInterval, rightSubtree, collisions);
    }

    /* Helper functions that maintain interval tree. */

    private void updateRootMaxVal(Node leftSubtree, Node root, Node rightSubtree) {
        if (leftSubtree == null && rightSubtree == null) {
            root.max = root.interval.getEnd();
            return;
        }

        if (leftSubtree == null) {
            root.max = getMaxIntervalPart(root.interval.getEnd(), rightSubtree.max);
            return;
        }

        if (rightSubtree == null) {
            root.max = getMaxIntervalPart(root.interval.getEnd(), leftSubtree.max);
            return;
        }

        S maxBetweenLeftAndRight = getMaxIntervalPart(leftSubtree.max, rightSubtree.max);
        root.max = getMaxIntervalPart(root.interval.getEnd(), maxBetweenLeftAndRight);
    }

    /**
     * Gets the maximum interval part.
     *
     * @param currentEnd The current end interval part
     * @param otherMax The other maximum interval part
     * @return The relevant interval part
     */
    private S getMaxIntervalPart(S currentEnd, S otherMax) {
        if (currentEnd.compareTo(otherMax) < 0) {
            return otherMax;
        } else {
            return currentEnd;
        }
    }

    /* Helper functions that maintain balanced AVL tree. */

    /**
     * Computes the height of the current node.
     *
     * @param leftSubtree The relevant left subtree
     * @param rightSubtree The relevant right subtree
     * @return The required height
     */
    private int computeHeight(Node leftSubtree, Node rightSubtree) {
        int rightSubtreeHeight = rightSubtree == null ? 0 : rightSubtree.height;
        int leftSubtreeHeight = leftSubtree == null ? 0 : leftSubtree.height;

        return 1 + Math.max(rightSubtreeHeight, leftSubtreeHeight);
    }

    /**
     * Finds the minimum in the branch.
     *
     * @param node The required node to be deleted
     * @return The relevant node to be removed.
     */
    private Node findMin(Node node) {
        if (node.leftNode == null) {
            return node;
        }
        return findMin(node.leftNode);
    }

    /**
     * Delete the minimum from the relevant branch.
     *
     * @param node The node to be deleted
     * @return The relevant node to be removed
     */
    private Node deleteMin(Node node) {
        if (node == null) {
            return null;
        }

        if (node.leftNode == null) {
            return node.rightNode;
        }

        node.leftNode = deleteMin(node);
        Node leftSubtree = node.leftNode;
        Node rightSubtree = node.rightNode;
        node.height = computeHeight(leftSubtree, rightSubtree);
        updateRootMaxVal(leftSubtree, root, rightSubtree);

        return avlBalance(node);
    }

    /**
     * Balances the interval search tree.
     *
     * @param root The root of the tree
     * @return The relevant node
     */
    private Node avlBalance(Node root) {
        Node rightSubtree = root.rightNode;
        Node leftSubtree = root.leftNode;

        if (balanceFactor(root) < -1) {
            if (balanceFactor(rightSubtree) > 0) {
                root.rightNode = rotateRight(rightSubtree);
            }
            root = rotateLeft(root);
        } else if (balanceFactor(root) > 1) {
            if (balanceFactor(leftSubtree) < 0) {
                root.leftNode = rotateLeft(leftSubtree);
            }
            root = rotateRight(root);
        }

        return root;
    }

    /**
     * Balancing factor for the current node
     * @param root The root of the current tree
     * @return The factor
     */
    private int balanceFactor(Node root) {
        Node rightSubtree = root.rightNode;
        Node leftSubtree = root.leftNode;

        int rightSubtreeHeight = rightSubtree == null ? 0 : rightSubtree.height;
        int leftSubtreeHeight = leftSubtree == null ? 0 : leftSubtree.height;

        return leftSubtreeHeight - rightSubtreeHeight;
    }

    /**
     * Rotates the branch to the left.
     *
     * @param root The root of the current node
     * @return The relevant node
     */
    private Node rotateLeft(Node root) {
        Node rightSubtree = root.rightNode;
        root.rightNode = rightSubtree.leftNode;
        // update root's max value
        updateRootMaxVal(root.leftNode, root, rightSubtree.leftNode);
        rightSubtree.leftNode = root;
        // update rightSubtree's max value
        updateRootMaxVal(rightSubtree.rightNode, rightSubtree, root);

        // update height
        root.height = computeHeight(root.leftNode, root.rightNode);
        rightSubtree.height = computeHeight(rightSubtree.leftNode, rightSubtree.rightNode);

        return rightSubtree;
    }

    /**
     * Rotates the branch to the right.
     *
     * @param root The root of the current node
     * @return The relevant node
     */
    private Node rotateRight(Node root) {
        Node leftSubtree = root.leftNode;
        root.leftNode = leftSubtree.rightNode;
        // update root's max value
        updateRootMaxVal(root.leftNode, root, leftSubtree.leftNode);
        leftSubtree.rightNode = root;
        // update leftSubtree's max value
        updateRootMaxVal(leftSubtree.leftNode, leftSubtree, root);

        // update height
        root.height = computeHeight(root.leftNode, root.rightNode);
        leftSubtree.height = computeHeight(leftSubtree.leftNode, leftSubtree.rightNode);

        return leftSubtree;
    }

    /**
     * Represents a node of the tree.
     */
    private class Node {
        private S max;
        private int height;
        private T interval;
        private Node leftNode;
        private Node rightNode;

        public Node(T interval, S max, int height) {
            this.interval = interval;
            leftNode = null;
            rightNode = null;
            this.max = max;
            this.height = height;
        }

        public Node(T interval) {
            this(interval, interval.getEnd(), 1);
        }

    }
}
