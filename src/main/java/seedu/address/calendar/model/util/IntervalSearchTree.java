package seedu.address.calendar.model.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class IntervalSearchTree<S extends IntervalPart<S>, T extends Interval<S, T>> {
    private Node root = null;
    private HashMap<Interval, Integer> intervalTracker = new HashMap<>();

    /* Methods that perform basic operations. */

    public Node insert(T interval) {
        root = insert(interval, root);
        return root;
    }

    private Node insert(T interval, Node root) {
        if (root == null) {
            return new Node(interval);
        }

        int compare = interval.compareTo(root.interval);
        Node rightSubtree = root.rightNode;
        Node leftSubtree = root.leftNode;

        if (compare == 0) {
            incrementInterval(interval);
        } else if (compare > 0) {
            updateRootMaxVal(interval, root);
            root.rightNode = insert(interval, rightSubtree);
        } else {
            updateRootMaxVal(interval, root);
            root.leftNode = insert(interval, leftSubtree);
        }

        root.height = computeHeight(leftSubtree, rightSubtree);
        return avlBalance(root);
    }

    private void incrementInterval(Interval interval) {
        if (!intervalTracker.containsKey(interval)) {
            intervalTracker.put(interval, 1);
            return;
        }
        int updatedNumberOfIntervals = intervalTracker.get(interval) + 1;
        intervalTracker.replace(interval, updatedNumberOfIntervals);
        return;
    }

    public void remove(T interval) throws NoSuchElementException {
        if (!intervalTracker.containsKey(interval)) {
            throw new NoSuchElementException("Unable to remove a non-existent interval");
        }
        root = remove(interval, root);
    }

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

        updateRootMaxVal(leftSubtree, root, rightSubtree);
        root.height = computeHeight(leftSubtree, rightSubtree);
        return avlBalance(root);
    }

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

    private void updateRootMaxVal(T interval, Node root) {
        if (interval.isEndsAfter(root.max)) {
            root.max = interval.getEnd();
        }
    }

    private void updateRootMaxVal(Node toUpdate, Node otherNode) {
        if (otherNode == null) {
            return;
        }
        toUpdate.max = getMaxIntervalPart(toUpdate, otherNode);
    }

    private void updateRootMaxVal(Node leftSubtree, Node root, Node rightSubtree) {
        if (leftSubtree == null && rightSubtree == null) {
            return;
        }

        if (leftSubtree == null) {
            root.max = getMaxIntervalPart(root, rightSubtree);
            return;
        }

        if (rightSubtree == null) {
            root.max = getMaxIntervalPart(root, leftSubtree);
            return;
        }

        S intermediateMax = getMaxIntervalPart(leftSubtree, rightSubtree);
        root.max = getMaxIntervalPart(root, intermediateMax);
    }

    private S getMaxIntervalPart(Node toUpdate, Node otherNode) {
        S otherNodeMax = otherNode.max;
        return getMaxIntervalPart(toUpdate, otherNodeMax);
    }

    private S getMaxIntervalPart(Node toUpdate, S otherMax) {
        S toUpdateMax = toUpdate.max;
        if (otherMax.compareTo(toUpdateMax) > 0) {
            return otherMax.copy();
        }
        return toUpdateMax;
    }

    /* Helper functions that maintain balanced AVL tree. */

    private int computeHeight(Node leftSubtree, Node rightSubtree) {
        int rightSubtreeHeight = rightSubtree == null ? 0 : rightSubtree.height;
        int leftSubtreeHeight = leftSubtree == null ? 0 : leftSubtree.height;

        return 1 + Math.max(rightSubtreeHeight, leftSubtreeHeight);
    }

    private Node findMin(Node node) {
        if (node.leftNode == null) {
            return node;
        }
        return findMin(node.leftNode);
    }

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

    private int balanceFactor(Node root) {
        Node rightSubtree = root.rightNode;
        Node leftSubtree = root.leftNode;

        int rightSubtreeHeight = rightSubtree == null ? 0 : rightSubtree.height;
        int leftSubtreeHeight = leftSubtree == null ? 0 : leftSubtree.height;

        return leftSubtreeHeight - rightSubtreeHeight;
    }

    private Node rotateLeft(Node root) {
        Node rightSubtree = root.rightNode;
        root.rightNode = rightSubtree.leftNode;
        // update root's max value
        updateRootMaxVal(root, rightSubtree.leftNode);
        rightSubtree.leftNode = root;
        // update rightSubtree's max value
        updateRootMaxVal(rightSubtree, root);

        // update height
        root.height = computeHeight(root.leftNode, root.rightNode);
        rightSubtree.height = computeHeight(rightSubtree.leftNode, rightSubtree.rightNode);

        return rightSubtree;
    }

    private Node rotateRight(Node root) {
        Node leftSubtree = root.leftNode;
        root.leftNode = leftSubtree.rightNode;
        // update root's max value
        updateRootMaxVal(root, leftSubtree.rightNode);
        leftSubtree.rightNode = root;
        // update leftSubtree's max value
        updateRootMaxVal(leftSubtree, root);

        // update height
        root.height = computeHeight(root.leftNode, root.rightNode);
        leftSubtree.height = computeHeight(leftSubtree.leftNode, leftSubtree.rightNode);

        return leftSubtree;
    }

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
