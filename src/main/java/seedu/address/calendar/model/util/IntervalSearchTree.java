package seedu.address.calendar.model.util;

import java.util.ArrayList;
import java.util.List;

class IntervalSearchTree<S extends IntervalPart<S>,  T extends Interval<S, T>>{
    private Node root = null;

    public Node insert(Interval interval) {
        root = insert(interval, root);
        return root;
    }

    private Node insert(Interval interval, Node root) {
        if (root == null) {
            return new Node(interval);
        }

        int compare = interval.compareTo(root.interval);
        Node rightSubtree = root.rightNode;
        Node leftSubtree = root.leftNode;

        if (compare == 0) {
            // todo: insert into list
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

    public void remove(Interval interval) {
        root = remove(interval, root);
    }

    private Node remove(Interval interval, Node root) {
        if (root == null) {
            return null;
        }

        Interval currentInterval = root.interval;
        Node rightSubtree = root.rightNode;
        Node leftSubtree = root.leftNode;

        int compare = interval.compareTo(currentInterval);

        if (compare == 0) {
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

    public boolean hasCollision(Interval interval) {
        return getCollision(interval, root) != null;
    }

    public List<Interval> getCollisions(Interval newInterval) {
        List<Interval> collisions = new ArrayList<>();
        getCollisions(newInterval, root, collisions);
        return collisions;
    }

    private void updateRootMaxVal(Interval interval, Node root) {
        if (interval.isEndsAfter(root.max)) {
            root.max = interval.getEnd();
        }
    }

    private void updateRootMaxVal(Node toUpdate, Node otherNode) {
        if (otherNode == null) {
            return;
        }
        toUpdate.max = getMaxIntervalPart(toUpdate, otherNode);
        /*
        int maxOther = otherNode == null ? -1 : otherNode.max;
        toUpdate.max = Math.max(toUpdate.max, maxOther);
         */
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

        IntervalPart intermediateMax = getMaxIntervalPart(leftSubtree, rightSubtree);
        root.max = getMaxIntervalPart(root, intermediateMax);
    }

    private IntervalPart getMaxIntervalPart(Node toUpdate, Node otherNode) {
        IntervalPart otherNodeMax = otherNode.max;
        return getMaxIntervalPart(toUpdate, otherNodeMax);
    }

    private IntervalPart getMaxIntervalPart(Node toUpdate, IntervalPart otherMax) {
        IntervalPart toUpdateMax = toUpdate.max;
        if (otherMax.compareTo(toUpdateMax) > 0) {
            return otherMax.copy();
        }
        return toUpdateMax;
    }

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

    private Interval getCollision(Interval newInterval, Node subtreeRoot) {
        if (subtreeRoot == null || newInterval.isStartsAfter(subtreeRoot.max)) {
            return null;
        }

        Interval currentInterval = subtreeRoot.interval;

        if (isOverlap(currentInterval, newInterval)) {
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

    private void getCollisions(Interval newInterval, Node subtreeRoot, List<Interval> collisions) {
        if (subtreeRoot == null || newInterval.isStartsAfter(subtreeRoot.max)) {
            return;
        }

        Interval currentInterval = subtreeRoot.interval;
        if (isOverlap(currentInterval, newInterval)) {
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

    private boolean isOverlap(Interval currentInterval, Interval newInterval) {
        Interval firstInterval;
        Interval secondInterval;
        int compare = currentInterval.compareTo(newInterval);

        if (compare == 0) {
            return true;
        } else if (compare < 0) {
            firstInterval = currentInterval;
            secondInterval = newInterval;
        } else {
            firstInterval = newInterval;
            secondInterval = currentInterval;
        }

        if (firstInterval.contains(secondInterval.getStart())) {
            return true;
        } else if (secondInterval.contains(firstInterval.getEnd())) {
            return true;
        }

        return false;
    }

    private class Node {
        private IntervalPart max;
        private int height;
        private Interval interval;
        private Node leftNode;
        private Node rightNode;

        public Node(Interval interval, IntervalPart max, int height) {
            this.interval = interval;
            leftNode = null;
            rightNode = null;
            this.max = max;
            this.height = height;
        }

        public Node(Interval interval) {
            this(interval, interval.getEnd(), 1);
        }

    }
}
